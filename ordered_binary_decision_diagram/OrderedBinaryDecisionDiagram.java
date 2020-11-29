package ordered_binary_decision_diagram;

import java.util.ArrayList;
import java.util.List;

public class OrderedBinaryDecisionDiagram{

    private String name;
    private Node root, current;
    private List<Node> nodesSequence, terminalNodes, theLowestNonterminalNodes;

    private boolean lastBoolean;

    public OrderedBinaryDecisionDiagram(String name){
        this.name = name;
        nodesSequence = new ArrayList<>();
        terminalNodes = new ArrayList<>();
        theLowestNonterminalNodes = new ArrayList<>();
    }

    public void insertNode(Object value, Boolean b){
        Node node = new Node();
        node.setValue(value);

        if (nodesSequence.isEmpty() && root == null) {
            root = node;
            nodesSequence.add(root);
        }
        else if (root != null && nodesSequence.isEmpty())
            nodesSequence.add(root);
        else if (current.getChildNode(lastBoolean) == null){
            if (lastBoolean) {
                current.setRightChildNode(node);
                current = current.getRightChildNode();
                current.setIncomingEdge(true);
            }
            else {
                current.setLeftChildNode(node);
                current = current.getLeftChildNode();
                current.setIncomingEdge(false);
            }

            current.setParentNode(nodesSequence.get(nodesSequence.size()-1));
            nodesSequence.add(current);
        }
        else
            nodesSequence.add(current.getChildNode(lastBoolean));

        lastBoolean = b;
        current = nodesSequence.get(nodesSequence.size()-1);
    }
    public void insertNodes(List<?> strs, List<Boolean> bs){
        for (int i=0; i<strs.size(); i++)
            insertNode(strs.get(i), bs.get(i));

        nodesSequence.clear();
    }

    public void addTerminalNode(Node nonTerminalNode, boolean b, Node terminalNode){
        if (nonTerminalNode.getLeftChildNode() == null && nonTerminalNode.getRightChildNode() == null) {
            terminalNode.setParentNode(nonTerminalNode);
            terminalNode.setIncomingEdge(b);
            if (b)
                nonTerminalNode.setRightChildNode(terminalNode);
            else
                nonTerminalNode.setLeftChildNode(terminalNode);

            //terminalNodes.add(terminalNode);
        }
        else if (nonTerminalNode.getLeftChildNode() == null && nonTerminalNode.getRightChildNode() != null){
            terminalNode.setParentNode(nonTerminalNode);
            terminalNode.setIncomingEdge(false);
            nonTerminalNode.setLeftChildNode(terminalNode);

            //terminalNodes.add(terminalNode);
        }
        else if (nonTerminalNode.getRightChildNode() == null && nonTerminalNode.getLeftChildNode() != null){
            terminalNode.setParentNode(nonTerminalNode);
            terminalNode.setIncomingEdge(true);
            nonTerminalNode.setRightChildNode(terminalNode);

            //terminalNodes.add(terminalNode);
        }
    }
    public void deleteTerminalNode(Node terminalNode){
        if (!terminalNodes.isEmpty()){
            if (terminalNodes.contains(terminalNode)) {
                Node parent = terminalNode.getParentNode();
                boolean sequence = terminalNode.getIncomingEdge();
                if (sequence)
                    parent.setRightChildNode(null);
                else
                    parent.setLeftChildNode(null);

                terminalNodes.remove(terminalNode);
            }
            else
                System.out.println(terminalNode + " does not exist.");
        }
        else
            System.out.println("MTBDD " + name + " does not have terminal nodes.");
    }
    public void deleteTerminalNode(int index){
        if (!terminalNodes.isEmpty() && index < terminalNodes.size()){
            Node tn = terminalNodes.get(index);
            Node parent = tn.getParentNode();
            boolean sequence = tn.getIncomingEdge();

            if (sequence)
                parent.setRightChildNode(null);
            else
                parent.setLeftChildNode(null);

            terminalNodes.remove(index);
        }
    }

    private void lowestNonterminalNodes(Node n){
        if (n.getLeftChildNode() != null && n.getRightChildNode() != null && n.getLeftChildNode().isTNode() && n.getRightChildNode().isTNode())
            theLowestNonterminalNodes.add(n);
        else if (n.getLeftChildNode() != null && n.getRightChildNode() == null && n.getLeftChildNode().isTNode())
            theLowestNonterminalNodes.add(n);
        else if (n.getLeftChildNode() == null && n.getRightChildNode() != null && n.getRightChildNode().isTNode())
            theLowestNonterminalNodes.add(n);
        else if (n.getLeftChildNode() != null && n.getRightChildNode() == null && !n.getLeftChildNode().isTNode())///////////////////novoye
            lowestNonterminalNodes(n.getLeftChildNode());
        else if (n.getLeftChildNode() == null && n.getRightChildNode() != null && !n.getRightChildNode().isTNode())//////////////////novoye
            lowestNonterminalNodes(n.getRightChildNode());
        else {
            lowestNonterminalNodes(n.getLeftChildNode());
            lowestNonterminalNodes(n.getRightChildNode());
        }
    }
    private void terminalNodes(){
        for (Node ntn : theLowestNonterminalNodes)
            if (ntn.getLeftChildNode() != null && ntn.getRightChildNode() != null){
                terminalNodes.add(ntn.getLeftChildNode());
                terminalNodes.add(ntn.getRightChildNode());
            }
            else if (ntn.getLeftChildNode() != null && ntn.getRightChildNode() == null)
                terminalNodes.add(ntn.getLeftChildNode());
            else if (ntn.getLeftChildNode() == null && ntn.getRightChildNode() != null)
                terminalNodes.add(ntn.getRightChildNode());
    }

    public void fill_NTNList(){
        theLowestNonterminalNodes = new ArrayList<>();
        lowestNonterminalNodes(root);
    }
    public void fill_TNLists(){
        terminalNodes = new ArrayList<>();
        terminalNodes();
    }

    public OrderedBinaryDecisionDiagram makeACopy() {
        OrderedBinaryDecisionDiagram newOBDD = new OrderedBinaryDecisionDiagram(this.name);
        newOBDD.setRoot(this.root.cloner());
        copy(this.getRoot(), newOBDD.getRoot());

        return newOBDD;
    }
    private void copy(Node oldNode, Node newNode) {
        if (oldNode.getLeftChildNode() != null) {
            newNode.setLeftChildNode(oldNode.getLeftChildNode().cloner());
            newNode.getLeftChildNode().setParentNode(newNode);
            copy(oldNode.getLeftChildNode(), newNode.getLeftChildNode());
        }
        if (oldNode.getRightChildNode() != null) {
            newNode.setRightChildNode(oldNode.getRightChildNode().cloner());
            newNode.getRightChildNode().setParentNode(newNode);
            copy(oldNode.getRightChildNode(), newNode.getRightChildNode());
        }
    }

    /**
     * Under development, don't use.
     * */
    @Deprecated
    public OrderedBinaryDecisionDiagram reduceTerminalNodes(){
        fill_NTNList();
        fill_TNLists();

        List<Node> temporary = new ArrayList<Node>();
        temporary.add(terminalNodes.get(0));

        for (int i = 0; i < terminalNodes.size(); i++) {
            for (int j = i; j < terminalNodes.size(); j++) {
                if (i != j) {
                    if (!(terminalNodes.get(i).getValue().equals(terminalNodes.get(j).getValue()))) {
                        if (!temporary.contains(terminalNodes.get(j))) {
                            temporary.add(terminalNodes.get(j));
                        }
                    }else {
                        Boolean b = terminalNodes.get(j).getIncomingEdge();
                        Node parent = terminalNodes.get(j).getParentNode();
                        if (b)
                            parent.setRightChildNode(terminalNodes.get(i));
                        else
                            parent.setLeftChildNode(terminalNodes.get(i));

                        terminalNodes.get(i).addParentToTerminalNode(terminalNodes.get(j).getParentNode());
                        terminalNodes.get(j).setParentNode(null);
                    }
                }
            }
        }

        this.setTerminalNodes(temporary);
        return this;
    }
    /*-------------------------------------------------------------------------------------------------
      ------------------------------------Getters/Setters/PrintOuts------------------------------------
      -------------------------------------------------------------------------------------------------*/
    public Node getRoot() {
        return root;
    }
    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getCurrent() {
        return current;
    }
    public void setCurrent(Node current) {
        this.current = current;
    }

    public List<Node> getNodesSequence() {
        return nodesSequence;
    }
    public void setNodesSequence(List<Node> nodesSequence) {
        this.nodesSequence = nodesSequence;
    }

    public List<Node> getTerminalNodes() {
        return terminalNodes;
    }
    public void setTerminalNodes(List<Node> terminalNodes) {
        this.terminalNodes = terminalNodes;
    }

    public List<Node> getTheLowestNonterminalNodes() {
        return theLowestNonterminalNodes;
    }
    public void setTheLowestNonterminalNodes(List<Node> theLowestNonterminalNodes) {
        this.theLowestNonterminalNodes = theLowestNonterminalNodes;
    }

    public boolean isLastBoolean() {
        return lastBoolean;
    }
    public void setLastBoolean(boolean lastBoolean) {
        this.lastBoolean = lastBoolean;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
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
            int lheight = height(root.getLeftChildNode());
            int rheight = height(root.getRightChildNode());

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
            printGivenLevel(root.getLeftChildNode(), level-1);
            printGivenLevel(root.getRightChildNode(), level-1);
        }
    }

    @Override
    public String toString() {
        printLevelOrder();
        return "";
    }
}
