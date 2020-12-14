package decision_table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
     * @return {@code true} if there is only <b>one</b> or <b>zero</b> pair of {@link Pair}s with distinguishing boolean values.
     * */
    public static boolean rulesCanMerge(Rule r1, Rule r2){
        int counter = 0;

        int i = 0;
        while (i < r1.getConditionPairs().size() && counter < 2){
            Boolean b1 = r1.getConditionPairs().get(i).getaBoolean();
            Boolean b2 = r2.getConditionPairs().get(i).getaBoolean();
            if (booleansCanMerge(b1, b2))
                counter++;

            i++;
        }

        return counter < 2;
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
}
