package misctests;

import java.time.LocalDate;
import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestStartingTime {

    private static final int MIN_PER_DAY = 24 * 60;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate rv = RandomVariateFactory.getInstance("DiscreteInteger", 
                    new int[] { MIN_PER_DAY, 2*MIN_PER_DAY, 5*MIN_PER_DAY},
                        new double[] { 3, 4, 5});
        long startingDate = LocalDate.now().toEpochDay();
        DelayedArrivalProcess arrivalProcess = new DelayedArrivalProcess(rv, startingDate);
        System.out.println(arrivalProcess);
                
        Schedule.setVerbose(true);
        Schedule.stopOnEvent(10, "Arrival");
        
        Schedule.reset();
        Schedule.startSimulation();
        
    }

}
