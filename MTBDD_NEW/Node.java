package MTBDD_NEW;

public class Node {

    private String label;
    private int lowID = -1;
    private int highID = -1;

    public static Node initializeNode(String label){
        Node n = new Node();
        n.setLabel(label);

        return n;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public void setLowID(int lowID) {
        this.lowID = lowID;
    }
    public void setHighID(int highID) {
        this.highID = highID;
    }

    public String getLabel() {
        return label;
    }
    public Integer getLowID() {
        return lowID;
    }
    public Integer getHighID() {
        return highID;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (lowID != node.lowID) return false;
        if (highID != node.highID) return false;
        return label.equals(node.label);
    }
    @Override public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + lowID;
        result = 31 * result + highID;
        return result;
    }
    @Override public String toString() {
        return "|" + label + "||" + lowID + "||" + highID + "|";
    }
}
