package decision_table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rule {

    private int ID;
    private List<Pair> conditionPairs;
    private List<Pair> actionPairs;

    public Rule(int ID){
        this.ID = ID;
        conditionPairs = new ArrayList<>();
        actionPairs = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public List<Pair> getConditionPairs() {
        return conditionPairs;
    }
    public void setConditionPairs(List<Pair> conditionPairs) {
        this.conditionPairs = conditionPairs;
    }
    public List<Pair> getActionPairs() {
        return actionPairs;
    }
    public void setActionPairs(List<Pair> actionPairs) {
        this.actionPairs = actionPairs;
    }

    public void addPair(Pair pair){
        if (pair.getValue() instanceof Condition)
            conditionPairs.add(pair);
        else if (pair.getValue() instanceof Action)
            actionPairs.add(pair);
    }
    public void addPair(Object value, Boolean aBoolean){
        Pair pair = new Pair(value, aBoolean);
        addPair(pair);
    }
    public void addPairs(List<Pair> pairs){
        pairs.forEach(this::addPair);
    }

    public boolean compareActionPairs(Rule rule){
        return this.getActionPairs().equals(rule.getActionPairs());
    }
    public boolean compareConditionPairs(Rule rule){
        int count = 0;

        if (this.compareActionPairs(rule)){
            for (int i = 0; i < this.getConditionPairs().size(); i++){
                if (conditionPairs.get(i).getValue().equals(rule.getConditionPairs().get(i).getValue())) {
                    if (!Utility.compareTwoBooleans(conditionPairs.get(i).getaBoolean(), rule.getConditionPairs().get(i).getaBoolean()))
                        count++;
                }
                if (count > 1)
                    return false;
            }
        }
        else
            return false;

        return true;
    }
    public Rule copyOfRule(){
        Rule clonedRule = new Rule(this.getID() + 1);
        List<Pair> condition_1List = this.getConditionPairs().stream().map(Pair::copyOfPair).collect(Collectors.toCollection(ArrayList::new));
        List<Pair> action_1List = this.getActionPairs().stream().map(Pair::copyOfPair).collect(Collectors.toCollection(ArrayList::new));

        clonedRule.setActionPairs(action_1List);
        clonedRule.setConditionPairs(condition_1List);

        return clonedRule;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (object instanceof Rule) {
            Rule rule = (Rule) object;
            return (compareConditionPairs(rule));
        }

        return false;
    }
    @Override
    public String toString(){
        return "R" + ID + ": " + conditionPairs.toString() + actionPairs.toString() + "\n";
    }
}
