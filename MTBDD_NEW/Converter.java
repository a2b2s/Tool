package MTBDD_NEW;

import decision_table.*;

import java.util.List;

public class Converter {

    public static DecisionTable MTBDD_into_DT(MTBDD mtbdd){
        DecisionTable dt = new DecisionTable(mtbdd.getName());



        return dt;
    }

    public static MTBDD DT_into_MTBDD(DecisionTable dt){
        MTBDD mtbdd = new MTBDD(dt.getName());

        List<Rule> ruleList = dt.getRuleList();
        for (Rule rule: ruleList){
            List<Pair> conditionPairs = rule.getConditionPairs();

            Pair firstPair = conditionPairs.get(0);

            if (mtbdd.getNodes().isEmpty()) {
                Condition c = (Condition) firstPair.getValue();
                mtbdd.addNode(Node.initializeNode(c.getName()));
            }
            boolean b = firstPair.getaBoolean();
            int prevID;
            mtbdd.setLastID(0);

            for (int i = 1; i < conditionPairs.size(); i++){
                prevID = mtbdd.getLastID();
                if (b && !mtbdd.hasHighChild(prevID)) {
                    mtbdd.addNode(Node.initializeNode(((Condition)conditionPairs.get(i).getValue()).getName()));
                    mtbdd.setHighID(prevID, mtbdd.getLastID());
                } else if (b && mtbdd.hasHighChild(mtbdd.getLastID()))
                    mtbdd.setLastID(mtbdd.getHighID(prevID));
                else if (!b && !mtbdd.hasLowChild(prevID)) {
                    mtbdd.addNode(Node.initializeNode(((Condition)conditionPairs.get(i).getValue()).getName()));
                    mtbdd.setLowID(prevID, mtbdd.getLastID());
                } else if (!b && mtbdd.hasLowChild(mtbdd.getLastID()))
                    mtbdd.setLastID(mtbdd.getLowID(prevID));

                b = conditionPairs.get(i).getaBoolean();
            }

            Pair actionPair = rule.getActionPairs().stream().filter(Pair::getaBoolean).findFirst().get();
            int tnID = mtbdd.existingTNodeID(((Action)actionPair.getValue()).getName());
            if (tnID == -1) {
                prevID = mtbdd.getLastID();
                mtbdd.addNode(Node.initializeNode(((Action) actionPair.getValue()).getName()));
                mtbdd.addTerminalNode(mtbdd.getLastID());
                mtbdd.addParentToTerminalNode(prevID, mtbdd.getLastID());
                if (b)
                    mtbdd.setHighID(prevID, mtbdd.getLastID());
                else
                    mtbdd.setLowID(prevID, mtbdd.getLastID());
            } else {
                prevID = mtbdd.getLastID();
                mtbdd.setLastID(tnID);
                mtbdd.addParentToTerminalNode(prevID, mtbdd.getLastID());
                if (b)
                    mtbdd.setHighID(prevID, mtbdd.getLastID());
                else
                    mtbdd.setLowID(prevID, mtbdd.getLastID());
            }
        }

        return mtbdd;
    }

}
