package dt_mtbdd_converter_mutator;

import MTBDD.MTBDD;
import decision_table.DecisionTable;

public class Runnnn {

    public static void main(String[] args) {
        Converter c = new Converter();

        DecisionTable r_dt = new DecisionTable("R_DT");
        r_dt.getDTFromFile("src/resources/Ventilation1.txt");
        System.out.println(r_dt);

        MTBDD r_mtbdd = c.DT_into_MTBDD(r_dt);
        System.out.println(r_mtbdd);


        DecisionTable r_dt1 = c.MTBDD_into_DT(r_mtbdd);
        System.out.println(r_dt1);
    }
}
