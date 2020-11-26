package mutations;

import decision_table.Action;
import decision_table.Condition;
import decision_table.DecisionTable;
import decision_table.Rule;

import java.util.ArrayList;
import java.util.List;

public class DTMutationOperators {

    private List<DecisionTable> dtMutantSet = new ArrayList<>();
    private List<DecisionTable> omittedActions = new ArrayList<>(), insertedActions = new ArrayList<>();
    private List<DecisionTable> omittedRules = new ArrayList<>(), insertedRules = new ArrayList<>();

    public DTMutationOperators(){
    }
    public DTMutationOperators(DecisionTable originalDT, Action action){
        omitActionFromDT(originalDT);
        insertActionInDT(originalDT, action);
    }

    public void omitActionFromDT(DecisionTable originalDT){
        for(int i = 0; i < originalDT.getRuleList().get(0).getActionPairs().size(); i++){
            DecisionTable copy = originalDT.copyOfDecisionTable();

            int trueActionCount = 0;
            boolean ignore = false;

            for (int j = 0; j < originalDT.getRuleList().size(); j++){
                Rule tempRule = copy.getRuleList().get(j);
                if (trueActionCount > 1) {
                    ignore = true;
                    break;
                }
                else if (tempRule.getActionPairs().get(i).getaBoolean())
                    trueActionCount++;
            }

            if (ignore)
                continue;
            else {
                for (int j = 0; j < originalDT.getRuleList().size(); j++)
                    copy.getRuleList().get(j).getActionPairs().remove(i);

                omittedActions.add(copy);
            }


        }

        dtMutantSet.addAll(omittedActions);
    }
    public void insertActionInDT(DecisionTable originalDT, Action action){
        for (int i = 0; i < originalDT.getRuleList().size(); i++){
            DecisionTable copy = originalDT.copyOfDecisionTable();
            copy.addPairToRule(i, action, true);
            copy.complementOtherRules(i, action);
            insertedActions.add(copy);
        }
        dtMutantSet.addAll(insertedActions);
    }
    public void omitRuleFromDT(DecisionTable originalDT){
        for(int i = 0; i < originalDT.getRuleList().size(); i++){
            DecisionTable copy = originalDT.copyOfDecisionTable();
            copy.getRuleList().remove(i);
            omittedRules.add(copy);
        }

        dtMutantSet.addAll(omittedRules);
    }
    public void insertRuleInDT(DecisionTable originalDT, Rule rule){
        int n = originalDT.getRuleList().get(0).getConditionPairs().size();

        if (originalDT.getRuleList().size() < Math.pow(2,n)) {
            DecisionTable copy = originalDT.copyOfDecisionTable();
            copy.getRuleList().add(rule);
            insertedRules.add(copy);
        }
        dtMutantSet.addAll(insertedRules);
    }

    public static void main(String[] args) {
        DecisionTable dt = new DecisionTable("JFEIFh");
        dt.getDTFromFile("src\\resources\\Ventilation.txt");

        Rule r = new Rule(5);
        r.addPair(new Condition(0,"BUtton pressed?"), true);
        r.addPair(new Condition(1,"Ventilation On?"), true);
        r.addPair(new Condition(2,"Ventilation Off?"), true);
        r.addPair(new Action(0,"Start ventilation"), true);
        r.addPair(new Action(1,"Stop ventilation"), false);
        r.addPair(new Action(2,"No action"), false);

        DTMutationOperators dtmo = new DTMutationOperators();
        dtmo.insertRuleInDT(dt, r);

        System.out.println(dtmo.getInsertedRules());
    }

    public List<DecisionTable> getDtMutantSet(){
        return dtMutantSet;
    }
    public List<DecisionTable> getOmittedActions(){
        return omittedActions;
    }
    public List<DecisionTable> getInsertedActions(){
        return insertedActions;
    }
    public List<DecisionTable> getOmittedRules(){
        return omittedRules;
    }
    public List<DecisionTable> getInsertedRules(){
        return insertedRules;
    }
}
