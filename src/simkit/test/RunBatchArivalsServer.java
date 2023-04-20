package simkit.test;

import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.components.BatchArrivalsServer;
import simkit.random.DiscreteRandomVariate;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 * Short verbose test of BatchArrivalsServer.
 *
 * @author ahbuss
 */
public class RunBatchArivalsServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Arrivals
        RandomVariate interrrivalTimeGenerator = RandomVariateFactory.getInstance("Exponential, 1.5");
        ArrivalProcess arrivalProcess = new ArrivalProcess(interrrivalTimeGenerator);
        System.out.println(arrivalProcess);

//        BatchArrivalsServer
        int totalNumberServers = 4;
        RandomVariate serviceTimeGenerator = RandomVariateFactory.getInstance("Gamma", 1.7, 1.8);
//        Discrete RandomVariates generate integers
        DiscreteRandomVariate batchGenerator
                = RandomVariateFactory.getDiscreteRandomVariateInstance("Binomial", 4, 0.6);
        BatchArrivalsServer batchArrivalsServer = new BatchArrivalsServer(totalNumberServers, serviceTimeGenerator, batchGenerator);
        System.out.println(batchArrivalsServer);

//        SimEventListener
        arrivalProcess.addSimEventListener(batchArrivalsServer);

//        For verbose checking state transitions
        SimplePropertyDumper simplePropertyDumperX = new SimplePropertyDumper(true);
        batchArrivalsServer.addPropertyChangeListener(simplePropertyDumperX);
        arrivalProcess.addPropertyChangeListener(simplePropertyDumperX);

        Schedule.setVerbose(true);

//        stop after 10 EndServie events
        Schedule.stopOnEvent(10, "EndService");
        Schedule.reset();
        Schedule.startSimulation();
    }

}
