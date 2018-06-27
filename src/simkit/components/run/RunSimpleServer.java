package simkit.components.run;

import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.components.SimpleServer;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.SimpleStatsTimeVarying;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class RunSimpleServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        RandomVariate interarrivalTimeGenerator = 
                RandomVariateFactory.getInstance("Uniform", 0.9, 2.2);
        ArrivalProcess arrivalProcess = new ArrivalProcess(interarrivalTimeGenerator);
        
        int totalNumberServers = 2;
        RandomVariate serviceTimeGenerator = 
                RandomVariateFactory.getInstance("Gamma", 1.7, 1.8);
        
        SimpleServer simpleServer = new SimpleServer( totalNumberServers, serviceTimeGenerator);
        
        arrivalProcess.addSimEventListener(simpleServer);
        
        System.out.println(arrivalProcess);
        System.out.println(simpleServer);

        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
//        uncomment for verbose mode
//        simpleServer.addPropertyChangeListener(simplePropertyDumper);
//        arrivalProcess.addPropertyChangeListener(simplePropertyDumper);
        
        SimpleStatsTimeVarying numberInQueueStat = new SimpleStatsTimeVarying("numberInQueue");
        simpleServer.addPropertyChangeListener(numberInQueueStat);
        
        SimpleStatsTimeVarying numberAvailableServersStat = new SimpleStatsTimeVarying("numberAvailableServers");
        simpleServer.addPropertyChangeListener(numberAvailableServersStat);
        
//        Schedule.setVerbose(true);
//        double stopTime = 20.0;
        double stopTime = 100000.0;
        Schedule.stopAtTime(stopTime);
        
        Schedule.reset();
        Schedule.startSimulation();
        
        System.out.printf("Simulation ended at time %,.1f%n%n", Schedule.getSimTime());
        System.out.printf("There have been %,d arrivals%n", arrivalProcess.getNumberArrivals());
        System.out.printf("There have been %,d customers served%n", simpleServer.getNumberServed());
        System.out.printf("Average # in queue:\t%,.3f%n", numberInQueueStat.getMean());
        System.out.printf("Average utilization\t%.3f%n", (1.0 - numberAvailableServersStat.getMean()/ simpleServer.getTotalNumberServers()));
    }

}
