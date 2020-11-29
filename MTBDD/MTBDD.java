package MTBDD;

import java.util.HashSet;
import java.util.Set;

public class MTBDD {

    private String name;
    private Node root;
    private Set<Node> terminalNodes;

    public MTBDD(){
        terminalNodes = new HashSet<Node>();
    }
    public MTBDD(String name){
        this.name = name;
        terminalNodes = new HashSet<Node>();
    }

    public String getName() {
        return name;
    }
    public Node getRoot() {
        return root;
    }
    public Set<Node> getTerminalNodes(){
        return terminalNodes;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setRoot(Node root) {
        this.root = root;
    }
    public void setTerminalNodes(Set<Node> terminalNodes){
        this.terminalNodes = terminalNodes;
    }
}
