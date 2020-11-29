package MTBDD;

public abstract class Node {

    private Object value;

    public Node(){

    }
    public Node(Object value){
        this.value = value;
    }

    public abstract Node getClone();

    public Object getValue(){
        return value;
    }

    public void setValue(Object value){
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Node node = (Node) o;

        return value.equals(node.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString(){
        return value + "";
    }
}
