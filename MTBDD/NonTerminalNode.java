package MTBDD;

public class NonTerminalNode extends Node{

    private Node parent, leftChild, rightChild;
    private boolean incomingEdge;

    @Override
    public NonTerminalNode getClone() {
        NonTerminalNode ntNode = new NonTerminalNode();
        ntNode.setValue(this.getValue());

        return ntNode;
    }

    public Node getParent(){
        return parent;
    }
    public Node getLeftChild(){
        return leftChild;
    }
    public Node getRightChild(){
        return rightChild;
    }
    public boolean getIncomingEdge(){
        return incomingEdge;
    }

    public void setParent(NonTerminalNode parent){
        this.parent = parent;
    }
    public void setLeftChild(Node leftChild){
        this.leftChild = leftChild;
    }
    public void setRightChild(Node rightChild){
        this.rightChild = rightChild;
    }
    public void setIncomingEdge(boolean incomingEdge){
        this.incomingEdge = incomingEdge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NonTerminalNode ntNode = (NonTerminalNode) o;

        if (incomingEdge != ntNode.incomingEdge) return false;
        if (parent != null ? !parent.equals(ntNode.parent) : ntNode.parent != null) return false;
        if (!leftChild.equals(ntNode.leftChild)) return false;
        return rightChild.equals(ntNode.rightChild);
    }
    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + leftChild.hashCode();
        result = 31 * result + rightChild.hashCode();
        result = 31 * result + (incomingEdge ? 1 : 0);
        return result;
    }
}
