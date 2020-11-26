package model.event_sequence_graph;

import decision_table.DecisionTable;

public class Event implements Comparable<Event>{

    private String eventName;
    private static int globalID = 0;
    private int eventID = 0;
    private DecisionTable decisionTable;

    public Event(){
        globalID++;
        setEventID(globalID);
    }
    public Event(DecisionTable decisionTable){
        this.eventName = decisionTable.getName();
        this.decisionTable = decisionTable;
        globalID++;
        setEventID(globalID);
    }

    public boolean isDTExtendedEvent(){
        return decisionTable != null;
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getEventID() {
        return eventID;
    }
    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public DecisionTable getDecisionTable() {
        return decisionTable;
    }
    public void setDecisionTable(DecisionTable decisionTable) {
        this.decisionTable = decisionTable;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (object instanceof Event){
            Event n = (Event) object;
            if (n.decisionTable != null && decisionTable != null)
                return decisionTable.equals(n.decisionTable);
            else if(n.decisionTable == null && decisionTable != null)
                return false;
            else if (n.decisionTable != null && decisionTable == null)
                return false;
            else
                return eventName.equalsIgnoreCase(n.eventName);
        }

        return false;
    }
    @Override
    public String toString() {
        return eventName;
    }
    @Override
    public int compareTo(Event o) {
        return Integer.compare(eventID, o.getEventID());
    }
}