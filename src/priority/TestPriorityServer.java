package priority;

import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.MultipleSimpleStatsTally;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestPriorityServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate[] interarrivalTimeGenerator = new RandomVariate[] {
            RandomVariateFactory.getInstance("Exponential", 2.6),
            RandomVariateFactory.getInstance("Exponential", 1.5),
        };
        
        PriorityEntityCreator[] creator = new PriorityEntityCreator[interarrivalTimeGenerator.length];
        for (int i = 0; i < creator.length; ++i) {
            creator[i] = new PriorityEntityCreator(interarrivalTimeGenerator[i], i + 1);
            System.out.println(creator[i]);
        }
        
        RandomVariate serviceTimeGenerator = RandomVariateFactory.getInstance("Gamma", 2.1, 2.4);
        int totalNumberServers = 6;
        
        PriorityServer priorityServer = new PriorityServer(serviceTimeGenerator, totalNumberServers);
        System.out.println(priorityServer);
        
        for (PriorityEntityCreator c: creator) {
            c.addSimEventListener(priorityServer);
        }
        
        MultipleSimpleStatsTally delayInQueueStat = new MultipleSimpleStatsTally("delayInQueue");
        priorityServer.addPropertyChangeListener(delayInQueueStat);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
//        priorityServer.addPropertyChangeListener(simplePropertyDumper);
        
        double stopTime = 1000000.0;
        Schedule.setVerbose(false);
        Schedule.stopAtTime(stopTime);
        
        Schedule.reset();
        Schedule.startSimulation();
        
        System.out.printf("Simulation ended at time %,.1f%n", Schedule.getSimTime());
        for (int i = 1; i <= 2; ++i) {
            System.out.printf("Avg delay in queue for priority %d customers: %,.4f%n", i, delayInQueueStat.getMean(i));
        }
    }

}
