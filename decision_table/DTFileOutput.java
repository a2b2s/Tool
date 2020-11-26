package decision_table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DTFileOutput {

    private DecisionTable dt;
    private List<DecisionTable> dts;
    private String name;

    public DTFileOutput(DecisionTable dt, String name){
        this.name = name;
        this.dt = dt;
        writeDTToFile();
    }
    public DTFileOutput(List<DecisionTable> dts, String name){
        this.name = name;
        this.dts = dts;
        writeDTsToFile();
    }

    private void writeDTToFile(){
        File f = new File("src\\resources\\" + name + ".txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(f))){
            writer.write(String.valueOf(prepareDTinTextForm(dt)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeDTsToFile(){
        File f = new File("src\\resources\\" + name + ".txt");
        List<String> strs = dts.stream().map(this::prepareDTinTextForm).map(StringBuilder::toString).collect(Collectors.toList());

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(f))){
            final int[] c = {0};
            strs.forEach(s -> {
                try {
                    writer.write("~~~~~~~~~~~~~~~~~~~~DT №" + c[0] + "~~~~~~~~~~~~~~~~~~~~\n" + s + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                c[0]++;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder prepareDTinTextForm(DecisionTable dt){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < dt.getRuleList().size(); i++){
            Rule temp = dt.getRuleList().get(i);
            stringBuilder.append("R").append(temp.getID()).append("-> ");

            for (int j = 0; j < temp.getConditionPairs().size(); j++)
                stringBuilder.append(temp.getConditionPairs().get(j)).append("/");
            for (int j = 0; j < temp.getActionPairs().size(); j++)
                stringBuilder.append(temp.getActionPairs().get(j)).append("/");

            stringBuilder.append("\n");
        }

        return stringBuilder;
    }
}
