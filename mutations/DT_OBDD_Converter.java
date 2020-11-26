package mutations;

import decision_table.DecisionTable;
import decision_table.Rule;
import decision_table.Pair;
import decision_table.Action;
import ordered_binary_decision_diagram.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DT_OBDD_Converter {

    /*private DecisionTable decisionTable;
    private OrderedBinaryDecisionDiagram orderedBinaryDecisionDiagram;
    private List<DecisionTable> dts;*/

    /**
     * Converts a given {@link OrderedBinaryDecisionDiagram} into {@link DecisionTable}.
     * */
    public DecisionTable OBDDtoDT(OrderedBinaryDecisionDiagram obdd){
        DecisionTable decisionTable = new DecisionTable(obdd.getName());
        if (obdd.getTheLowestNonterminalNodes().isEmpty() && obdd.getTerminalNodes().isEmpty()){
            obdd.fill_NTNList();
            obdd.fill_TNLists();
        }

        int ruleID = 0;

        for (Node tn: obdd.getTerminalNodes()){
            Node temp = tn;
            while(!temp.isRoot()){
                Node prev = temp;
                temp = temp.getParentNode();
                decisionTable.addPairToRule(ruleID, temp.getValue(), prev.getIncomingEdge());
            }
            Collections.reverse(decisionTable.getRuleList().get(ruleID).getConditionPairs());
            decisionTable.getRuleList().get(ruleID).setActionPairs(separateActions(tn));
            ruleID++;
        }

        complementActionPairs(decisionTable);
        return decisionTable;
    }
    /**
     * Converts a given {@link DecisionTable} into {@link OrderedBinaryDecisionDiagram}.
     * */
    public OrderedBinaryDecisionDiagram DTtoOBDD(DecisionTable dt){
        OrderedBinaryDecisionDiagram orderedBinaryDecisionDiagram = new OrderedBinaryDecisionDiagram(dt.getName());

        List<Rule> rl = makeExpandedDT(dt).getRuleList();

        for(Rule rule: rl){
            List<Object> dtps = rule.getConditionPairs().stream().map(Pair::getValue).collect(Collectors.toList());
            dtps.add(mergeActions(rule.getActionPairs()));
            List<Boolean> bls = rule.getConditionPairs().stream().map(Pair::getaBoolean).collect(Collectors.toList());
            bls.add(true);

            orderedBinaryDecisionDiagram.insertNodes(dtps, bls);
        }

        return orderedBinaryDecisionDiagram;
    }
    /**
     * Converts a list of {@link OrderedBinaryDecisionDiagram}s into a list of {@link DecisionTable}s.
     * */
    public List<DecisionTable> OBDDsToDTs(List<OrderedBinaryDecisionDiagram> obdds){
        return obdds.stream().map(this::OBDDtoDT).collect(Collectors.toCollection((ArrayList::new)));
    }

    /**
     * Useful if we need to get rid of 'don't care' values in {@link DecisionTable}
     */
    public DecisionTable makeExpandedDT(DecisionTable decisionTable){
        DecisionTable expandedDT = decisionTable.copyOfDecisionTable();
        List<Rule> expandedRuleList = expandedDT.getRuleList();

        while(expandedRuleList.stream().flatMap(p -> p.getConditionPairs().stream()).anyMatch(p -> p.getaBoolean() == null))
            for (int i = 0; i < expandedRuleList.size(); i++)
                for (int j = 0; j < expandedRuleList.get(i).getConditionPairs().size(); j++)
                    if (expandedRuleList.get(i).getConditionPairs().get(j).getaBoolean() == null){
                        expandedRuleList.get(i).getConditionPairs().get(j).setaBoolean(false);
                        expandedRuleList.add(i+1, expandedRuleList.get(i).copyOfRule());
                        expandedRuleList.get(i+1).getConditionPairs().get(j).setaBoolean(true);
                    }

        for (int i = 0; i < expandedRuleList.size(); i++)
            if (i != expandedRuleList.get(i).getID())
                expandedRuleList.get(i).setID(i);

        return expandedDT;
    }
    /**
     * Useful when a {@link Rule} has 2 or more active {@link Action}s.
     * <p>Is used during conversion of {@link DecisionTable} into {@link OrderedBinaryDecisionDiagram}</p>
     * */
    private Object mergeActions(List<Pair> actionList){
        return actionList.stream().filter(Pair::getaBoolean).map(Pair::getValue).collect(Collectors.toList());
    }
    /**
     * Useful when a {@link Node} has 2 or more terminal elements.
     * <p>Is used during conversion of {@link OrderedBinaryDecisionDiagram} into {@link DecisionTable}</p>
     * */
    private List<Pair> separateActions(Node node){
        List<Object> actions = (List<Object>)node.getValue();

        return actions.stream().map(p -> new Pair(p, true)).collect(Collectors.toList());
    }
    /**
     * Restores all {@link Action}s during translation of {@link OrderedBinaryDecisionDiagram} into {@link DecisionTable}.
     * */
    private void complementActionPairs(DecisionTable decisionTable){
        List<Action> ap = decisionTable.getRuleList().stream().flatMap(p -> p.getActionPairs().stream()).map(p -> (Action)p.getValue()).sorted().distinct().collect(Collectors.toList());

        for (Rule r: decisionTable.getRuleList()){
            List<Pair> pl = r.getActionPairs();
            if (pl.isEmpty())
                ap.forEach(a -> r.addPair(new Pair(a, false)));
            else
                Collections.sort(ap.stream()
                        .filter(action -> !(pl.stream().map(x -> (Action)x.getValue()).collect(Collectors.toList()).contains(action)))
                        .map(action -> new Pair(action, false))
                        .collect(Collectors.toCollection(() -> pl)));
        }
    }
}
