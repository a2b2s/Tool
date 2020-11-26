package mutations;

import decision_table.Action;
import decision_table.Condition;
import decision_table.DecisionTable;
import decision_table.Rule;
import model.dt_augmented_esg.DecisionTableAugmentedEventSequenceGraph;
import model.event_sequence_graph.Event;
import model.event_sequence_graph.EventGenerator;
import model.event_sequence_graph.EventSequenceGraph;
import model.event_sequence_graph.Sequence;
import ordered_binary_decision_diagram.Node;
import ordered_binary_decision_diagram.OrderedBinaryDecisionDiagram;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMutator {

    private List<EventSequenceGraph> modelDTActionOmission = new ArrayList<>();
    private List<EventSequenceGraph> modelDTRuleOmission = new ArrayList<>();
    private List<EventSequenceGraph> modelDTActionInsertion = new ArrayList<>();
    private List<EventSequenceGraph> modelDTRuleInsertion = new ArrayList<>();

    private List<EventSequenceGraph> modelOBDDNodeInsertion = new ArrayList<>();
    private List<EventSequenceGraph> modelOBDDNodeOmission = new ArrayList<>();

    private List<EventSequenceGraph> allModelMutants = new ArrayList<>();

    public void mutateESGByActionOmission(EventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DTMutationOperators dtmo = new DTMutationOperators();
                DecisionTable dt = event.getDecisionTable();
                dtmo.omitActionFromDT(dt);

                List<DecisionTable> tempDTList = dtmo.getOmittedActions();

                for (DecisionTable diti: tempDTList){
                    EventSequenceGraph modelCopy = originalModel.copyOfESG();
                    Event tempE = modelCopy.getKeyEvent(event);

                    List<Sequence> tempSequences = diti.getWorkingActions()
                            .stream()
                            .map(x -> new EventGenerator().dtActionToEsgEvent(x))
                            .map(x -> new Sequence(tempE, x))
                            .collect(Collectors.toCollection(ArrayList::new));


                    for (Sequence s: originalModel.getEventSequenceGraph().get(tempE))
                        if (!tempSequences.contains(s))
                            modelCopy.removeSequence(s);

                    tempE.setDecisionTable(diti);
                    modelDTActionOmission.add(modelCopy);
                }
            }
        }
        allModelMutants.addAll(modelDTActionOmission);
    }
    public void mutateESGByRuleOmission(EventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DTMutationOperators dtmo = new DTMutationOperators();
                DecisionTable dt = event.getDecisionTable();
                dtmo.omitRuleFromDT(dt);

                List<DecisionTable> tempDTList = dtmo.getOmittedRules();

                for (DecisionTable diti: tempDTList){
                    EventSequenceGraph modelCopy = originalModel.copyOfESG();
                    Event tempE = modelCopy.getKeyEvent(event);

                    List<Sequence> tempSequences = diti.getWorkingActions()
                            .stream()
                            .map(x -> new EventGenerator().dtActionToEsgEvent(x))
                            .map(x -> new Sequence(tempE, x))
                            .collect(Collectors.toCollection(ArrayList::new));

                    for (Sequence s: originalModel.getEventSequenceGraph().get(tempE))
                        if (!tempSequences.contains(s))
                            modelCopy.removeSequence(s);

                    tempE.setDecisionTable(diti);
                    modelDTRuleOmission.add(modelCopy);
                }
            }
        }
        allModelMutants.addAll(modelDTRuleOmission);
    }
    public void mutateESGByActionInsertion(EventSequenceGraph originalModel, Action action){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DTMutationOperators dtmo = new DTMutationOperators();
                DecisionTable dt = event.getDecisionTable();
                dtmo.insertActionInDT(dt, action);

                List<DecisionTable> tempDTList = dtmo.getInsertedActions();

                for (DecisionTable diti: tempDTList){
                    EventSequenceGraph modelCopy = originalModel.copyOfESG();
                    Event tempE = modelCopy.getKeyEvent(event);

                    List<Sequence> tempSequences = diti.getWorkingActions()
                            .stream()
                            .map(x -> new EventGenerator().dtActionToEsgEvent(x))
                            .map(x -> new Sequence(tempE, x))
                            .collect(Collectors.toCollection(ArrayList::new));

                    /*for (Sequence s: originalModel.getEventSequenceGraph().get(tempE))
                        if (!tempSequences.contains(s))
                            modelCopy.removeSequence(s);*/

                    for (Sequence s: tempSequences)
                        if (!originalModel.getEventSequenceGraph().get(tempE).contains(s))
                            modelCopy.addSequence(s);

                    tempE.setDecisionTable(diti);
                    modelDTActionInsertion.add(modelCopy);
                }
            }
        }
        allModelMutants.addAll(modelDTActionInsertion);
    }
    public void mutateESGByRuleInsertion(EventSequenceGraph originalModel, Rule rule){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DTMutationOperators dtmo = new DTMutationOperators();
                DecisionTable dt = event.getDecisionTable();
                dtmo.insertRuleInDT(dt, rule);

                List<DecisionTable> tempDTList = dtmo.getInsertedRules();

                for (DecisionTable diti: tempDTList){
                    EventSequenceGraph modelCopy = originalModel.copyOfESG();
                    Event tempE = modelCopy.getKeyEvent(event);

                    List<Sequence> tempSequences = diti.getWorkingActions()
                            .stream()
                            .map(x -> new EventGenerator().dtActionToEsgEvent(x))
                            .map(x -> new Sequence(tempE, x))
                            .collect(Collectors.toCollection(ArrayList::new));

                    for (Sequence s: tempSequences)
                        if (!originalModel.getEventSequenceGraph().get(tempE).contains(s))
                            modelCopy.addSequence(s);

                    tempE.setDecisionTable(diti);
                    modelDTRuleInsertion.add(modelCopy);
                }
            }
        }
        allModelMutants.addAll(modelDTRuleInsertion);
    }

    public void mutateESGByNodeInsertion(EventSequenceGraph originalModel, Node node){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                DT_OBDD_Converter dt_obdd_converter = new DT_OBDD_Converter();
                OrderedBinaryDecisionDiagram obdd = dt_obdd_converter.DTtoOBDD(dt);

                OBDDMutantionOperators omo = new OBDDMutantionOperators();
                omo.terminalNodeInsertion(obdd, node);

                List<DecisionTable> tempDTList = dt_obdd_converter.OBDDsToDTs(omo.getObddInsertionMutantSet());

                for (DecisionTable diti: tempDTList){
                    EventSequenceGraph modelCopy = originalModel.copyOfESG();
                    Event tempE = modelCopy.getKeyEvent(event);

                    List<Sequence> tempSequences = diti.getWorkingActions()
                            .stream()
                            .map(x -> new EventGenerator().dtActionToEsgEvent(x))
                            .map(x -> new Sequence(tempE, x))
                            .collect(Collectors.toCollection(ArrayList::new));

                    for (Sequence s: tempSequences)
                        if (!originalModel.getEventSequenceGraph().get(tempE).contains(s))
                            modelCopy.addSequence(s);

                    tempE.setDecisionTable(diti);
                    modelOBDDNodeInsertion.add(modelCopy);
                }
            }
        }
        allModelMutants.addAll(modelOBDDNodeInsertion);
    }
    public void mutateESGByNodeOmission(EventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                DT_OBDD_Converter dt_obdd_converter = new DT_OBDD_Converter();
                OrderedBinaryDecisionDiagram obdd = dt_obdd_converter.DTtoOBDD(dt);

                OBDDMutantionOperators omo = new OBDDMutantionOperators();
                omo.terminalNodeOmission(obdd);

                List<DecisionTable> tempDTList = dt_obdd_converter.OBDDsToDTs(omo.getObddOmissionMutantSet());

                for (DecisionTable diti: tempDTList){
                    EventSequenceGraph modelCopy = originalModel.copyOfESG();
                    Event tempE = modelCopy.getKeyEvent(event);

                    List<Sequence> tempSequences = diti.getWorkingActions()
                            .stream()
                            .map(x -> new EventGenerator().dtActionToEsgEvent(x))
                            .map(x -> new Sequence(tempE, x))
                            .collect(Collectors.toCollection(ArrayList::new));

                    for (Sequence s: tempSequences)
                        if (!originalModel.getEventSequenceGraph().get(tempE).contains(s))
                            modelCopy.addSequence(s);

                    tempE.setDecisionTable(diti);
                    modelOBDDNodeOmission.add(modelCopy);
                }
            }
        }
        allModelMutants.addAll(modelOBDDNodeOmission);
    }

    public List<EventSequenceGraph> getModelDTActionOmission(){
        return modelDTActionOmission;
    }
    public List<EventSequenceGraph> getModelDTRuleOmission(){
        return modelDTRuleOmission;
    }
    public List<EventSequenceGraph> getModelDTActionInsertion(){
        return modelDTActionInsertion;
    }
    public List<EventSequenceGraph> getModelDTRuleInsertion(){
        return modelDTRuleInsertion;
    }
    public List<EventSequenceGraph> getModelOBDDNodeInsertion(){
        return modelOBDDNodeInsertion;
    }
    public List<EventSequenceGraph> getModelOBDDNodeOmission(){
        return modelOBDDNodeOmission;
    }
    public List<EventSequenceGraph> getAllModelMutants(){
        return allModelMutants;
    }

    public static void main(String[] args) {
        DecisionTable dt1 = new DecisionTable("VenSys");
        dt1.getDTFromFile("src\\resources\\Ventilation.txt");

        DecisionTableAugmentedEventSequenceGraph dt_esg1 = new DecisionTableAugmentedEventSequenceGraph("System", dt1);
        System.out.println("Original--" + dt_esg1);

        ModelMutator mm = new ModelMutator();

        mm.mutateESGByActionOmission(dt_esg1);

        System.out.println(mm.getModelDTActionOmission());
    }
}
