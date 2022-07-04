package MTBDD;

/**
 * {@link Node} is the basic unit in {@link MTBDD}
 * */
public class Node {

    private Object value;
    private Node parent, leftChild, rightChild;
    private boolean incomingEdge;

    public Node(){

    }
    public Node(Object value){
        this.value = value;
    }
    /**
     * Creates a copy of the current {@link Node}.
     * @return {@link Node}
     * */
    public Node getClone() {
        Node node = new Node();
        node.setValue(value);
        node.setIncomingEdge(incomingEdge);

        return node;
    }
    /**
     * Checks whether this {@link Node} is terminal or not.
     * @return {@code true} if it is a terminal {@link Node} <p>or</p> {@code false} if it is a non-terminal {@link Node}
     * */
    public boolean isTerminalNode(){
        return leftChild == null && rightChild == null;
    }
    /**
     * Checks whether this {@link Node} is root or not.
     * @return {@code true} if it is a root {@link Node} <p>or</p> {@code false} if it is not a root {@link Node}
     * */
    public boolean isRootNode(){
        return parent == null;
    }
    /**
     * Gives a child {@link Node} based on the edge.
     * @param b This parameter represents the edge of the current {@link Node}.
     * @return {@linkplain #rightChild}, if b is {@code true} <p>or</p> {@linkplain #leftChild}, if b is {@code false}.
     * */
    public Node getChildByEdge(boolean b){
        return b ? rightChild : leftChild;
    }

    public Object getValue(){
        return value;
    }
    public Node getParent() {
        return parent;
    }
    public Node getLeftChild() {
        return leftChild;
    }
    public Node getRightChild() {
        return rightChild;
    }
    public boolean getIncomingEdge() {
        return incomingEdge;
    }

    public void setValue(Object value){
        this.value = value;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }
    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
    public void setIncomingEdge(boolean incomingEdge) {
        this.incomingEdge = incomingEdge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (incomingEdge != node.incomingEdge) return false;
        if (!value.equals(node.value)) return false;
        if (parent != null ? !parent.equals(node.parent) : node.parent != null) return false;
        if (leftChild != null ? !leftChild.equals(node.leftChild) : node.leftChild != null) return false;
        return rightChild != null ? rightChild.equals(node.rightChild) : node.rightChild == null;
    }
    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (leftChild != null ? leftChild.hashCode() : 0);
        result = 31 * result + (rightChild != null ? rightChild.hashCode() : 0);
        result = 31 * result + (incomingEdge ? 1 : 0);
        return result;
    }
    @Override
    public String toString(){
        return value + "/" + incomingEdge;
    }
}
