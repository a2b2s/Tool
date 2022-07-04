package MTBDD_NEW;

import decision_table.DecisionTable;

public class Run {

    public static void main(String[] args) {
        DecisionTable dt = new DecisionTable("VentilationDT");
        dt.getDTFromFile("src\\resources\\Ventilation.txt");
        System.out.println(dt);

        MTBDD mtbdd1 = Converter.DT_into_MTBDD(dt);
        System.out.println(mtbdd1);
    }
}
