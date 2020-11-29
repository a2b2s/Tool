package MTBDD;

public class BuilderMTBDD {

    private MTBDD mtbdd;
    private NonTerminalNode currentNode;

    public BuilderMTBDD(){

    }
    public BuilderMTBDD(MTBDD mtbdd){
        this.mtbdd = mtbdd;
    }

    public void addNonterminalNode(NonTerminalNode newNode, Boolean edge){
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
    public void addTerminalNode(TerminalNode terminalNode, Boolean edge){
        mtbdd.getTerminalNodes().add(terminalNode);
        if (edge) {
            currentNode.setRightChild(terminalNode);
            terminalNode.getParents().add(currentNode);
            terminalNode.getEdges().add(true);
        } else {
            currentNode.setLeftChild(terminalNode);
            terminalNode.getParents().add(currentNode);
            terminalNode.getEdges().add(false);
        }
    }

    public void addNode(Node node, Boolean edge){
        if (node instanceof NonTerminalNode)
            addNonterminalNode((NonTerminalNode) node, edge);
        else if (node instanceof TerminalNode)
            addTerminalNode((TerminalNode) node, edge);
    }

    public MTBDD getMTBDD(){
        return mtbdd;
    }
    public NonTerminalNode getCurrentNode(){
        return currentNode;
    }

    public void setMTBDD(MTBDD mtbdd){
        this.mtbdd = mtbdd;
    }
    public void setCurrentNode(NonTerminalNode currentNode){
        this.currentNode = currentNode;
    }

}
