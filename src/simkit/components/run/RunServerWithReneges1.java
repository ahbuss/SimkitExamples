package simkit.components.run;

import java.io.File;
import simkit.Schedule;
import simkit.components.RenegingCustomerCreator;
import simkit.components.ServerWithReneges;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.SimpleStatsTally;
import simkit.stat.SimpleStatsTimeVarying;
import simkit.stat.CollectionSizeTimeVaryingStats;
import simkit.util.CSVDataLogger;
import simkit.util.SimplePropertyDumper;

/**
 * Impatient customers are served by a multiple server serverWithReneges. Each
 * customer has a certain amount of time s/he is willing to wait in the
 * serverWithReneges, after which s/he will exit the serverWithReneges
 * ("renege").
 * <p>
 * Output:
 * <pre>
RenegingCustomerArrivalProcess.1
	renegeTimeGenerator = Uniform (2.000, 6.000)
	interarrivalTimeGenerator = Exponential (1.500)
ServerWithReneges.2
	serviceTimeGenerator = Gamma (2.500, 1.200)
	totalNumberServers = 2

Simulation ended at time 100,000.0

	   Number Arrivals: 66,645
	     Number Served: 54,995
	    Number Reneges: 11,648
	   Percent Reneges: 0.1748
	    Avg # in Queue: 1.1088
	   Avg Utilization: 0.8220
	Avg Delay in Queue Served: 1.3333
	Avg Delay in Queue Reneged: 3.2242
	Avg Time in System: 4.3227
 * </pre>
 *
 * @author ahbuss
 */
public class RunServerWithReneges1 {

    public static void main(String[] args) {
        RandomVariate interarrivalTimeGenerator = RandomVariateFactory.getInstance("Exponential", 1.5);

        RandomVariate renegeTimeGenerator = RandomVariateFactory.getInstance("Uniform", 2.0, 6.0);
        RenegingCustomerCreator renegingCustomerCreator
                = new RenegingCustomerCreator(
                        interarrivalTimeGenerator,
                        renegeTimeGenerator);

        RandomVariate serviceTimeGenerator = RandomVariateFactory.getInstance("Gamma", 2.5, 1.2);
        ServerWithReneges serverWithReneges = new ServerWithReneges(2, serviceTimeGenerator);

        renegingCustomerCreator.addSimEventListener(serverWithReneges);

        CollectionSizeTimeVaryingStats numberInQueueStat = new CollectionSizeTimeVaryingStats("queue");
        SimpleStatsTimeVarying numberAvailableServersStat = new SimpleStatsTimeVarying("numberAvailableServers");
        SimpleStatsTally delayInQueueServedStat = new SimpleStatsTally("delayInQueueServed");
        SimpleStatsTally delayInQueueRenegedStat = new SimpleStatsTally("delayInQueueReneged");
        SimpleStatsTally timeInSystemStat = new SimpleStatsTally("timeInSystem");

        serverWithReneges.addPropertyChangeListener(numberInQueueStat);
        serverWithReneges.addPropertyChangeListener(numberAvailableServersStat);
        serverWithReneges.addPropertyChangeListener(delayInQueueServedStat);
        serverWithReneges.addPropertyChangeListener(delayInQueueRenegedStat);
        serverWithReneges.addPropertyChangeListener(timeInSystemStat);

        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
//        serverWithReneges.addPropertyChangeListener(simplePropertyDumper);

        System.out.println(renegingCustomerCreator);
        System.out.println(serverWithReneges);
        
        File outputFile = new File("output.csv");
        CSVDataLogger csvDataLogger = new CSVDataLogger(outputFile);
        serverWithReneges.addSimEventListener(csvDataLogger);

        Schedule.setVerbose(false);
        double stopTime = 100000.0;
//        stopTime = 20.0;
        Schedule.stopAtTime(stopTime);

        Schedule.reset();
        Schedule.startSimulation();
        
        csvDataLogger.close();

        System.out.printf("%nSimulation ended at time %,.1f%n", Schedule.getSimTime());
        System.out.printf("\n\t   Number Arrivals: %,d%n", renegingCustomerCreator.getNumberArrivals());
        System.out.printf("\t     Number Served: %,d%n", timeInSystemStat.getCount());
        System.out.printf("\t    Number Reneges: %,d%n", serverWithReneges.getNumberReneges());
        System.out.printf("\t   Percent Reneges: %.4f%n",
                 (double) serverWithReneges.getNumberReneges()
                / (renegingCustomerCreator.getNumberArrivals() - serverWithReneges.getQueue().size()));
        System.out.printf("\t    Avg # in Queue: %,.4f%n", numberInQueueStat.getMean());
        System.out.printf("\t   Avg Utilization: %.4f%n",
                1.0 - numberAvailableServersStat.getMean() / serverWithReneges.getTotalNumberServers());
        System.out.printf("\tAvg Delay in Queue Served: %.4f%n", delayInQueueServedStat.getMean());
        System.out.printf("\tAvg Delay in Queue Reneged: %,.4f%n", delayInQueueRenegedStat.getMean());
        System.out.printf("\tAvg Time in System: %.4f%n", timeInSystemStat.getMean());

    }
}
