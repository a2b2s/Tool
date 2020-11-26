package decision_table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DecisionTable {

    private String name;
    private List<Rule> ruleList;

    public DecisionTable(String name){
        this.name = name;
        this.ruleList = new ArrayList<>();
    }

    public void addRule(Rule rule){
        this.ruleList.add(rule);
    }
    public void addRule(int ruleID){
        Rule newRule = new Rule(ruleID);
        this.ruleList.add(newRule);
    }
    /*public void addPairToRule(int ruleID, DecisionTableParts dtPart, Boolean aBoolean){
        for (Rule r: ruleList){
            if (r.getID() == ruleID){
                r.addPair(new Pair(dtPart, aBoolean));
            } else {
                addRule(new Rule(ruleList.size()));
                this.getRuleList().get(ruleList.size() - 1).addPair(new Pair(dtPart, aBoolean));
            }
            break;
        }
    }*/
    public void addPairToRule(int ruleID, Object value, Boolean aBoolean){
        if (ruleID < ruleList.size())
            ruleList.get(ruleID).addPair(new Pair(value, aBoolean));
        else {
            ruleID = ruleList.size();
            addRule(ruleID);
            this.getRuleList().get(ruleID).addPair(new Pair(value, aBoolean));
        }
    }
    public void complementOtherRules(int ruleID, Object value){
        ruleList.stream().filter(rl -> ruleID != rl.getID()).forEach(r -> r.addPair(value, false));
    }
    public void addPairToRule(int ruleID, Pair pair){
        if (ruleID < ruleList.size())
            ruleList.get(ruleID).addPair(pair);
        else {
            ruleID = ruleList.size();
            addRule(ruleID);
            this.getRuleList().get(ruleID).addPair(pair);
        }
    }
    public void getDTFromFile(String filePath){
        DTFileReader fetchData = new DTFileReader(filePath);
        List<List<Pair>> lists = fetchData.listOfPairLists();

        for (int i = 0; i < lists.size(); i++){
            Rule r = new Rule(i);
            r.addPairs(lists.get(i));
            addRule(r);
        }
    }

    public DecisionTable copyOfDecisionTable(){
        DecisionTable copy = new DecisionTable(this.name);

        copy.setRuleList(ruleList.stream().map(Rule::copyOfRule).collect(Collectors.toList()));
        for (int i = 0; i < copy.ruleList.size(); i++)
            copy.getRuleList().get(i).setID(i);

        copy.setRuleList(ruleList.stream().map(Rule::copyOfRule).collect(Collectors.toList()));
        for (int i = 0; i < copy.ruleList.size(); i++)
            copy.getRuleList().get(i).setID(i);

        return copy;
    }

    public List<Action> getWorkingActions(){
        List<Action> actions = new ArrayList<>();

        for (Rule r: ruleList){
            List<Action> tempAction = r.getActionPairs()
                    .stream()
                    .filter(Pair::getaBoolean)
                    .map(x -> (Action)x.getValue())
                    .collect(Collectors.toCollection(ArrayList::new));
            actions.addAll(tempAction);
        }

        return actions.stream().sorted().distinct().collect(Collectors.toList());
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public List<Rule> getRuleList(){
        return ruleList;
    }
    public void setRuleList(List<Rule> rules){
        ruleList = rules;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (object instanceof DecisionTable) {
            DecisionTable dt = (DecisionTable) object;
            if (this.getRuleList().size() == dt.getRuleList().size()) {
                int count = 0;

                for (int i = 0; i < this.getRuleList().size(); i++) {
                    Rule thisR = this.ruleList.get(i);
                    for (int j = 0; j < dt.getRuleList().size(); j++) {
                        Rule dtR = dt.ruleList.get(j);

                        if (thisR.equals(dtR)) {
                            count++;
                            break;
                        }
                    }
                    if (count <= i)
                        break;
                }
                return count == this.getRuleList().size();
            }
            else
                return false;
        }
        return false;
    }
    @Override
    public String toString(){
        return this.name + "\n" + ruleList;
    }
}
