package simkit.util.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.Schedule;
import simkit.components.EntityCreator;
import simkit.components.EntityServer;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.CollectionSizeTimeVaryingStats;
import simkit.stat.SimpleStatsTally;
import simkit.stat.SimpleStatsTimeVarying;
import simkit.util.StateChangeLogger;

/**
 *
 * @author ahbuss
 */
public class TestStateChangeLogger {

    private static final Logger LOGGER = Logger.getLogger(TestStateChangeLogger.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            PrintStream printStream = new PrintStream("output.txt");
            StateChangeLogger stateChangeLogger = new StateChangeLogger(System.out);
            RandomVariate rv = RandomVariateFactory.getInstance(
                    "Uniform", 0.9, 2.2);
            EntityCreator entityCreator = new EntityCreator(rv);

            String rvName = "Gamma";
            double alpha = 1.7;
            double beta = 1.8;

            rv = RandomVariateFactory.getInstance(rvName, alpha, beta);

            int numServ = 2;
            EntityServer entityServer = new EntityServer(numServ, rv);

            entityCreator.addSimEventListener(entityServer);
            
            entityCreator.addPropertyChangeListener(stateChangeLogger);
            entityServer.addPropertyChangeListener(stateChangeLogger);

//        This is for debugging - uncomment to view state changes for the
//        EntityServer instance.
//        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
//        entityServer.addPropertyChangeListener(simplePropertyDumper);
            CollectionSizeTimeVaryingStats numberInQueue = new CollectionSizeTimeVaryingStats("queue");
            SimpleStatsTimeVarying numberAvailableServersStat = new SimpleStatsTimeVarying("numberAvailableServers");

            SimpleStatsTally delayInQueueStats = new SimpleStatsTally("delayInQueue");
            SimpleStatsTally timeInSystemStats = new SimpleStatsTally("timeInSystem");

            entityServer.addPropertyChangeListener(numberInQueue);
            entityServer.addPropertyChangeListener(numberAvailableServersStat);
            entityServer.addPropertyChangeListener(delayInQueueStats);
            entityServer.addPropertyChangeListener(timeInSystemStats);

            System.out.println(entityCreator);
            System.out.println(entityServer);

//        Schedule.setVerbose(true);
//        Schedule.setSingleStep(false);
//        double stopTime = 2.0;
            double stopTime = 500.0;

            Schedule.stopAtTime(stopTime);
            Schedule.reset();
            Schedule.startSimulation();
            entityServer.firePropertyChange("end", true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestStateChangeLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
