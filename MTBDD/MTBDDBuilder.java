package MTBDD;

import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as a builder for {@link MTBDD}.
 * */
public class MTBDDBuilder {

    private MTBDD mtbdd;
    private Node currentNode;

    public MTBDDBuilder(){

    }
    public MTBDDBuilder(MTBDD mtbdd){
        this.mtbdd = mtbdd;
    }

    /**
     * Use this method if {@link MTBDD} does not have a root node.
     * @param rootNode pass a new node to add first node to the {@link MTBDD}.
     * */
    public void addRoot(Node rootNode){
        mtbdd.setRoot(rootNode);
        currentNode = mtbdd.getRoot();
    }

    /**
     * This method adds a new non-terminal node by connecting it to the existing non-terminal node by certain edge.
     * @param parentNode is an existing non-terminal node.
     * @param newNode is a new non-terminal node.
     * @param edge assignes low/high status to the new node.
     * */
    public void addNonterminalNode(Node parentNode, Node newNode, boolean edge){
        if (parentNode.getChildByEdge(edge) == null) {
            if (edge)
                parentNode.setRightChild(newNode);
            else
                parentNode.setLeftChild(newNode);
            newNode.setParent(parentNode);
            newNode.setIncomingEdge(edge);
            currentNode = newNode;
        }else
            currentNode = parentNode.getChildByEdge(edge);
    }
    /**
     * This method connects a terminal node to the existing non-terminal node by certain edge.
     * @param parentNonterminalNode is an existing non-terminal node.
     * @param terminalNode is.
     * @param edge assignes low/high status to the terminal node.
     * */
    public void addTerminalNode(Node parentNonterminalNode, Node terminalNode, Boolean edge){
        if (mtbdd.getTerminalNodes().containsKey(terminalNode))
            mtbdd.getTerminalNodes().get(terminalNode).add(parentNonterminalNode);
        else {
            mtbdd.getTerminalNodes().put(terminalNode, new ArrayList<>());
            mtbdd.getTerminalNodes().get(terminalNode).add(parentNonterminalNode);
            mtbdd.getTerminalIncomingEdges().put(terminalNode, new ArrayList<>());
        }
        mtbdd.getTerminalIncomingEdges().get(terminalNode).add(edge);

        if (edge)
            parentNonterminalNode.setRightChild(terminalNode);
        else
            parentNonterminalNode.setLeftChild(terminalNode);
    }

    /**
     * Detaches one child node from parent node by child's incoming edge.
     * @param parent {@link Node} which needed to get rid of child {@link Node}
     * @param edge parameter defines incoming edge child node.
     * */
    public void removeChildNode(Node parent, boolean edge){
        if (edge)
            parent.setRightChild(null);
        else
            parent.setLeftChild(null);
    }
    /**
     * Detaches and removes terminal node from {@link MTBDD}.
     * @param terminalNode should be removed from {@link MTBDD} .
     * */
    public void removeTerminalNode(Node terminalNode){
        if (mtbdd.getTerminalNodes().containsKey(terminalNode)){
            List<Boolean> edgeList = mtbdd.getTerminalIncomingEdges().get(terminalNode);
            List<Node> nodeList = mtbdd.getTerminalNodes().get(terminalNode);

            for (int i = 0; i < nodeList.size(); i++)
                removeChildNode(nodeList.get(i), edgeList.get(i));

            mtbdd.getTerminalNodes().remove(terminalNode);
            mtbdd.getTerminalIncomingEdges().remove(terminalNode);
        }
    }

    /**
     * This method is a core of {@link MTBDD#getCopy()}. It passes a copy of a given {@link MTBDD}.
     * @return a copy of a given {@link MTBDD}.
     * */
    public MTBDD makeMTBDDCopy(){
        MTBDD copy = new MTBDD(mtbdd.getName());
        copy.setRoot(mtbdd.getRoot().getClone());
        copy(mtbdd.getRoot(), copy.getRoot());

        List<Node> lowNTNodes = new ArrayList<>();
        lowestNonterminalNodes(copy.getRoot(), lowNTNodes);
        terminalNodesForCopy(copy,lowNTNodes);

        return copy;
    }
    /**
     * Generates copies of all nodes starting from root node.
     * @param oldNode passes its left/right children to the
     * @param newNode .
     * */
    private void copy(Node oldNode, Node newNode) {
        if (oldNode.getLeftChild() != null) {
            newNode.setLeftChild(oldNode.getLeftChild().getClone());
            newNode.getLeftChild().setParent(newNode);
            copy(oldNode.getLeftChild(), newNode.getLeftChild());
        }
        if (oldNode.getRightChild() != null) {
            newNode.setRightChild(oldNode.getRightChild().getClone());
            newNode.getRightChild().setParent(newNode);
            copy(oldNode.getRightChild(), newNode.getRightChild());
        }
    }
    /**
     * Use this method to build a list of non-terminal nodes which connect terminal nodes to {@link MTBDD}.
     * @param node should be a root node.
     * @param lowNTNodes is a list where you save thos non-terminal nodes.
     * */
    private void lowestNonterminalNodes(Node node, List<Node> lowNTNodes){
        if (node.getLeftChild() != null && node.getRightChild() != null && node.getLeftChild().isTerminalNode() && node.getRightChild().isTerminalNode())
            lowNTNodes.add(node);
        else if (node.getLeftChild() != null && node.getRightChild() == null && node.getLeftChild().isTerminalNode())
            lowNTNodes.add(node);
        else if (node.getLeftChild() == null && node.getRightChild() != null && node.getRightChild().isTerminalNode())
            lowNTNodes.add(node);
        else if (node.getLeftChild() != null && node.getRightChild() == null && !node.getLeftChild().isTerminalNode())///////////////////novoye
            lowestNonterminalNodes(node.getLeftChild(), lowNTNodes);
        else if (node.getLeftChild() == null && node.getRightChild() != null && !node.getRightChild().isTerminalNode())//////////////////novoye
            lowestNonterminalNodes(node.getRightChild(), lowNTNodes);
        else {
            lowestNonterminalNodes(node.getLeftChild(), lowNTNodes);
            lowestNonterminalNodes(node.getRightChild(), lowNTNodes);
        }
    }
    /**
     * Use this method to save terminal nodes with their parents and incoming edges in Map structure.
     * @param mtbdd is {@link MTBDD} which does not have saved terminal nodes in Map structure.
     * @param lowNTNodes is list of non-terminal nodes. These nodes give you their terminal children.
     * */
    private void terminalNodesForCopy(MTBDD mtbdd, List<Node> lowNTNodes){
        MTBDDBuilder b = new MTBDDBuilder(mtbdd);

        for (Node parentNode : lowNTNodes) {
            if (parentNode.getLeftChild() != null) {
                Node leftChild = parentNode.getChildByEdge(false);
                leftChild.setParent(null);
                leftChild.setIncomingEdge(false);
                b.addTerminalNode(parentNode, leftChild, false);
            }
            if (parentNode.getRightChild() != null) {
                Node rightChild = parentNode.getChildByEdge(true);
                rightChild.setParent(null);
                rightChild.setIncomingEdge(false);
                b.addTerminalNode(parentNode, rightChild, true);
            }
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
