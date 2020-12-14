package dt_mtbdd_converter;

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
    public MTBDD DecisionTable_into_MTBDD(DecisionTable decisionTable){
        MTBDD mtbdd = new MTBDD(decisionTable.getName());
        MTBDDBuilder build = new MTBDDBuilder(mtbdd);

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
                build.addRoot(nonTerminalNodesChain.get(0));
            for (int j = 1; j < nonTerminalNodesChain.size(); j++) {
                build.addNonterminalNode(build.getCurrentNode(), nonTerminalNodesChain.get(j), prev);
                prev = edgesChain.get(j);
            }
            build.addTerminalNode(build.getCurrentNode(), terminalNode, prev);
            build.setCurrentNode(mtbdd.getRoot());
        }

        return mtbdd;
    }
    /**
     * This method converts {@link MTBDD} into {@link DecisionTable}
     * @param mtbdd is a {@link MTBDD}.
     * @return a new {@link DecisionTable}.
     * */
    public DecisionTable MTBDD_into_DecisionTable(MTBDD mtbdd){
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
    }
}
