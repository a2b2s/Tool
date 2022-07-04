package MTBDD_NEW;

import java.util.*;

public class MTBDD{

    private final String name;
    private final List<Node> nodes;
    private Map<Integer, List<Integer>> terminalNodesWithParents;
    private int lastID = 0;

    public MTBDD(String name){
        this.name = name;
        nodes = new ArrayList<>();
        terminalNodesWithParents = new HashMap<>();
    }

    public void addNode(Node node){
        nodes.add(node);
        lastID = nodes.size()-1;
    }
    public void addTerminalNode(int id){
        if (!terminalNodesWithParents.containsKey(id))
            terminalNodesWithParents.put(id, new ArrayList<>());
    }
    public void addParentToTerminalNode(int parentID, int tNodeID){
        if (!terminalNodesWithParents.containsKey(tNodeID))
            addTerminalNode(tNodeID);
        terminalNodesWithParents.get(tNodeID).add(parentID);
    }
    public void setLowID(int parentID, int lowID){
        nodes.get(parentID).setLowID(lowID);
    }
    public void setHighID(int parentID, int highID){
        nodes.get(parentID).setHighID(highID);
    }
    public void setLastID(int lastID){
        this.lastID = lastID;
    }

    public boolean isMTBDDEmpty(){
        return nodes.isEmpty();
    }
    public boolean isTerminal(int nodeID){
        return nodes.get(nodeID).getLowID() == -1 && nodes.get(nodeID).getHighID() == -1;
    }
    public boolean hasLowChild(int nodeID){
        return nodes.get(nodeID).getLowID() != -1;
    }
    public boolean hasHighChild(int nodeID){
        return nodes.get(nodeID).getHighID() != -1;
    }
    public int existingTNodeID(String label){
        for (Integer id: terminalNodesWithParents.keySet())
            if (nodes.get(id).getLabel().equalsIgnoreCase(label))
                return id;

        return -1;
    }

    public int getLastID(){
        return lastID;
    }
    public int getLowID(int nodeID){
        return nodes.get(nodeID).getLowID();
    }
    public int getHighID(int nodeID){
        return nodes.get(nodeID).getHighID();
    }
    public List<Node> getNodes(){
        return nodes;
    }
    public Map<Integer, List<Integer>> getTerminalNodesWithParents(){
        return terminalNodesWithParents;
    }
    public String getName(){
        return name;
    }

    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder("MTBDD{name=" + name + ", nodes=\n");
        nodes.forEach(x -> stringBuilder.append(x).append("\n"));
        stringBuilder.append( '}');

        return stringBuilder.toString();
    }
}
