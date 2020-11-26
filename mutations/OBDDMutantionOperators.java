package mutations;

import decision_table.DecisionTable;
import ordered_binary_decision_diagram.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class OBDDMutantionOperators {

    private List<OrderedBinaryDecisionDiagram> obddMutantSet;
    private List<OrderedBinaryDecisionDiagram> obddInsertedNodeMutants;
    private List<OrderedBinaryDecisionDiagram> obddOmittedNodeMutants;

    /**
     * Use this constructor if you need to generate certain kind of mutants.
     * */
    public OBDDMutantionOperators(){
        obddInsertedNodeMutants = new ArrayList<>();
        obddOmittedNodeMutants = new ArrayList<>();
        obddMutantSet = new ArrayList<>();
    }
    /**
     * Use this constructor if you do not want manually generate all mutants.
     * */
    public OBDDMutantionOperators(OrderedBinaryDecisionDiagram originalOBDD, Node tNode) {
        obddInsertedNodeMutants = new ArrayList<>();
        obddOmittedNodeMutants = new ArrayList<>();
        obddMutantSet = new ArrayList<>();

        terminalNodeInsertion(originalOBDD, tNode);
        terminalNodeOmission(originalOBDD);
    }

    /**
     * Generates a set of {@link OrderedBinaryDecisionDiagram} mutants, by inserting terminal {@link Node} whenever it is possible.
     * <p>Insertions are applied once for each mutant.</p>
     * */
    public void terminalNodeInsertion(OrderedBinaryDecisionDiagram obdd, Node newTerminalNode){
        BiFunction<Integer, Boolean, OrderedBinaryDecisionDiagram> reuse = (i, bool) -> {
            OrderedBinaryDecisionDiagram copy = obdd.makeACopy();
            copy.fill_NTNList();

            Node tempNTN = copy.getTheLowestNonterminalNodes().get(i);
            Node newNode = newTerminalNode.cloner();
            copy.addTerminalNode(tempNTN, bool, newNode);
            copy.fill_TNLists();

            return copy;
        };

        if (obdd.getTheLowestNonterminalNodes().isEmpty())
            obdd.fill_NTNList();
        List<Node> originalNTNList = obdd.getTheLowestNonterminalNodes();

        for (int i = 0; i < originalNTNList.size(); i++){
            Node nn = originalNTNList.get(i);

            if (nn.getLeftChildNode() == null && nn.getRightChildNode() == null){
                obddInsertedNodeMutants.add(reuse.apply(i, true));
                obddInsertedNodeMutants.add(reuse.apply(i, false));
            } else if (nn.getLeftChildNode() == null && nn.getRightChildNode() != null)
                obddInsertedNodeMutants.add(reuse.apply(i, false));
            else if (nn.getLeftChildNode() != null && nn.getRightChildNode() == null)
                obddInsertedNodeMutants.add(reuse.apply(i,true));
        }

        obddMutantSet.addAll(obddInsertedNodeMutants);
    }
    /**
     * Generates a set of {@link OrderedBinaryDecisionDiagram} mutants, by omission of all terminal {@link Node}s.
     * <p>Omissions are applied once for each mutant.</p>
     * */
    public void terminalNodeOmission(OrderedBinaryDecisionDiagram obdd){
        Function<Integer, OrderedBinaryDecisionDiagram> reuse = (index) -> {
            OrderedBinaryDecisionDiagram copy = obdd.makeACopy();
            copy.fill_NTNList();
            copy.fill_TNLists();

            copy.deleteTerminalNode(index);

            return copy;
        };

        if (obdd.getTerminalNodes().isEmpty() || obdd.getTheLowestNonterminalNodes().isEmpty()) {
            obdd.fill_NTNList();
            obdd.fill_TNLists();
        }
        List<Node> tnList = obdd.getTerminalNodes();

        for (int i = 0; i < tnList.size(); i++)
            obddOmittedNodeMutants.add(reuse.apply(i));

        obddMutantSet.addAll(obddOmittedNodeMutants);
    }

    public List<OrderedBinaryDecisionDiagram> getObddMutantSet() {
        return obddMutantSet;
    }
    public List<OrderedBinaryDecisionDiagram> getObddInsertionMutantSet(){
        return obddInsertedNodeMutants;
    }
    public List<OrderedBinaryDecisionDiagram> getObddOmissionMutantSet(){
        return obddOmittedNodeMutants;
    }

    public static void main(String[] args) {
        DecisionTable dt = new DecisionTable("eojvh");
        dt.getDTFromFile("src\\resources\\Ventilation.txt");
        System.out.println(dt);

        DT_OBDD_Converter c = new DT_OBDD_Converter();
        OrderedBinaryDecisionDiagram o = c.DTtoOBDD(dt);
        OBDDMutantionOperators mm = new OBDDMutantionOperators();
        mm.terminalNodeOmission(o);

        System.out.println(c.OBDDsToDTs(mm.getObddOmissionMutantSet()));
    }
}
