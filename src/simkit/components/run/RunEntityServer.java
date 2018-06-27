package simkit.components.run;

import java.io.File;
import java.text.DecimalFormat;
import simkit.Schedule;
import simkit.components.EntityCreator;
import simkit.components.EntityServer;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.CollectionSizeTimeVaryingStats;
import simkit.stat.SimpleStatsTimeVarying;
import simkit.stat.SimpleStatsTally;
import simkit.util.CSVPropertyDataLogger;

/**
 * Simple test of EntityServer class.  This should produce identical 
 * results for time-varying statistics as the simple Server test.  Also,
 * delay in queue and time in system are directly computed and estimated
 * via Little's formula.
 *  <dl><dt><b>Output:</b></dt></dl><pre>
 * <pre>
EntityArrivalProcess.1
	interarrivalTimeGenerator = Uniform (0.9, 2.2)
EntityServer.2
	serviceTimeGenerator = Gamma (1.7, 1.8)
	totalNumberServers = 2

Simulation ended at time 10000.0

There have been 6461 arrivals
There have been 6449 customers served

Average number in queue:	11.4528
Average utilization:    	0.9737
Average arrival rate:    	0.6461

Via Direct Tally:
Average delay in queue:  	17.7413
Average time in System:  	20.7608

Via Litle's Formula:
Average delay in queue:  	17.7260
Average Time in System:  	20.7400</pre>
 * 
 * @version $Id: RunEntityServer.java 2114 2017-05-02 11:28:31Z ahbuss $
 * @author ahbuss
 */
public class RunEntityServer {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        RandomVariate rv = RandomVariateFactory.getInstance(
            "Uniform", 0.9, 2.2 );
        EntityCreator entityCreator = new EntityCreator(rv);
        
        String rvName = "Gamma";
        double alpha = 1.7;
        double beta = 1.8;
        
        rv = RandomVariateFactory.getInstance( rvName, alpha, beta );
        
        int numServ = 2;
        EntityServer entityServer = new EntityServer(numServ, rv);
        
        entityCreator.addSimEventListener(entityServer);
        
//        This is for debugging - uncomment to view state changes for the
//        EntityServer instance.
//        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
//        entityServer.addPropertyChangeListener(simplePropertyDumper);
        
        CollectionSizeTimeVaryingStats numberInQueue = new CollectionSizeTimeVaryingStats("queue");
        SimpleStatsTimeVarying numberAvailableServersStat = new SimpleStatsTimeVarying("numberAvailableServers");
        
        SimpleStatsTally delayInQueueStats = new SimpleStatsTally("delayInQueue");
        SimpleStatsTally timeInSystemStats = new SimpleStatsTally("timeInSystem");
        
        entityServer.addPropertyChangeListener(numberInQueue);
        entityServer.addPropertyChangeListener(numberAvailableServersStat);
        entityServer.addPropertyChangeListener(delayInQueueStats);
        entityServer.addPropertyChangeListener(timeInSystemStats);
        
        System.out.println(entityCreator);
        System.out.println(entityServer);
        
//        Schedule.setVerbose(true);
        
//        Schedule.setSingleStep(false);
        
//        double stopTime = 2.0;
        double stopTime = 10000.0;
        
        Schedule.stopAtTime(stopTime);
        Schedule.reset();
        Schedule.startSimulation();
                
        DecimalFormat df = new DecimalFormat("0.0000");
        
        System.out.println("\nSimulation ended at time " + Schedule.getSimTime());
        System.out.println("\nThere have been " + entityCreator.getNumberArrivals() 
            + " arrivals");
        System.out.println("There have been " + timeInSystemStats.getCount()+
            " customers served\n");
        System.out.println("Average number in queue:\t" + df.format(numberInQueue.getMean()));
        System.out.println("Average utilization:    \t" + 
            df.format(1.0 - numberAvailableServersStat.getMean()/entityServer.getTotalNumberServers()));
        double averageArrivalRate = entityCreator.getNumberArrivals()/ Schedule.getSimTime();
        System.out.println("Average arrival rate:    \t" + df.format(averageArrivalRate));
        System.out.println("\nVia Direct Tally:");
        System.out.println("Average delay in queue:  \t" + df.format(delayInQueueStats.getMean()));
        System.out.println("Average time in System:  \t" + df.format(timeInSystemStats.getMean()));

        System.out.println("\nVia Litle's Formula:");
        System.out.println("Average delay in queue:  \t" +
                df.format(numberInQueue.getMean() / averageArrivalRate));
        System.out.println("Average Time in System:  \t" +
                df.format((numberInQueue.getMean() + entityServer.getTotalNumberServers() - 
                numberAvailableServersStat.getMean())/averageArrivalRate));
    }
} 