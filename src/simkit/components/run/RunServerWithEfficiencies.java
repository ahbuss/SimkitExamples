package simkit.components.run;

import simkit.Entity;
import simkit.Schedule;
import simkit.components.CustomerCreator;
import simkit.components.Server;
import simkit.components.ServerWithEfficiencies2;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.MultipleSimpleStatsTimeVarying;
import simkit.stat.SimpleStatsTally;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class RunServerWithEfficiencies {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Entity[] allServers = new Entity[3];
        allServers[0] = new Server(0.8);
        allServers[1] = new Server(1.5);
        allServers[2] = new Server(1.0);

        ServerWithEfficiencies2 serverWithEfficiencies =
                new ServerWithEfficiencies2(allServers);
        System.out.println(serverWithEfficiencies);
        
        RandomVariate interarrivalTimeGenerator =
                RandomVariateFactory.getInstance("Uniform", 0.9, 2.2);
        RandomVariate serviceTimeGenerator =
                RandomVariateFactory.getInstance("Gamma", 2.1, 1.8);
        CustomerCreator customerCreator = 
                new CustomerCreator(interarrivalTimeGenerator, serviceTimeGenerator);
        System.out.println(customerCreator);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
//        serverWithEfficiencies.addPropertyChangeListener(simplePropertyDumper);
        
        customerCreator.addSimEventListener(serverWithEfficiencies);
        
        SimpleStatsTally delayInQueueStat = new SimpleStatsTally("delayInQueue");
        serverWithEfficiencies.addPropertyChangeListener(delayInQueueStat);
        
        MultipleSimpleStatsTimeVarying serverBusyStat = new MultipleSimpleStatsTimeVarying("busy");
        serverWithEfficiencies.addPropertyChangeListener(serverBusyStat);
        
        Schedule.setVerbose(false);
        
        Schedule.stopAtTime(100000.0);
        
        Schedule.reset();
        Schedule.startSimulation();
        
        System.out.printf("Simluation ended at time %,.1f%n", Schedule.getSimTime());
        System.out.printf("Avg delayInQueue = %,.4f%n", delayInQueueStat.getMean());
        
//        System.out.println(serverBusyStat);
        for (int i = 1; i < serverBusyStat.getAllSampleStat().length; ++i) {
            System.out.printf("Avg Utilization of server %d: %.4f%n", i, serverBusyStat.getMean(i));
        }
//        System.out.println(new Customer(123.456));
//        new Customer(-1.0);
//        for (Server server : allServers) {
//            System.out.println(server);
//        }
        
//        new Server(-1.0);
//        new Server(0.0);
    }

}
