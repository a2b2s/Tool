package mutation_operators;

import MTBDD.MTBDD;
import decision_table.DecisionTable;
import dt_mtbdd_converter_mutator.Converter;
import mutations.DT_OBDD_Converter;
import ordered_binary_decision_diagram.Node;
import ordered_binary_decision_diagram.OrderedBinaryDecisionDiagram;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MTBDDMutantionOperators {

    private List<MTBDD> mtbddMutantSet;
    private List<MTBDD> mtbddInsertedNodeMutants;
    private List<MTBDD> mtbddOmittedNodeMutants;
    private List<MTBDD> mtbddCorruptedNodeMutants;
    private List<MTBDD> mtbddInsertedEdgeMutants;
    private List<MTBDD> mtbddOmittedEdgeMutants;
    private List<MTBDD> mtbddCorruptedEdgeMutants;
    private List<MTBDD> mtbddSwitchedEdgeMutants;

    /**
     * Use this constructor if you need to generate certain kind of mutants.
     * */
    public MTBDDMutantionOperators(){
        mtbddInsertedNodeMutants = new ArrayList<>();
        mtbddOmittedNodeMutants = new ArrayList<>();
        mtbddCorruptedNodeMutants = new ArrayList<>();
        mtbddInsertedEdgeMutants = new ArrayList<>();
        mtbddOmittedEdgeMutants = new ArrayList<>();
        mtbddCorruptedEdgeMutants = new ArrayList<>();
        mtbddSwitchedEdgeMutants = new ArrayList<>();
        mtbddMutantSet = new ArrayList<>();
    }
    /**
     * Use this constructor if you do not want manually generate all mutants.
     * */
    public MTBDDMutantionOperators(MTBDD originalMTBDD, Node tNode) {
        mtbddInsertedNodeMutants = new ArrayList<>();
        mtbddOmittedNodeMutants = new ArrayList<>();
        mtbddMutantSet = new ArrayList<>();

        terminalNodeInsertion(originalMTBDD, tNode);
        terminalNodeOmission(originalMTBDD);
    }

    /**
     * Generates a set of {@link OrderedBinaryDecisionDiagram} mutants, by inserting terminal {@link Node} whenever it is possible.
     * <p>Insertions are applied once for each mutant.</p>
     * */
    public void terminalNodeInsertion(MTBDD mtbdd, Node newTerminalNode){
        BiFunction<Integer, Boolean, MTBDD> reuse = (i, bool) -> {
            MTBDD copy = mtbdd.getCopy();
            /*copy.fill_NTNList();

            Node tempNTN = copy.getTheLowestNonterminalNodes().get(i);
            Node newNode = newTerminalNode.cloner();
            copy.addTerminalNode(tempNTN, bool, newNode);
            copy.fill_TNLists();*/

            return copy;
        };
        /*if (obdd.getTheLowestNonterminalNodes().isEmpty())
            obdd.fill_NTNList();
        List<Node> originalNTNList = obdd.getTheLowestNonterminalNodes();

        for (int i = 0; i < originalNTNList.size(); i++){
            Node nn = originalNTNList.get(i);

            if (nn.getLeftChildNode() == null && nn.getRightChildNode() == null){
                //mtbddInsertedNodeMutants.add(reuse.apply(i, true));
               // mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            } *//*else if (nn.getLeftChildNode() == null && nn.getRightChildNode() != null)
                mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            else if (nn.getLeftChildNode() != null && nn.getRightChildNode() == null)
                mtbddInsertedNodeMutants.add(reuse.apply(i,true));*//*
        }*/
        mtbddMutantSet.addAll(mtbddInsertedNodeMutants);
    }
    /**
     * Generates a set of {@link OrderedBinaryDecisionDiagram} mutants, by omission of all terminal {@link Node}s.
     * <p>Omissions are applied once for each mutant.</p>
     * */
    public void terminalNodeOmission(MTBDD mtbdd){
        Function<Integer, MTBDD> reuse = (index) -> {
            MTBDD copy = mtbdd.getCopy();
            /*copy.fill_NTNList();
            copy.fill_TNLists();

            copy.deleteTerminalNode(index);*/

            return copy;
        };

        /*if (obdd.getTerminalNodes().isEmpty() || obdd.getTheLowestNonterminalNodes().isEmpty()) {
            obdd.fill_NTNList();
            obdd.fill_TNLists();
        }
        List<Node> tnList = obdd.getTerminalNodes();

        for (int i = 0; i < tnList.size(); i++)
            mtbddOmittedNodeMutants.add(reuse.apply(i));*/

        mtbddMutantSet.addAll(mtbddOmittedNodeMutants);
    }
    public void terminalNodeCorruption(MTBDD mtbdd, Node anotherNode){
        Function<Integer, MTBDD> reuse = (index) -> {
            MTBDD copy = mtbdd.getCopy();
            /*copy.fill_NTNList();
            copy.fill_TNLists();

            copy.deleteTerminalNode(index);*/

            return copy;
        };

        mtbddMutantSet.addAll(mtbddCorruptedNodeMutants);
    }
    public void edgeInsertion(MTBDD mtbdd){
        BiFunction<Integer, Boolean, MTBDD> reuse = (i, bool) -> {
            MTBDD copy = mtbdd.getCopy();
            /*copy.fill_NTNList();

            Node tempNTN = copy.getTheLowestNonterminalNodes().get(i);
            Node newNode = newTerminalNode.cloner();
            copy.addTerminalNode(tempNTN, bool, newNode);
            copy.fill_TNLists();*/

            return copy;
        };
        /*if (obdd.getTheLowestNonterminalNodes().isEmpty())
            obdd.fill_NTNList();
        List<Node> originalNTNList = obdd.getTheLowestNonterminalNodes();

        for (int i = 0; i < originalNTNList.size(); i++){
            Node nn = originalNTNList.get(i);

            if (nn.getLeftChildNode() == null && nn.getRightChildNode() == null){
                //mtbddInsertedNodeMutants.add(reuse.apply(i, true));
               // mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            } *//*else if (nn.getLeftChildNode() == null && nn.getRightChildNode() != null)
                mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            else if (nn.getLeftChildNode() != null && nn.getRightChildNode() == null)
                mtbddInsertedNodeMutants.add(reuse.apply(i,true));*//*
        }*/
        mtbddMutantSet.addAll(mtbddInsertedEdgeMutants);
    }
    public void edgeOmission(MTBDD mtbdd){
        BiFunction<Integer, Boolean, MTBDD> reuse = (i, bool) -> {
            MTBDD copy = mtbdd.getCopy();
            /*copy.fill_NTNList();

            Node tempNTN = copy.getTheLowestNonterminalNodes().get(i);
            Node newNode = newTerminalNode.cloner();
            copy.addTerminalNode(tempNTN, bool, newNode);
            copy.fill_TNLists();*/

            return copy;
        };
        /*if (obdd.getTheLowestNonterminalNodes().isEmpty())
            obdd.fill_NTNList();
        List<Node> originalNTNList = obdd.getTheLowestNonterminalNodes();

        for (int i = 0; i < originalNTNList.size(); i++){
            Node nn = originalNTNList.get(i);

            if (nn.getLeftChildNode() == null && nn.getRightChildNode() == null){
                //mtbddInsertedNodeMutants.add(reuse.apply(i, true));
               // mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            } *//*else if (nn.getLeftChildNode() == null && nn.getRightChildNode() != null)
                mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            else if (nn.getLeftChildNode() != null && nn.getRightChildNode() == null)
                mtbddInsertedNodeMutants.add(reuse.apply(i,true));*//*
        }*/
        mtbddMutantSet.addAll(mtbddOmittedEdgeMutants);
    }
    public void edgeCorruption(MTBDD mtbdd){
        BiFunction<Integer, Boolean, MTBDD> reuse = (i, bool) -> {
            MTBDD copy = mtbdd.getCopy();
            /*copy.fill_NTNList();

            Node tempNTN = copy.getTheLowestNonterminalNodes().get(i);
            Node newNode = newTerminalNode.cloner();
            copy.addTerminalNode(tempNTN, bool, newNode);
            copy.fill_TNLists();*/

            return copy;
        };
        /*if (obdd.getTheLowestNonterminalNodes().isEmpty())
            obdd.fill_NTNList();
        List<Node> originalNTNList = obdd.getTheLowestNonterminalNodes();

        for (int i = 0; i < originalNTNList.size(); i++){
            Node nn = originalNTNList.get(i);

            if (nn.getLeftChildNode() == null && nn.getRightChildNode() == null){
                //mtbddInsertedNodeMutants.add(reuse.apply(i, true));
               // mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            } *//*else if (nn.getLeftChildNode() == null && nn.getRightChildNode() != null)
                mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            else if (nn.getLeftChildNode() != null && nn.getRightChildNode() == null)
                mtbddInsertedNodeMutants.add(reuse.apply(i,true));*//*
        }*/
        mtbddMutantSet.addAll(mtbddCorruptedEdgeMutants);
    }
    public void edgeSwitch(MTBDD mtbdd){
        BiFunction<Integer, Boolean, MTBDD> reuse = (i, bool) -> {
            MTBDD copy = mtbdd.getCopy();
            /*copy.fill_NTNList();

            Node tempNTN = copy.getTheLowestNonterminalNodes().get(i);
            Node newNode = newTerminalNode.cloner();
            copy.addTerminalNode(tempNTN, bool, newNode);
            copy.fill_TNLists();*/

            return copy;
        };
        /*if (obdd.getTheLowestNonterminalNodes().isEmpty())
            obdd.fill_NTNList();
        List<Node> originalNTNList = obdd.getTheLowestNonterminalNodes();

        for (int i = 0; i < originalNTNList.size(); i++){
            Node nn = originalNTNList.get(i);

            if (nn.getLeftChildNode() == null && nn.getRightChildNode() == null){
                //mtbddInsertedNodeMutants.add(reuse.apply(i, true));
               // mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            } *//*else if (nn.getLeftChildNode() == null && nn.getRightChildNode() != null)
                mtbddInsertedNodeMutants.add(reuse.apply(i, false));
            else if (nn.getLeftChildNode() != null && nn.getRightChildNode() == null)
                mtbddInsertedNodeMutants.add(reuse.apply(i,true));*//*
        }*/
        mtbddMutantSet.addAll(mtbddSwitchedEdgeMutants);
    }

    public List<MTBDD> getMtbddMutantSet() {
        return mtbddMutantSet;
    }
    public List<MTBDD> getMtbddInsertedNodeMutantSet(){
        return mtbddInsertedNodeMutants;
    }
    public List<MTBDD> getMtbddOmittedNodeMutantSet(){
        return mtbddOmittedNodeMutants;
    }
    public List<MTBDD> getMtbddCorruptedNodeMutantSet(){
        return mtbddCorruptedNodeMutants;
    }
    public List<MTBDD> getMtbddOmittedEdgeMutants(){
        return mtbddOmittedEdgeMutants;
    }
    public List<MTBDD> getMtbddInsertedEdgeMutantSet(){
        return mtbddInsertedEdgeMutants;
    }
    public List<MTBDD> getMtbddCorruptedEdgeMutants(){
        return mtbddCorruptedEdgeMutants;
    }
    public List<MTBDD> getMtbddSwitchedEdgeMutantSet(){
        return mtbddSwitchedEdgeMutants;
    }

    /*public static void main(String[] args) {
        DecisionTable dt = new DecisionTable("eojvh");
        dt.getDTFromFile("src\\resources\\Ventilation.txt");
        System.out.println(dt);

        Converter c = new Converter();
        MTBDD mtbdd = c.DT_into_MTBDD(dt);
        MTBDDMutantionOperators mm = new MTBDDMutantionOperators();
        mm.terminalNodeOmission(o);

        System.out.println(c.OBDDsToDTs(mm.getObddOmissionMutantSet()));
    }*/
}