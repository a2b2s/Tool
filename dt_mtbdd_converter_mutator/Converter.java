package dt_mtbdd_converter_mutator;

import MTBDD.*;
import decision_table.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serves as a translator between {@link DecisionTable}s and {@link MTBDD}s.
 * */
public class Converter {
    /**
     * This method converts {@link DecisionTable} into {@link MTBDD}
     * @param decisionTable is a {@link DecisionTable}.
     * @return a new {@link MTBDD}.
     * */
    /*public MTBDD DecisionTable_into_MTBDD(DecisionTable decisionTable){
        MTBDD mtbdd = new MTBDD(decisionTable.getName());
        MTBDDBuilder builder = new MTBDDBuilder(mtbdd);

        for (int i = 0; i < decisionTable.getRuleList().size(); i++){
            Rule tempRule = decisionTable.getRuleList().get(i);
            List<Node> nonTerminalNodesChain = tempRule.getConditionPairs()
                    .stream().map(c -> (Condition)c.getValue()).map(v -> new Node(v)).collect(Collectors.toCollection(ArrayList::new));
            List<Boolean> edgesChain = tempRule.getConditionPairs()
                    .stream().map(Pair::getaBoolean).collect(Collectors.toCollection(ArrayList::new));
            Node terminalNode = tempRule.getActionPairs()
                    .stream().filter(Pair::getaBoolean).map(x -> new Node((Action)x.getValue())).findFirst().get();

            boolean prev = edgesChain.get(0);
            if (mtbdd.getRoot() == null)
                builder.addRoot(nonTerminalNodesChain.get(0));
            for (int j = 1; j < nonTerminalNodesChain.size(); j++) {
                builder.addNonterminalNode(builder.getCurrentNode(), nonTerminalNodesChain.get(j), prev);
                prev = edgesChain.get(j);
            }
            builder.addTerminalNode(builder.getCurrentNode(), terminalNode, prev);
            builder.setCurrentNode(mtbdd.getRoot());
        }

        return mtbdd;
    }*/
    /**
     * This experimental method converts reduced Decision Table into reduced MTBDD
     * */
    public MTBDD DT_into_MTBDD(DecisionTable decisionTable){
        MTBDD mtbdd = new MTBDD(decisionTable.getName());
        MTBDDBuilder builder = new MTBDDBuilder(mtbdd);

        List<Action> allActions = decisionTable.getRuleList().get(0).getActionPairs().stream().map(o -> (Action)o.getValue()).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < decisionTable.getRuleList().size(); i++){
            Rule currentRule = decisionTable.getRuleList().get(i);
            List<Pair> ruleConditionPairs = currentRule.getConditionPairs();
            Pair actionPair = currentRule.getActionPairs().stream().filter(Pair::getaBoolean).findAny().get();

            if (builder.getMTBDD().getRoot() == null)
                builder.addRoot(new Node(ruleConditionPairs.get(0).getValue()));
            boolean theLastEdge = ruleConditionPairs.get(0).getaBoolean();
            for (int j = 1; j < ruleConditionPairs.size(); j++){
                if (ruleConditionPairs.get(j).getaBoolean() != null){
                    Node newNode = new Node(ruleConditionPairs.get(j).getValue());
                    builder.addNonterminalNode(builder.getCurrentNode(), newNode, theLastEdge);
                    theLastEdge = ruleConditionPairs.get(j).getaBoolean();
                }
            }
            builder.addTerminalNode(builder.getCurrentNode(), new Node(actionPair.getValue()), theLastEdge);
            builder.setCurrentNode(mtbdd.getRoot());
        }

        return mtbdd;
    }
    /**
     * This experimental method converts reduced MTBDD into reduced Decision Table
     * */
    public DecisionTable MTBDD_into_DT(MTBDD mtbdd){
        DecisionTable dt = new DecisionTable(mtbdd.getName());
        Map<Node, List<Node>> terminalNodes = mtbdd.getTerminalNodes();

        int i = 0;
        for (Map.Entry<Node, List<Node>> entry: terminalNodes.entrySet()){
            Node tnNode = entry.getKey();
            List<Node> nontNodes = entry.getValue();
            List<Boolean> edges = mtbdd.getTerminalIncomingEdges().get(tnNode);

            for (int k = 0; k < nontNodes.size(); k++){
                Rule r = new Rule(i);
                Node nontNode = nontNodes.get(k);
                boolean edge = edges.get(k), save = false;
                while(!nontNode.isRootNode()){
                    r.addPair(nontNode.getValue(), edge);
                    nontNode = nontNode.getParent();
                    save = edge;
                    edge = nontNode.getIncomingEdge();
                }

                r.addPair(nontNode.getValue(), save);
                r.addPair(tnNode.getValue(), true);

                Collections.reverse(r.getConditionPairs());
                dt.addRule(r);
                i++;
            }
        }

        return dt;
    }
    /**
     * This method converts {@link MTBDD} into {@link DecisionTable}
     * @param mtbdd is a {@link MTBDD}.
     * @return a new {@link DecisionTable}.
     * */
    /*public DecisionTable MTBDD_into_DecisionTable(MTBDD mtbdd){
        DecisionTable decisionTable = new DecisionTable(mtbdd.getName());
        Map<Node, List<Node>> temp = mtbdd.getTerminalNodes();

        int i = 0;
        for (Map.Entry<Node, List<Node>> entry: temp.entrySet()){
            Node Tn = entry.getKey();
            List<Node> tempNTs = entry.getValue();
            List<Boolean> tempEdges = mtbdd.getTerminalIncomingEdges().get(Tn);

            for(int j = 0; j < tempNTs.size(); ++j){
                Rule newRule = new Rule(i);

                Node NTn = tempNTs.get(j);
                boolean b = tempEdges.get(j), save = false;

                while(!NTn.isRootNode()){
                    newRule.addPair(NTn.getValue(), b);
                    NTn = NTn.getParent();
                    save = b;
                    b = NTn.getIncomingEdge();
                }

                newRule.addPair(NTn.getValue(), save);
                newRule.addPair(Tn.getValue(), true);

                Collections.reverse(newRule.getConditionPairs());
                decisionTable.addRule(newRule);
                i++;
            }
        }

        return decisionTable;
    }*/
}
