package decision_table;

import java.util.*;
import java.util.stream.Collectors;

public class Utility {

    public Boolean stringToBoolean(String value){
        if (value.matches("[tTyY1]"))
            return true;
        else if (value.matches("[fFnN0]"))
            return false;
        else
            return null;
    }

    public String[] stringToArray(String string){
        String[] arr = new String[string.length()];

        for (int i = 0; i < arr.length; i++)
            arr[i] = string.substring(i, i+1);

        return arr;
    }

    public static boolean compareTwoBooleans(Boolean b1, Boolean b2){
        if (b1 == null || b2 == null)
            return true;
        else
            return b1.equals(b2);
    }
    /**
     * This method checks two booleans for merging.
     * @param b1 is the boolean value of the first {@link Rule}'s pair
     * @param b2 is the boolean value of the second {@link Rule}'s pair
     * @return {@code true} if inputs are not same.
     * */
    public static boolean booleansCanMerge(Boolean b1, Boolean b2){
        if (b1 == b2)
            return false;
        else
            return b1 != null || b2 != null;
    }
    /**
     * Checks two {@link Rule}s for the possibility of merging.
     * @param r1 first rule
     * @param r2 first rule
     * @return {@code -1} if there is no pair of {@link Pair}s with distinguishing boolean values or any {@code int} which represents ID of Pairs.
     **/
    public static int rulesCanMerge(Rule r1, Rule r2){
        int counter = 0;
        int ID = 0;

        int i = 0;
        while (i < r1.getConditionPairs().size() && counter < 2){
            Boolean b1 = r1.getConditionPairs().get(i).getaBoolean();
            Boolean b2 = r2.getConditionPairs().get(i).getaBoolean();
            if (booleansCanMerge(b1, b2)) {
                ID = i;
                counter++;
            }
            i++;
        }

        if (counter == 1)
            return ID;
        else
            return -1;
    }
    /**
     * Combines {@link Rule}s with the same {@link Action}s.
     * @param decisionTable .
     * @return list of rules mapped to their {@link Action}s
     * */
    public static Map<Action, List<Rule>> combineRulesByAction(DecisionTable decisionTable){
        Map<Action, List<Rule>> groupedRules = new HashMap<>();

        for(Rule r : decisionTable.getRuleList()){
            Action a = r.getActionPairs().stream().filter(Pair::getaBoolean).map(x -> (Action)x.getValue()).findFirst().get();
            if (groupedRules.containsKey(a))
                groupedRules.get(a).add(r);
            else {
                groupedRules.put(a, new ArrayList<>());
                groupedRules.get(a).add(r);
            }
        }

        return groupedRules;
    }
    /**
     * This is a wrapper of {@link Utility#distributeRule(Rule, Set, Map)} distributeRule method.
     * */
    public static void multipleCountTrues(List<Rule> rules, Map<Integer, Map<Set<Integer>, Rule>> map){
        rules.forEach(r -> distributeRule(r, Set.of(r.getID()), map));
    }
    /**
     * Puts {@link Rule} in certain group based on its number of {@code T} values.
     * @param rule under investigation.
     * @param indices index.
     * @param map structure which stores rules according to the number of T
     * */
    public static void distributeRule(Rule rule, Set<Integer> indices, Map<Integer, Map<Set<Integer>, Rule>> map) {
        groupMappings(countTrues(rule), indices, rule, map);
    }
    /**
     * Counts the number of T value pairs in conditions.
     * */
    public static int countTrues(Rule rule){
        int truesNumber = 0;
        for (Pair p: rule.getConditionPairs())
            if (p.getaBoolean() != null)
                if (p.getaBoolean())
                    truesNumber++;

       return truesNumber;
    }
    /**
     * Allocates values with indices based on key in map structure.
     * */
    public static <K,U,T> void groupMappings(K key, Set<U> indices, T t, Map<K, Map<Set<U>, T>> globalMap){
        if (globalMap.containsKey(key))
            globalMap.get(key).put(indices, t);
        else {
            globalMap.put(key, new HashMap<>());
            globalMap.get(key).put(indices, t);
        }
    }
    /**
     * Saves indices of the used {@link Rule}s.
     * @param key is the group to which recently used temporary rule belongs.
     * @param indices the set of integers which temporary form the index of the recently used temporary rule.
     * @param map is the structure where all used temporary rules were saved.
     * */
    public static <K,U> void rememberUsedRules(K key, Set<U> indices, Map<K, Set<Set<U>>> map){
        if (map.containsKey(key)){
            map.get(key).add(indices);
        }else {
            map.put(key, new HashSet<>());
            map.get(key).add(indices);
        }
    }
    /**
     * Removes all used rules and leaves only unused ones.
     * @param m1 structure of all rules.
     * @param m2 structure of unused rules.
     * @return {@code list} of unused rules.
     * */
    public static <K,U,T> List<T> extractUnusedItems(Map<K, Map<Set<U>, T>> m1, Map<K, Set<Set<U>>> m2){
        for (Map.Entry<K, Map<Set<U>, T>> entry : m1.entrySet()) {
            K key = entry.getKey();
            Map<Set<U>, T> currentGroup = entry.getValue();
            Set<Set<U>> indicesSet = m2.get(key) != null ? m2.get(entry.getKey()) : new HashSet<>(new HashSet<>());
            indicesSet.forEach(currentGroup::remove);
        }
        return m1.entrySet().stream().flatMap(k -> k.getValue().values().stream()).collect(Collectors.toList());
    }
    /**
     * Generates a new set of integers which represents an index of a new intermediate {@link Rule}.
     * @param s1 an index of the first rule.
     * @param s2 an index of the second rule.
     * @return a new index in the form of integer set.
     * */
    public static <T> Set<T> newIndicesCombination(Set<T> s1, Set<T> s2){
        Set<T> set = new HashSet<>(s1);
        set.addAll(s2);

        return set;
    }
    /**
     * Creates a new {@link Rule} from a given one with an updated "don't care" {@link Pair}.
     * @param r rule.
     * @param ID a pair ID for update.
     * @return a new rule.
     * */
    public static Rule merge2Rules(Rule r, int ID){
        Rule newRule = r.copyOfRule();
        newRule.getConditionPairs().get(ID).setaBoolean(null);
        return newRule;
    }
}
