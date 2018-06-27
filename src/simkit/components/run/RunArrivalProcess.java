package simkit.components.run;

import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 * A small, verbose test of ArrivalProcess with all state transitions "dumped"
 * by the SimplePropertyDumper object.
 * @author ahbuss
 */
public class RunArrivalProcess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Instantiate an arrivalProcess with Exponential(3.2) interarrival times
        RandomVariate rv = RandomVariateFactory.getInstance("Exponential", 3.2);
        ArrivalProcess arrivalProcess = new ArrivalProcess(rv);
//        arrivalProcess.setInterarrivalTimeGenerator(rv);
//        Note that the toString() method returns the component's parameters and
//        their respective values (in this case, just one parameter)
        System.out.println(arrivalProcess);
        
//        Instantiate a SimplePropertyDumper and have it listen to arrivalProcess
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
        arrivalProcess.addPropertyChangeListener(simplePropertyDumper);
        
//        With verbose "true", the EventList is written to the console after each
//        event is processed
        Schedule.setVerbose(true);
//        Stop simulation run at time 15.0
        Schedule.stopAtTime(15.0);
//        Schedule.reset() invokes the reset() method on every SimEntity component
//        that has been instantiated (in this case just the one). It also puts
//        each component's Run event on the Event List
        Schedule.reset();
//        This kicks off the Event List alogorithm and returns when the Event List 
//        is empty.
        Schedule.startSimulation();
        
//        Print "results"
        System.out.printf("At time %,.1f there have been %d arrivals%n", 
                Schedule.getSimTime(), arrivalProcess.getNumberArrivals());
        
    }

}
