package decision_table;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public void addPairToRule(int ruleID, Object value, Boolean aBoolean){
        if (ruleID < ruleList.size())
            ruleList.get(ruleID).addPair(new Pair(value, aBoolean));
        else {
            ruleID = ruleList.size();
            addRule(ruleID);
            this.getRuleList().get(ruleID).addPair(new Pair(value, aBoolean));
        }
    }
    @Deprecated
    public void complementOtherRules(int ruleID, Object value){
        ruleList.stream().filter(rl -> ruleID != rl.getID()).forEach(r -> r.addPair(value, false));
    }
    public void complementOtherRulesCondition(){
        Set<Condition> conditionSet = ruleList
                .stream().flatMap(t -> t.getConditionPairs().stream()).map(f -> (Condition)f.getValue())
                .collect(Collectors.toCollection(HashSet::new));

        for (Rule tempR : ruleList) {
            List<Condition> conditions = tempR.getConditionPairs().stream().map(x -> (Condition) x.getValue()).collect(Collectors.toList());
            conditionSet.stream().filter(Predicate.not(conditions::contains)).forEach(x -> tempR.addPair(x, false));
            tempR.setConditionPairs(tempR.getConditionPairs().stream().sorted().collect(Collectors.toList()));
        }
    }
    public void complementOtherRulesAction(){
        Set<Action> actionSet = ruleList
                .stream().flatMap(t -> t.getActionPairs().stream()).map(f -> (Action)f.getValue())
                .collect(Collectors.toCollection(HashSet::new));

        for (Rule tempR : ruleList) {
            List<Action> actions = tempR.getActionPairs().stream().map(x -> (Action) x.getValue()).collect(Collectors.toList());
            actionSet.stream().filter(Predicate.not(actions::contains)).forEach(x -> tempR.addPair(x, false));
            tempR.setActionPairs(tempR.getActionPairs().stream().sorted().collect(Collectors.toList()));
        }
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
    /**
     * Use this method before {@link DecisionTable#expandDT()} to be sure whether this {@link DecisionTable} needs expansion or not.
     * @return {@code true} if {@link DecisionTable} has at least one "don't care".
     * */
    public boolean isDTShortened(){
        return ruleList.stream().anyMatch(DecisionTable::isRuleShortened);
    }
    /**
     * Checks current {@link Rule} for shortness.
     * @param rule to be checked.
     * @return {@code true} if this {@link Rule} has "don't care".
     * */
    public static boolean isRuleShortened(Rule rule){
        return rule.getConditionPairs().stream().map(Pair::getaBoolean).anyMatch(Objects::isNull);
    }
    /**
     * This method gets rid of "don't care" values in {@link Rule} if it is required.
     * As a result a number of {@link Rule}s can drastically increase.
     * */
    public void expandDT() {
        while (isDTShortened())
            for (int i = 0; i < ruleList.size(); i++)
                for (int j = 0; j < ruleList.get(i).getConditionPairs().size(); j++)
                    if (ruleList.get(i).getConditionPairs().get(j).getaBoolean() == null) {
                        ruleList.get(i).getConditionPairs().get(j).setaBoolean(false);
                        ruleList.add(i + 1, ruleList.get(i).copyOfRule());
                        ruleList.get(i + 1).getConditionPairs().get(j).setaBoolean(true);
                    }

        for (int i = 0; i < ruleList.size(); i++)
            if (i != ruleList.get(i).getID())
                ruleList.get(i).setID(i);
    }
    /**
     * This method reduces the number of rules, basically by merging {@link Rule}s.
     * */
    public void shortenDT(){
        Map<Action, List<Rule>> map = Utility.combineRulesByAction(this);

        for(Map.Entry<Action, List<Rule>> entry: map.entrySet()){
            if (entry.getValue().size() > 1){
                List<Rule> thisActionsRules = entry.getValue();
                List<Rule> currentList = new ArrayList<>(thisActionsRules);
                List<Rule> intermediateRules = new ArrayList<>();
                List<Rule> impossibleToMerge = new ArrayList<>();
                List<Integer> timesUsed;

                while(Utility.ruleListForMerging(currentList)){
                    int currentSize = currentList.size();
                    timesUsed = IntStream.of(new int[currentSize]).boxed().collect(Collectors.toList());

                    for(int i = 0; i < currentSize; i++){
                        Rule r1 = currentList.get(i);
                        for (int j = i; j < currentSize; j++){
                            Rule r2 = currentList.get(j);
                            if (j != i){
                                int pID = Utility.rulesCanMerge(r1, r2);
                                if (pID != -1){
                                    Rule mergedRule = r1.copyOfRule();
                                    mergedRule.getConditionPairs().get(pID).setaBoolean(null);
                                    intermediateRules.add(mergedRule);
                                    //currentList.add(mergedRule);
                                    timesUsed.set(i, timesUsed.get(i) + 1);
                                    timesUsed.set(j, timesUsed.get(j) + 1);
                                }
                            }
                        }
                    }
                    for (int u = 0; u < timesUsed.size(); u++)
                        if (timesUsed.get(u) == 0)
                            impossibleToMerge.add(currentList.get(u));
                    currentList.clear();
                    currentList.addAll(intermediateRules);
                    intermediateRules.clear();
                }
                if (!currentList.isEmpty() && currentList.size() > 1) {
                    for (int i = 0; i < currentList.size(); i++)
                        for (int j = 0; j < currentList.size(); j++)
                            if (j != i)
                                if (currentList.get(i).equals(currentList.get(j)))
                                    currentList.remove((int)j);
                    impossibleToMerge.addAll(currentList);
                }
                entry.setValue(impossibleToMerge);
            }
        }

        ruleList = map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }
    /*public void shortenDT(){
        Map<Action, List<Rule>> map = Utility.combineRulesByAction(this);

        for(Map.Entry<Action, List<Rule>> entry: map.entrySet()){
            if (entry.getValue().size() > 1){
                List<Rule> thisActionsRules = entry.getValue();
                List<Rule> currentList = new ArrayList<>(thisActionsRules);
                List<Rule> impossibleToMerge = new ArrayList<>();
                List<Integer> timesUsed;

                while(Utility.ruleListForMerging(currentList)){
                    int currentSize = currentList.size();
                    timesUsed = IntStream.of(new int[currentSize]).boxed().collect(Collectors.toList());

                    for(int i = 0; i < currentSize; i++){
                        Rule r1 = currentList.get(i);
                        for (int j = i; j < currentSize; j++){
                            Rule r2 = currentList.get(j);
                            if (j != i){
                                int pID = Utility.rulesCanMerge(r1, r2);
                                if (pID != -1){
                                    Rule mergedRule = r1.copyOfRule();
                                    mergedRule.getConditionPairs().get(pID).setaBoolean(null);
                                    currentList.add(mergedRule);
                                    timesUsed.set(i, timesUsed.get(i) + 1);
                                    timesUsed.set(j, timesUsed.get(j) + 1);
                                }
                            }
                        }
                    }
                    for (int u = 0; u < timesUsed.size(); u++)
                        if (timesUsed.get(u) == 0)
                            impossibleToMerge.add(currentList.get(u));
                    currentList.subList(0, currentSize).clear();
                }
                if (!currentList.isEmpty() && currentList.size() > 1) {
                    for (int i = 0; i < currentList.size(); i++)
                        for (int j = 0; j < currentList.size(); j++)
                            if (j != i)
                                if (currentList.get(i).equals(currentList.get(j)))
                                    currentList.remove((int)j);
                    impossibleToMerge.addAll(currentList);
                }
                entry.setValue(impossibleToMerge);
            }
        }

        ruleList = map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }*/
    /**
     * Creates a copy of this {@link DecisionTable}
     * @return copy object of this Decision Table.
     * */
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

    @Deprecated
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