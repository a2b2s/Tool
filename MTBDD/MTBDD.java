package MTBDD;

import java.util.*;

public class MTBDD {

    private String name;
    private Node root;
    private Map<Node, List<Node>> terminalNodes;

    public MTBDD(){
        terminalNodes = new HashMap<Node, List<Node>>();
    }
    public MTBDD(String name){
        this.name = name;
        terminalNodes = new HashMap<Node, List<Node>>();
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

    public void setName(String name) {
        this.name = name;
    }
    public void setRoot(Node root) {
        this.root = root;
    }
    public void setTerminalNodes(Map<Node, List<Node>> terminalNodes) {
        this.terminalNodes = terminalNodes;
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
