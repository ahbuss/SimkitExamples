package thread.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.sqlite.JDBC.PREFIX;
import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.components.SimpleServer;
import simkit.random.RandomNumber;
import simkit.random.RandomNumberFactory;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import thread.Executor;
import static thread.MetricType.TIME_VARYING;
import thread.MetricsManager;
import thread.SimRunner;

/**
 *
 * @author ahbuss
 */
public class TestExecutor {

    private static final Logger LOGGER = Logger.getLogger(TestExecutor.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        ReentrantLock lock = new ReentrantLock();
        int numberProcessors = Runtime.getRuntime().availableProcessors();
        int numberTasks = numberProcessors * 3;
//        numberTasks = 2;
        double stopTime = 100000.0;
        int id;
        int numberReplications = 30;
        LOGGER.log(Level.INFO, "Attempting {0} tasks using {1} processors",
                new Object[]{numberTasks, numberProcessors});
        LOGGER.log(Level.INFO, "Each simulation will be run for {0} time units "
                + "and {1} replications", new Object[]{stopTime, numberReplications});

        Connection connection = DriverManager.getConnection(PREFIX.concat("output/SimOut.SQLite"));
        Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS SimOutput (SimID INTEGER, Name TEXT, Value REAL, Replications INTEGER)";
        statement.executeUpdate(createTable);
        statement.close();
        connection.setAutoCommit(false);

        for (int i = 0; i < numberProcessors; ++i) {
            lock.lock();
            try {
                id = Schedule.addNewEventList();
                Schedule.getEventList(id).stopAtTime(stopTime);
            } finally {
                lock.unlock();
            }

            RandomNumber rng = RandomNumberFactory.getInstance(id);
            RandomVariate iat = RandomVariateFactory.getInstance("Uniform", 0.9, 2.2);
            iat.setRandomNumber(rng);

            ArrivalProcess arrivalProcess = new ArrivalProcess(iat);
            arrivalProcess.setEventListID(id);

            int numberServers = 2;
            RandomVariate stg = RandomVariateFactory.getInstance("Gamma", 1.7, 1.8);
            stg.setRandomNumber(rng);

            SimpleServer simpleServer = new SimpleServer(numberServers, stg);
            simpleServer.setEventListID(id);
            arrivalProcess.addSimEventListener(simpleServer);

            Executor executor = new Executor(simpleServer, numberReplications);
            executor.setVerbose(false);
            MetricsManager metricsManager = new MetricsManager(simpleServer);
            metricsManager.setConnection(connection);
            metricsManager.addMetric("numberInQueue", TIME_VARYING);
            metricsManager.addMetric("numberAvailableServers", TIME_VARYING);
            executor.addPropertyChangeListener(metricsManager);

        }

        ExecutorService executorService = Executors.newFixedThreadPool(numberProcessors);
        for (int i = 0; i < numberTasks; ++i) {
            SimRunner simRunner = new SimRunner();
            executorService.execute(simRunner);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1L, TimeUnit.DAYS);
            connection.close();
        } catch (InterruptedException ex) {
            Logger.getLogger(TestExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

        LOGGER.info("Done!");

    }

}
