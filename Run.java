public class Run {

    /*public static void main(String[] args) {
        DecisionTable dt = new DecisionTable("VentilationDT");
        dt.getDTFromFile("src\\resources\\Ventilation.txt");
        System.out.println(dt);

        AgeApp aa = new AgeApp();
        AgeAppTest aaTest = new AgeAppTest(aa);
        List<Object> results = List.of(aa.runAgeApp("12", "ADOLESCENCE"));
        List<Boolean> bools = aaTest.testResults();

        Converter c = new Converter();
        OrderedBinaryDecisionDiagram obdd = c.DTtoOBDD(dt);

        Action action = new Action(3, "-_-_-_-_-_-");
        Event n =new Event();
        n.setDecisionTable(List.of(action));
        //n.setDecisionTable(List.of(action));
        DTMutationOperators m1 = new DTMutationOperators(dt, action);
        List<DecisionTable> dtMutants1 = m1.getDtMutantSet();

        OBDDMutantionOperators m2 = new OBDDMutantionOperators(obdd,n);
        List<OrderedBinaryDecisionDiagram> obddMutants = m2.getObddMutantSet();
        List<DecisionTable> dtMutants2 = c.OBDDsToDTs(obddMutants);

        System.out.println(dtMutants1 + "\n\n");
        System.out.println(dtMutants2);

        DTFileOutput dtFileOutput1 = new DTFileOutput(dtMutants1, "Direct mutation resutls");
        DTFileOutput dtFileOutput2 = new DTFileOutput(dtMutants2, "Not direct mutation results");

        List<DecisionTable> equal = dtMutants1.stream().filter(dtMutants2::contains).collect(Collectors.toList());
        System.out.println(equal);
        List<DecisionTable> bigList = new ArrayList<>(dtMutants1);
        bigList.addAll(dtMutants2);
        System.out.println(bigList.size());

        bigList.forEach(dts -> dts.test(bools,results));
    }*/
    /*public static void main(String[] args) {
        DecisionTable dt = new DecisionTable("VentilationDT");
        dt.getDTFromFile("src\\resources\\Ventilation.txt");
        System.out.println(dt);

        DTMutationOperators dtmo = new DTMutationOperators();

        dtmo.omitActionFromDT(dt);
        dtmo.omitRuleFromDT(dt);
        Action_1 testAction1 = new Action_1(3,"XXX");
        dtmo.insertActionInDT(dt, testAction1);
        Rule_1 newR = new Rule_1(3);
        newR.addPairs(List.of(
                new Pair_1(new Condition_1(1,"QWE"), true),
                new Pair_1(new Condition_1(2,"RTY"), true),
                new Pair_1(new Condition_1(3,"UIO"), true),
                new Pair_1(new Action_1(1,"---"), true),
                new Pair_1(new Action_1(2,"+++"), true),
                new Pair_1(new Action_1(3,"==="), true)
        ));
        dtmo.insertRuleInDT(dt, newR);

        System.out.println(dtmo.getInsertedRules());
    }*/
}