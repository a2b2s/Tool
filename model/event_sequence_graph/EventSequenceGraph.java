package model.event_sequence_graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EventSequenceGraph {

    private String graphName;
    private Event current;
    private TreeMap<Event, List<Sequence>> eventSequenceGraph;
    protected final EventGenerator eg = new EventGenerator();

    public EventSequenceGraph(String graphName){
        this.graphName = graphName;
        eventSequenceGraph = new TreeMap<>();
    }

    public void setTheFirstEvent(){
        current = eventSequenceGraph.firstKey();
    }
    public String moveToTheNextEvent(Event nextEvent){
        Sequence s = new Sequence(current, nextEvent);
        if (isSequenceExist(s)) {
            current = nextEvent;
            return "Success";
        } else if (isEventExist(nextEvent))
            return "No sequence to this event from current event";
        else
            return "such event does not exist";
    }

    public void addEvent(Event event){
        if (!isEventExist(event))
            eventSequenceGraph.put(event, new ArrayList<>());
    }
    public void addEvents(List<Event> events){
        events.forEach(this::addEvent);
    }
    public void addSequence(Event source, Event destination){
        Sequence e = new Sequence(source, destination);

        addEvent(source);
        addEvent(destination);

        if (!isSequenceExist(e))
            eventSequenceGraph.get(source).add(e);
    }
    public void addSequence(Sequence sequence){
        addEvent(sequence.getSource());
        addEvent(sequence.getDestination());

        if (!isSequenceExist(sequence))
            eventSequenceGraph.get(sequence.getSource()).add(sequence);
    }
    public void addSequences(List<Event> sources, List<Event> destinations){
        if (sources.size() == destinations.size())
            for (int i = 0; i < sources.size(); i++)
                addSequence(sources.get(i), destinations.get(i));
    }
    public void addSequences(Event source, List<Event> destinations){
        destinations.forEach(x -> addSequence(source,x));
    }
    public void addSequences(List<Event> sources, Event destination){
        sources.forEach(x -> addSequence(x, destination));
    }

    public boolean isEventExist(Event event){
        return eventSequenceGraph.containsKey(event);
    }
    public boolean isSequenceExist(Sequence sequence){
        return eventSequenceGraph.get(sequence.getSource()).stream().anyMatch(g -> g.equals(sequence));
    }

    public void removeEvent(Event event){
        for (Event key: eventSequenceGraph.keySet())
            eventSequenceGraph.get(key).removeIf(s -> s.sequenceHasDestinationEvent(event));

        eventSequenceGraph.remove(event);
    }
    public void removeSequence(Sequence sequence){
        if (isEventExist(sequence.getSource()))
        //if(eventSequenceGraph.containsKey(sequence.getSource()))
            if (isSequenceExist(sequence))
                eventSequenceGraph.get(sequence.getSource()).remove(sequence);
    }

    public Event getKeyEvent(Event e){
        return eventSequenceGraph.keySet().stream().filter(x -> x.equals(e)).findFirst().get();
    }

    public EventSequenceGraph copyOfESG(){
        EventSequenceGraph copy = new EventSequenceGraph(this.graphName);

        for (Event event: eventSequenceGraph.keySet())
            copy.addEvent(eg.generateACopyOfEvent(event));

        List<Event> copyEvents      = new ArrayList<Event>(copy.getEventSequenceGraph().keySet());
        List<Event> originalEvents  = new ArrayList<Event>(eventSequenceGraph.keySet());

        for (int i = 0; i < originalEvents.size(); i++){
            List<Sequence> originalSequence = eventSequenceGraph.get(originalEvents.get(i));
            for (Sequence sequence : originalSequence)
                for (Event cE : copyEvents)
                    if (cE.equals(sequence.getDestination())) {
                        copy.addSequence(copyEvents.get(i), cE);
                        break;
                    }
        }

        return copy;
    }

    public String getGraphName() {
        return graphName;
    }
    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }
    public Map<Event, List<Sequence>> getEventSequenceGraph() {
        return eventSequenceGraph;
    }
    public void setEventSequenceGraph(TreeMap<Event, List<Sequence>> eventSequenceGraph) {
        this.eventSequenceGraph = eventSequenceGraph;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("eventSequenceGraph: ").append(graphName).append("\n");

        for (Event key: eventSequenceGraph.keySet())
            strBuilder.append(key).append(" ==> ").append(eventSequenceGraph.get(key)).append("\n");

        return strBuilder.toString();
    }
}