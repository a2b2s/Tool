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

    public void addNonterminalNode(Node newNode, Boolean edge){
        if (mtbdd.getRoot() == null) {
            mtbdd.setRoot(newNode);
            currentNode = mtbdd.getRoot();
        } else{
            if (edge){
                currentNode.setRightChild(newNode);
                newNode.setParent(currentNode);
                newNode.setIncomingEdge(true);
            }
            else {
                currentNode.setLeftChild(newNode);
                newNode.setParent(currentNode);
                newNode.setIncomingEdge(false);
            }
            currentNode = newNode;
        }
    }
    public void addTerminalNode(Node terminalNode, Boolean edge){
        if (mtbdd.getTerminalNodes().containsKey(terminalNode)){
            if (edge)
                currentNode.setRightChild(terminalNode);
            else
                currentNode.setLeftChild(terminalNode);

            mtbdd.getTerminalNodes().get(terminalNode).add(currentNode);
        }else {
            if (edge)
                currentNode.setRightChild(terminalNode);
            else
                currentNode.setLeftChild(terminalNode);
            mtbdd.getTerminalNodes().put(terminalNode, List.of(currentNode));
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
