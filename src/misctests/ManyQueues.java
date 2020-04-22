package misctests;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.components.SimpleServer;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class ManyQueues {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate interarrivalTimeGenerator =
                RandomVariateFactory.getInstance("Exponential", 1.7);
        
        RandomVariate serviceTimeGenerator = RandomVariateFactory.getInstance("Gamma", 2.2, 2.3);
        int k = 3;
        
        int number = 10000;
        
        for (int i = 0; i < number; ++i) {
        
            ArrivalProcess arrivalProcess = new ArrivalProcess(interarrivalTimeGenerator);
            SimpleServer simpleServer = new SimpleServer(k, serviceTimeGenerator);
            arrivalProcess.addSimEventListener(simpleServer);
        }
        
        LocalDateTime start = LocalDateTime.now();
        
        double stopTime = 100000;
        
        Schedule.stopAtTime(stopTime);
        Schedule.reset();
        Schedule.startSimulation();
        
        LocalDateTime end = LocalDateTime.now();
        long elapsed = ChronoUnit.MILLIS.between(start, end);
        
        System.out.printf("%,.3f secs to execute %,d copies for %,.2f time units%n", 
                 0.001 * elapsed, number, stopTime);
    }

}
