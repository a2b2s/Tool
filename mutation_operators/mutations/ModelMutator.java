package mutation_operators.mutations;

import MTBDD.MTBDD;
import decision_table.Action;
import decision_table.DecisionTable;
import decision_table.Rule;
import dt_mtbdd_converter_mutator.Converter;
import model.dt_augmented_esg.DecisionTableAugmentedEventSequenceGraph;
import model.event_sequence_graph.Event;
import model.event_sequence_graph.EventGenerator;
import model.event_sequence_graph.EventSequenceGraph;
import model.event_sequence_graph.Sequence;
import mutation_operators.MTBDDMutantionOperators;
import ordered_binary_decision_diagram.Node;
import ordered_binary_decision_diagram.OrderedBinaryDecisionDiagram;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMutator {

    private List<DecisionTableAugmentedEventSequenceGraph> modelMTBDDNodeInsertion = new ArrayList<>();
    private List<DecisionTableAugmentedEventSequenceGraph> modelMTBDDNodeOmission = new ArrayList<>();
    private List<DecisionTableAugmentedEventSequenceGraph> modelMTBDDNodeCorruption = new ArrayList<>();
    private List<DecisionTableAugmentedEventSequenceGraph> modelMTBDDEdgeOmission = new ArrayList<>();
    private List<DecisionTableAugmentedEventSequenceGraph> modelMTBDDEdgeInsertion = new ArrayList<>();
    private List<DecisionTableAugmentedEventSequenceGraph> modelMTBDDEdgeCorruption = new ArrayList<>();
    private List<DecisionTableAugmentedEventSequenceGraph> modelMTBDDEdgeSwitch = new ArrayList<>();

    private List<DecisionTableAugmentedEventSequenceGraph> allModelMutants = new ArrayList<>();

    public void mutateESGByNodeInsertion(DecisionTableAugmentedEventSequenceGraph originalModel, Node node){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                Converter dt_mtbdd_converter = new Converter();
                MTBDD mtbdd = dt_mtbdd_converter.DT_into_MTBDD(dt);

                MTBDDMutantionOperators mtmo = new MTBDDMutantionOperators();
                mtmo.terminalNodeInsertion(mtbdd, node);

                //List<DecisionTable> tempDTList = dt_mtbdd_converter.MTBDD_into_DT(mtmo.getMtbddInsertedNodeMutantSet());

                /*for (DecisionTable diti: tempDTList){
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
                    modelMTBDDNodeInsertion.add(modelCopy);
                }*/
            }
        }
        allModelMutants.addAll(modelMTBDDNodeInsertion);
    }
    public void mutateESGByNodeOmission(DecisionTableAugmentedEventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                Converter converter = new Converter();
                MTBDD mtbdd = converter.DT_into_MTBDD(dt);

                MTBDDMutantionOperators mtmo = new MTBDDMutantionOperators();
                mtmo.terminalNodeOmission(mtbdd);

                /*List<DecisionTable> tempDTList = converter.MTBDD_into_DT(mtmo.getMtbddOmittedNodeMutantSet());

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
                    modelMTBDDNodeOmission.add(modelCopy);
                }*/
            }
        }
        allModelMutants.addAll(modelMTBDDNodeOmission);
    }
    public void mutateESGByNodeCorruption(DecisionTableAugmentedEventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                Converter converter = new Converter();
                MTBDD mtbdd = converter.DT_into_MTBDD(dt);

                MTBDDMutantionOperators mtmo = new MTBDDMutantionOperators();
                mtmo.terminalNodeOmission(mtbdd);

                /*List<DecisionTable> tempDTList = converter.MTBDD_into_DT(mtmo.getMtbddOmittedNodeMutantSet());

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
                    modelMTBDDNodeOmission.add(modelCopy);
                }*/
            }
        }
        allModelMutants.addAll(modelMTBDDNodeOmission);
    }
    public void mutateESGByEdgeOmission(DecisionTableAugmentedEventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                Converter converter = new Converter();
                MTBDD mtbdd = converter.DT_into_MTBDD(dt);

                MTBDDMutantionOperators mtmo = new MTBDDMutantionOperators();
                mtmo.terminalNodeOmission(mtbdd);

                /*List<DecisionTable> tempDTList = converter.MTBDD_into_DT(mtmo.getMtbddOmittedNodeMutantSet());

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
                    modelMTBDDNodeOmission.add(modelCopy);
                }*/
            }
        }
        allModelMutants.addAll(modelMTBDDNodeOmission);
    }
    public void mutateESGByEdgeInsertion(DecisionTableAugmentedEventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                Converter converter = new Converter();
                MTBDD mtbdd = converter.DT_into_MTBDD(dt);

                MTBDDMutantionOperators mtmo = new MTBDDMutantionOperators();
                mtmo.terminalNodeOmission(mtbdd);

                /*List<DecisionTable> tempDTList = converter.MTBDD_into_DT(mtmo.getMtbddOmittedNodeMutantSet());

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
                    modelMTBDDNodeOmission.add(modelCopy);
                }*/
            }
        }
        allModelMutants.addAll(modelMTBDDNodeOmission);
    }
    public void mutateESGByEdgeCorruption(DecisionTableAugmentedEventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                Converter converter = new Converter();
                MTBDD mtbdd = converter.DT_into_MTBDD(dt);

                MTBDDMutantionOperators mtmo = new MTBDDMutantionOperators();
                mtmo.terminalNodeOmission(mtbdd);

                /*List<DecisionTable> tempDTList = converter.MTBDD_into_DT(mtmo.getMtbddOmittedNodeMutantSet());

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
                    modelMTBDDNodeOmission.add(modelCopy);
                }*/
            }
        }
        allModelMutants.addAll(modelMTBDDNodeOmission);
    }
    public void mutateESGByEdgeSwitch(DecisionTableAugmentedEventSequenceGraph originalModel){
        for(Event event: originalModel.getEventSequenceGraph().keySet()){
            if (event.isDTExtendedEvent()){
                DecisionTable dt = event.getDecisionTable();
                Converter converter = new Converter();
                MTBDD mtbdd = converter.DT_into_MTBDD(dt);

                MTBDDMutantionOperators mtmo = new MTBDDMutantionOperators();
                mtmo.terminalNodeOmission(mtbdd);

                /*List<DecisionTable> tempDTList = converter.MTBDD_into_DT(mtmo.getMtbddOmittedNodeMutantSet());

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
                    modelMTBDDNodeOmission.add(modelCopy);
                }*/
            }
        }
        allModelMutants.addAll(modelMTBDDNodeOmission);
    }

    public List<DecisionTableAugmentedEventSequenceGraph> getModelMTBDDNodeInsertion(){
        return modelMTBDDNodeInsertion;
    }
    public List<DecisionTableAugmentedEventSequenceGraph> getModelMTBDDNodeOmission(){
        return modelMTBDDNodeOmission;
    }
    public List<DecisionTableAugmentedEventSequenceGraph> getModelMTBDDNodeCorruption(){
        return modelMTBDDNodeCorruption;
    }
    public List<DecisionTableAugmentedEventSequenceGraph> getModelMTBDDEdgeOmission(){
        return modelMTBDDEdgeOmission;
    }
    public List<DecisionTableAugmentedEventSequenceGraph> getModelMTBDDEdgeInsertion(){
        return modelMTBDDEdgeInsertion;
    }
    public List<DecisionTableAugmentedEventSequenceGraph> getModelMTBDDEdgeCorruption(){
        return modelMTBDDEdgeCorruption;
    }
    public List<DecisionTableAugmentedEventSequenceGraph> getModelMTBDDEdgeSwitch(){
        return modelMTBDDEdgeSwitch;
    }
    public List<DecisionTableAugmentedEventSequenceGraph> getAllModelMutants(){
        return allModelMutants;
    }

    /*public static void main(String[] args) {
        DecisionTable dt1 = new DecisionTable("VenSys");
        dt1.getDTFromFile("src\\resources\\Ventilation.txt");

        DecisionTableAugmentedEventSequenceGraph dt_esg1 = new DecisionTableAugmentedEventSequenceGraph("System", dt1);
        System.out.println("Original--" + dt_esg1);

        ModelMutator mm = new ModelMutator();

        mm.mutateESGByActionOmission(dt_esg1);

        System.out.println(mm.getModelDTActionOmission());
    }*/
}
