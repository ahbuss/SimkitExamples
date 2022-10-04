package simkit.nointrospect;

import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestArrivalProcess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate interarrivalTimeGenerator
                = RandomVariateFactory.getInstance("Exponential", 1.7);
        ArrivalProcess arrivalProcess = new ArrivalProcess(interarrivalTimeGenerator);
        System.out.println(arrivalProcess);

        int totalNumberServers = 1;
        RandomVariate serviceTimeGenerator = RandomVariateFactory.getInstance("Constant", 1.6);
        SimpleServer simpleServer = new SimpleServer(totalNumberServers, serviceTimeGenerator);
        System.out.println(simpleServer);

        arrivalProcess.addSimEventListener(simpleServer);

        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        arrivalProcess.addPropertyChangeListener(simplePropertyDumper);
        simpleServer.addPropertyChangeListener(simplePropertyDumper);

        Schedule.setVerbose(true);
        Schedule.stopAtTime(20.0);

        Schedule.reset();
        Schedule.startSimulation();
        
        System.out.printf("At time %,.2f there have been %,d served%n", Schedule.getSimTime(),
                simpleServer.getNumberServed());
    }

}
