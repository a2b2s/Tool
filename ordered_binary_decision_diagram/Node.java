package ordered_binary_decision_diagram;

import decision_table.Action;
import decision_table.Condition;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Object value;
    private Node leftChildNode, rightChildNode, parentNode;
    private Boolean incomingEdge;
    private List<Node> terminalNodeParentList = new ArrayList<Node>();

    public Node getChildNode(boolean b){
        return b ? rightChildNode : leftChildNode;
    }
    public boolean isRoot(){
        return this.getParentNode() == null;
    }
    public boolean isTNode(){
        return this.leftChildNode == null && this.rightChildNode == null;
    }

    public Node cloner(){
        Node nn = new Node();

        nn.setValue(this.value);
        nn.setIncomingEdge(this.incomingEdge);

        return nn;
    }
    /*-------------------------------------------------------------------------------------------------------------
      ----------------------------------------Getters/Setters/PrintOuts--------------------------------------------
      -------------------------------------------------------------------------------------------------------------*/
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public Node getLeftChildNode() {
        return leftChildNode;
    }
    public void setLeftChildNode(Node leftChildNode) {
        this.leftChildNode = leftChildNode;
    }
    public Node getRightChildNode() {
        return rightChildNode;
    }
    public void setRightChildNode(Node rightChildNode) {
        this.rightChildNode = rightChildNode;
    }
    public Node getParentNode() {
        return parentNode;
    }
    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
    public Boolean getIncomingEdge() {
        return incomingEdge;
    }
    public void setIncomingEdge(Boolean incomingEdge) {
        this.incomingEdge = incomingEdge;
    }
    public List<Node> getTerminalNodeParentList(){
        return terminalNodeParentList;
    }
    public void setTerminalNodeParentList(List<Node> terminalNodeParentList){
        this.terminalNodeParentList = terminalNodeParentList;
    }

    public void addParentToTerminalNode(Node nonterminalNode){
        terminalNodeParentList.add(nonterminalNode);
    }
    public void removeParentOfTerminalNode(Node nonterminalNode){
        terminalNodeParentList.remove(nonterminalNode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Node){
            Node n = (Node) obj;
            if (n.getValue() instanceof Condition)
                return (this.getValue().equals(n.getValue()));
            if (n.getValue() instanceof Action)
                return (this.getValue().equals(n.getValue()));
        }
        //return super.equals(obj);
        return false;
    }

    @Override
    public String toString() {
        return value + "|" + incomingEdge;
    }
}
