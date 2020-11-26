package model.event_sequence_graph;

public class Sequence {

    private Event source, destination;

    public Sequence(Event source, Event destination){
        this.source = source;
        this.destination = destination;
    }

    public boolean sequenceHasDestinationEvent(Event destinationEvent){
        return destination.equals(destinationEvent);
    }

    public Event getSource() {
        return source;
    }
    public void setSource(Event source) {
        this.source = source;
    }
    public Event getDestination() {
        return destination;
    }
    public void setDestination(Event destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (object == null)
            return false;
        if (object instanceof Sequence){
            Sequence e = (Sequence)object;
            return source.equals(e.source) && destination.equals(e.destination);
        }

        return false;
    }
    @Override
    public String toString() {
        return "(" + source + ", " + destination + ")";
    }
}