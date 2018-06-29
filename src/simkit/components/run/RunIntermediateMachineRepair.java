package simkit.components.run;

import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.components.IntermediateMachineFailure;
import simkit.components.ServerWithFailure;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class RunIntermediateMachineRepair {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        RandomVariate interarrivalTimeGenerator = 
                RandomVariateFactory.getInstance("Exponential", 2.7);
        ArrivalProcess arrivalProcess = new ArrivalProcess(interarrivalTimeGenerator);
        
        int numberRepairPeople = 1;
        ServerWithFailure[] allServers = new ServerWithFailure[6];
        for (int i = 0; i < allServers.length; ++i) {
            allServers[i] = new ServerWithFailure();
        }
        RandomVariate timeToFailureGenerator
                = RandomVariateFactory.getInstance("Gamma", 20.2, 1.3);
        RandomVariate repairTimeGenerator
                = RandomVariateFactory.getInstance("Exponential", 0.5);
        RandomVariate processingTimeGenerator
                = RandomVariateFactory.getInstance("Gamma", 6.7, 2.5);

        IntermediateMachineFailure intermediateMachineFailure
                = new IntermediateMachineFailure(numberRepairPeople,
                        allServers,
                        timeToFailureGenerator,
                        repairTimeGenerator,
                        processingTimeGenerator);
        System.out.println(intermediateMachineFailure);
        
        arrivalProcess.addSimEventListener(intermediateMachineFailure);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
        intermediateMachineFailure.addPropertyChangeListener(simplePropertyDumper);
        
        Schedule.stopOnEvent(10, "Failure", ServerWithFailure.class);
        
        Schedule.setVerbose(true);
        
        Schedule.reset();
        Schedule.startSimulation();
    }

}
