package MTBDD;

import java.util.List;

public class TerminalNode extends Node{

    private List<NonTerminalNode> parents;
    private List<Boolean> edges;

    @Override
    public TerminalNode getClone() {
        TerminalNode tNode = new TerminalNode();
        tNode.setValue(this.getValue());

        return tNode;
    }

    public List<NonTerminalNode> getParents(){
        return parents;
    }
    public List<Boolean> getEdges(){
        return edges;
    }

    public void setParents(List<NonTerminalNode> parents){
        this.parents = parents;
    }
    public void setEdges(List<Boolean> edges){
        this.edges = edges;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
