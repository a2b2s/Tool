package MTBDD;

import java.util.List;

public class BuilderMTBDD {

    private MTBDD mtbdd;
    private Node currentNode;

    public BuilderMTBDD(){

    }
    public BuilderMTBDD(MTBDD mtbdd){
        this.mtbdd = mtbdd;
    }

    public void addNode(Node newNode, Boolean edge){
        if (mtbdd.getRoot() == null) {
            mtbdd.setRoot(newNode);
            currentNode = mtbdd.getRoot();
        } else{
            establishConnection(currentNode, newNode, edge);
            currentNode = newNode;
        }
    }
    public void addNodes(List<Node> nodes, List<Boolean> edges){
        for (int i = 0; i < nodes.size(); i++)
            addNode(nodes.get(i), edges.get(i));
    }
    public void addTerminalNode(Node terminalNode, Boolean edge){
        mtbdd.getTerminalNodes().add(terminalNode);
        if (edge) {
            currentNode.setRightChild(terminalNode);
        } else {
            currentNode.setLeftChild(terminalNode);
        }
    }

    public void establishConnection(Node parent, Node child, Boolean edge){
        if (edge) {
            parent.setRightChild(child);
            child.setParent(parent);
            child.setIncomingEdge(true);
        } else{
            parent.setLeftChild(child);
            child.setParent(parent);
            child.setIncomingEdge(false);
        }
    }

    public MTBDD getMTBDD(){
        return mtbdd;
    }
    public Node getCurrentNode(){
        return currentNode;
    }

    public void setMTBDD(MTBDD mtbdd){
        this.mtbdd = mtbdd;
    }
    public void setCurrentNode(Node currentNode){
        this.currentNode = currentNode;
    }

}
