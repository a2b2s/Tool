package MTBDD;

import ordered_binary_decision_diagram.OrderedBinaryDecisionDiagram;

import java.util.*;

/**
 * {@code MTBDD} represents real-life Multi-Terminal Binary Decision Diagram, which is a DAG.
 * Consists of Non-terminal and Terminal nodes, which are represented as a {@link Node}.
 * As BDD can not have a duplicated nodes. Moreover, there can be only 2 terminal nodes {@code 0} and {@code 1}.
 * <p>Therefore, MTBDDs have more than 2 non-repetitive nodes.</p>
 * */
public class MTBDD {

    private String name;
    private Node root;
    /**
     * Terminal nodes in {@linkplain #MTBDD} are represented as keys of Map structure, where {@link List} of non-terminal {@link Node}s
     * are values associated to certain terminal node.
     * */
    private Map<Node, List<Node>> terminalNodes;
    /**
     * As terminal nodes can have more than 1 incoming edge, all of them are stored in Map structure.
     * */
    private Map<Node, List<Boolean>> terminalIncomingEdges;

    public MTBDD(){
        terminalNodes = new HashMap<>();
        terminalIncomingEdges = new HashMap<>();
    }
    public MTBDD(String name){
        this.name = name;
        terminalNodes = new HashMap<>();
        terminalIncomingEdges = new HashMap<>();
    }

    public String getName() {
        return name;
    }
    public Node getRoot() {
        return root;
    }
    public Map<Node, List<Node>> getTerminalNodes() {
        return terminalNodes;
    }
    public Map<Node, List<Boolean>> getTerminalIncomingEdges() {
        return terminalIncomingEdges;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setRoot(Node root) {
        this.root = root;
    }
    public void setTerminalNodes(Map<Node, List<Node>> terminalNodes) {
        this.terminalNodes = terminalNodes;
    }
    public void setTerminalIncomingEdges(Map<Node, List<Boolean>> terminalIncomingEdges) {
        this.terminalIncomingEdges = terminalIncomingEdges;
    }

    /**
     * This method serves as copy generator of this MTBDD.
     * Also it is a wrapper of {@link MTBDDBuilder#makeMTBDDCopy()} method.
     * @return copy of this {@link MTBDD}.
     * */
    public MTBDD getCopy(){
        return new MTBDDBuilder(this).makeMTBDDCopy();
    }

    @Override
    public String toString() {
        printLevelOrder();
        return "";
    }

    private void printLevelOrder() {
        int h = height(root);
        int i;

        for (i=1; i<=h; i++) {
            printGivenLevel(root, i);
            System.out.println();
        }
    }
    private int height(Node root) {
        if (root == null)
            return 0;
        else {
            int lheight = height(root.getLeftChild());
            int rheight = height(root.getRightChild());

            if (lheight > rheight)
                return(lheight+1);
            else
                return(rheight+1);
        }
    }
    private void printGivenLevel (Node root, int level) {
        if (root == null)
            return;
        if (level == 1)
            System.out.print(root + " ");
        else if (level > 1) {
            printGivenLevel(root.getLeftChild(), level-1);
            printGivenLevel(root.getRightChild(), level-1);
        }
    }
}
