package MTBDD;

import java.util.HashSet;
import java.util.Set;

public class MTBDD {

    private String name;
    private NonTerminalNode root;
    private Set<TerminalNode> terminalNodes;

    public MTBDD(){
        terminalNodes = new HashSet<TerminalNode>();
    }
    public MTBDD(String name){
        this.name = name;
        terminalNodes = new HashSet<TerminalNode>();
    }

    public String getName() {
        return name;
    }
    public NonTerminalNode getRoot() {
        return root;
    }
    public Set<TerminalNode> getTerminalNodes(){
        return terminalNodes;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setRoot(NonTerminalNode root) {
        this.root = root;
    }
    public void setTerminalNodes(Set<TerminalNode> terminalNodes){
        this.terminalNodes = terminalNodes;
    }
}
