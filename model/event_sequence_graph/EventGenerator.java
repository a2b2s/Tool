package model.event_sequence_graph;

import decision_table.Action;
import decision_table.DecisionTable;

public class EventGenerator {

    public Event generateEvent(String eventName){
        Event event = new Event();
        event.setEventName(eventName);

        return event;
    }

    public Event generateEvent(DecisionTable decisionTable){
        Event event = new Event();
        event.setDecisionTable(decisionTable);
        event.setEventName(decisionTable.getName());

        return event;
    }

    public Event generateACopyOfEvent(Event event){
        Event copy = new Event();

        copy.setEventID(event.getEventID());
        copy.setEventName(event.getEventName());
        if (event.getDecisionTable() != null)
            copy.setDecisionTable(event.getDecisionTable().copyOfDecisionTable());

        return copy;
    }

    public Event dtActionToEsgEvent(Action action){
        Event event = new Event();
        event.setEventName(action.getName());

        return event;
    }
}
