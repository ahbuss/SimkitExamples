package simkit.stat;

import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestStateDump {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrivalProcess arrivalProcess = new ArrivalProcess();
        RandomVariate interarrivalTimeGenerator = RandomVariateFactory.getInstance("Exponential", 0.25);
        arrivalProcess.setInterarrivalTimeGenerator(interarrivalTimeGenerator);

        SimpleStatsTally dailyArrivals = new SimpleStatsTally("numberArrivals");
        arrivalProcess.addPropertyChangeListener(dailyArrivals);

        PeriodicStateDumper dumper = new PeriodicStateDumper(dailyArrivals, 1.0, System.out);

        System.out.println(arrivalProcess);

        Schedule.stopAtTime(20.0);

        int numberReplications = 5;

        for (int replication = 1; replication <= numberReplications; ++replication) {
            System.out.printf("Replication %d:%n", replication);
            Schedule.reset();
            Schedule.startSimulation();
        }
    }

}
