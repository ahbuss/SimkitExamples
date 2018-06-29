package simkit.components.run;

import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.components.IntermediateMachineFailure;
import simkit.components.ServerWithFailure;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.CollectionSizeTimeVaryingStats;
import simkit.stat.SimpleStatsTimeVarying;
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
        
        int numberRepairPeople = 3;
        int numberMachines = 7;
        ServerWithFailure[] allServers = new ServerWithFailure[numberMachines];
        for (int i = 0; i < allServers.length; ++i) {
            allServers[i] = new ServerWithFailure();
        }
        RandomVariate timeToFailureGenerator
                = RandomVariateFactory.getInstance("Weibull", 1.5, 20.3);
        RandomVariate repairTimeGenerator
                = RandomVariateFactory.getInstance("Exponential", 1.6);
        RandomVariate processingTimeGenerator
                = RandomVariateFactory.getInstance("Gamma", 3.7, 2.5);

        IntermediateMachineFailure intermediateMachineFailure
                = new IntermediateMachineFailure(numberRepairPeople,
                        allServers,
                        timeToFailureGenerator,
                        repairTimeGenerator,
                        processingTimeGenerator);
        System.out.println(intermediateMachineFailure);
        
        arrivalProcess.addSimEventListener(intermediateMachineFailure);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
//        intermediateMachineFailure.addPropertyChangeListener(simplePropertyDumper);
        
        SimpleStatsTimeVarying numberJobsInQueueStat = new SimpleStatsTimeVarying("numberJobsInQueue");
        intermediateMachineFailure.addPropertyChangeListener(numberJobsInQueueStat);
        
        CollectionSizeTimeVaryingStats repairQueueStat =
                new CollectionSizeTimeVaryingStats("repairQueue");
        intermediateMachineFailure.addPropertyChangeListener(repairQueueStat);
        
        CollectionSizeTimeVaryingStats availableMachinesStat =
                new CollectionSizeTimeVaryingStats("availableServers");
        intermediateMachineFailure.addPropertyChangeListener(availableMachinesStat);
        
//        Schedule.stopOnEvent(10, "Failure", ServerWithFailure.class);
        Schedule.stopAtTime(100000);
        
        Schedule.setVerbose(false);
        
        Schedule.reset();
        Schedule.startSimulation();
        
        System.out.printf("Simulation ended at time %,.2f%n", Schedule.getSimTime());
        System.out.printf("There were %d machines and %d repair people%n", 
                intermediateMachineFailure.getAllServers().length, intermediateMachineFailure.getNumberRepairPeople());
        System.out.printf("Avg # jobs in queue: %.3f%n", numberJobsInQueueStat.getMean());
        System.out.printf("Avg # machines in repair queue: %.3f%n", repairQueueStat.getMean());
        System.out.printf("Avg # available machines: %.3f%n", availableMachinesStat.getMean());
        System.out.printf("There were %,d broken parts out of %,d due to machine failure%n", 
                intermediateMachineFailure.getNumberBrokenParts(), arrivalProcess.getNumberArrivals());
        System.out.printf("%% parts lost: %.4f%n", 100.0 * intermediateMachineFailure.getNumberBrokenParts() /
                arrivalProcess.getNumberArrivals());
    }

}
