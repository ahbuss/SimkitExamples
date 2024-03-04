package simkit.random;

import static java.lang.Double.NaN;
import simkit.Schedule;
import simkit.components.ArrivalProcess;

/**
 *
 * @author ahbuss
 */
public class TestResettingTraceVariate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate discrete = RandomVariateFactory.getInstance("DiscreteInteger", new int[] {10, 20, 30, 40},
                new double[]{4,3,2,1});
        RandomVariate resetting = RandomVariateFactory.getInstance("ResettingTrace",
                new double[0], NaN, discrete, 100);

        ArrivalProcess arrivalProcess = new ArrivalProcess(resetting);
        System.out.println(arrivalProcess);

        ((ResettingTraceVariate) resetting).setEventList(arrivalProcess.getEventList());
        int numberReplications = 20;
        for (int replication = 1; replication <= numberReplications; ++replication) {
            System.out.printf("Starting replication %,d%n", replication);
            Schedule.setVerbose(false);
            Schedule.reset();
            Schedule.startSimulation();
            System.out.printf("Replication %,d ended at simTime %,.2f%n", replication, Schedule.getSimTime());
        }
    }

}
