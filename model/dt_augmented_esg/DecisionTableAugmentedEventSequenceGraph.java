package model.dt_augmented_esg;

import decision_table.*;
import model.event_sequence_graph.Event;
import model.event_sequence_graph.EventGenerator;
import model.event_sequence_graph.EventSequenceGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DecisionTableAugmentedEventSequenceGraph extends EventSequenceGraph {

    public DecisionTableAugmentedEventSequenceGraph(String graphName) {
        super(graphName);
    }
    public DecisionTableAugmentedEventSequenceGraph(String graphName, DecisionTable decisionTable){
        super(graphName);

        Event e = eg.generateEvent(decisionTable);
        generateEventsFromDecisionTableInEvent(e);
    }

    /*public void generateEventsFromDecisionTableInEvent(Event event){
        DecisionTable dt = event.getDecisionTable();

        List<Action> nextEvents = new ArrayList<>();
        for (Rule r: dt.getRuleList()){
            List<Action> tempAction = r.getActionPairs()
                    .stream()
                    .filter(Pair::getaBoolean)
                    .distinct()
                    .map(x -> (Action)x.getValue())
                    .collect(Collectors.toCollection(ArrayList::new));
            nextEvents.addAll(tempAction);
        }
        nextEvents.forEach(x -> addSequence(event, eg.dtActionToEsgEvent(x)));
    }*/
    public void generateEventsFromDecisionTableInEvent(Event event){
        if (event.getDecisionTable() != null) {
            DecisionTable dt = event.getDecisionTable();
            dt.getWorkingActions().forEach(x -> addSequence(event, eg.dtActionToEsgEvent(x)));
        }
    }
    public void setDTtoEventAndGenerateNewEvents(Event event, DecisionTable decisionTable){
        EventGenerator eg = new EventGenerator();

        event.setDecisionTable(decisionTable);
        event.setEventName(decisionTable.getName());

        generateEventsFromDecisionTableInEvent(event);
    }

    public static void main(String[] args) {
        DecisionTableAugmentedEventSequenceGraph dt_esg1 = new DecisionTableAugmentedEventSequenceGraph("System");
        DecisionTable dt1 = new DecisionTable("VenSys");
        dt1.getDTFromFile("src\\resources\\Ventilation.txt");

        Event e1 = new Event(); e1.setEventName("e1");
        Event e2 = new Event(); e2.setEventName("e2");
        Event e3 = new Event(); e3.setEventName("e3");
        Event e4 = new Event(); e4.setEventName("e4");
        Event e5 = new Event(); e5.setEventName("e5");

        dt_esg1.addEvent(e1);
        dt_esg1.addEvent(e2);
        dt_esg1.addEvent(e3);
        dt_esg1.addEvent(e4);
        dt_esg1.addEvent(e5);

        dt_esg1.addSequence(e1, e2);
        dt_esg1.addSequence(e1, e3);
        dt_esg1.addSequence(e2, e3);
        dt_esg1.addSequence(e3, e4);
        dt_esg1.addSequence(e2, e5);

        System.out.println("Original--" + dt_esg1);

        dt_esg1.setDTtoEventAndGenerateNewEvents(e5, dt1);

        System.out.println("Original--" + dt_esg1);
        EventSequenceGraph dt_esg2 = dt_esg1.copyOfESG();
        System.out.println("Copy--" + dt_esg2);
    }
}