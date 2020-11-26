package decision_table;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTFileReader {

    private String path;

    public DTFileReader(String path){
        this.path = path;
    }

    private List<String> extractData(){
        File file = new File(path);

        try(BufferedReader br = new BufferedReader(new java.io.FileReader(file))){
            return br.lines().map(e -> e.replaceAll(";\\s*", ";")).collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    private List<List<String>> divideDTParts(){
        List<String> list = extractData();

        List<String> list4Condition = list.stream().takeWhile(x -> !x.matches("^\\W*Actions*\\W*$"))
                .collect(Collectors.toCollection(ArrayList::new));
        List<String> list4Action = list.stream().dropWhile(x -> !x.matches("^\\W*Actions*\\W*$"))
                .collect(Collectors.toCollection(ArrayList::new));
        list4Action.remove(0);

        List<List<String>> both = new ArrayList<>();
        both.add(list4Condition);
        both.add(list4Action);

        return both;
    }
    private List<List<String[]>> splitData(){
        List<List<String>> lists = divideDTParts();

        List<List<String[]>> lists2 = new ArrayList<>();
        List<String[]> dataCondition = new ArrayList<>();
        List<String[]> dataAction = new ArrayList<>();

        for (int i=0; i < lists.get(0).size(); i++){
            String[] split = lists.get(0).get(i).split(";", 2);
            dataCondition.add(split);
        }
        for (int i=0; i < lists.get(1).size(); i++){
            String[] split = lists.get(1).get(i).split(";", 2);
            dataAction.add(split);
        }
        lists2.add(dataCondition);
        lists2.add(dataAction);
        return lists2;
    }

    public List<List<Pair>> listOfPairLists(){
        List<List<String[]>> rawLists = splitData();
        List<List<Pair>> lists = new ArrayList<>();

        for (int i=0; i < rawLists.get(0).size(); i++)
            lists.add(conditionPairs(i, rawLists.get(0).get(i)));
        for (int i=0; i < rawLists.get(1).size(); i++)
            lists.add(actionPairs(i, rawLists.get(1).get(i)));

        return changeOrder(lists);
    }

    private List<List<Pair>> changeOrder(List<List<Pair>> pl){
        int rows = pl.get(0).size();
        int cols = pl.size();

        List<List<Pair>> newListOfPairLists = new ArrayList<>(rows);

        for (int i = 0; i < rows; i++){
            List<Pair> temp = new ArrayList<>();

            for (int j = 0; j < cols; j++)
                temp.add(pl.get(j).get(i));

            newListOfPairLists.add(temp);
        }

        return newListOfPairLists;
    }

    private List<Pair> conditionPairs(int ID, String[] set){
        Utility sw = new Utility();
        String[] values = sw.stringToArray(set[1]);

        return List.of(values).stream().map(o -> new Pair(new Condition(ID, set[0]), sw.stringToBoolean(o))).collect(Collectors.toList());
    }
    private List<Pair> actionPairs(int ID, String[] set){
        Utility sw = new Utility();
        String[] values = sw.stringToArray(set[1]);

        return List.of(values).stream().map(o -> new Pair(new Action(ID, set[0]), sw.stringToBoolean(o))).collect(Collectors.toList());
    }

    public void setPath(String path){
        this.path = path;
    }
    public String getPath(){
        return path;
    }
}
