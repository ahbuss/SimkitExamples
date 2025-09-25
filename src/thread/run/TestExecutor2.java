package thread.run;

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import simkit.SimEntityBase;
import simkit.examples.ArrivalProcess;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.SampleStatistics;
import thread.SimExecutor;

/**
 *
 * @author arnoldbuss
 */
public class TestExecutor2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numberProcessors = Runtime.getRuntime().availableProcessors();
        int numberSimulations = 20;
        int numberThreads = min(numberSimulations, numberProcessors);
        int numberReplications = 30;

        List<SimEntityBase> simulations = new ArrayList<>();
        List<SampleStatistics> stats = new ArrayList<>();
        for (int i = 1; i <= numberSimulations; ++i) {
            RandomVariate interarrivalTimeGenerator = RandomVariateFactory.getInstance("Exponential", i);
            ArrivalProcess arrivalProcess = new ArrivalProcess(interarrivalTimeGenerator);
            arrivalProcess.setEventListID(i);
            simulations.add(arrivalProcess);
            SimExecutor executor = new SimExecutor(arrivalProcess, numberSimulations);
        }

        RandomVariate interarrivalTimeGenerator = RandomVariateFactory.getInstance("Exponential", 1.0);

    }

}
