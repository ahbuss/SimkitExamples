package thread;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.BasicEventList;
import simkit.Schedule;
import simkit.examples.ArrivalProcess;
import simkit.examples.SimpleServer;
import simkit.random.RandomNumber;
import simkit.random.RandomNumberFactory;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.SimpleStatsTally;
import simkit.stat.SimpleStatsTimeVarying;

/**
 *
 * @author ahbuss
 */
public class MuliThreadQueues {

    private static final double STOP_TIME = 1000000.0;

    private static int numberCompleted;

    private static Map<Integer, Long> completedSims = new TreeMap<>();

    private static final ReentrantLock LOCK = new ReentrantLock();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numberPrecessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberPrecessors);
        int number = numberPrecessors * 20;
        System.out.printf("Will attempt %d parallel runs on %d processors%n", number, numberPrecessors);
        numberCompleted = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < number; ++i) {
            Task task = new Task();
            executorService.execute(task);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1L, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(MuliThreadQueues.class.getName()).log(Level.SEVERE, null, ex);
        }
        long end = System.currentTimeMillis();
        System.out.printf("%d simulations completed in %,.3f sec%n", completedSims.size(),
                (end - start) * 0.001);
//        for (int i: completedSims.keySet()) {
//            System.out.printf("%d: %d%n", i, completedSims.get(i));
//        }
    }

    private static class Task implements Runnable {

        private final int id;

        private SimpleStatsTally counter;

        private SimpleStatsTimeVarying numberInQueueStat;

        public Task() {
            LOCK.lock();
            try {
                id = Schedule.addNewEventList();
                setupSimulation();
            } finally {
                LOCK.unlock();
            }
        }

        private void setupSimulation() {
            RandomNumber rng = RandomNumberFactory.getInstance(id);
            RandomVariate iat = RandomVariateFactory.getInstance("Uniform", 0.9, 2.2);
            iat.setRandomNumber(rng);

            ArrivalProcess arrivalProcess = new ArrivalProcess(iat);
            arrivalProcess.setEventListID(id);

            int numberServers = 2;
            RandomVariate stg = RandomVariateFactory.getInstance("Gamma", 1.7, 1.8);

            SimpleServer simpleServer = new SimpleServer(numberServers, stg);
            simpleServer.setEventListID(id);
            arrivalProcess.addSimEventListener(simpleServer);

            numberInQueueStat = new SimpleStatsTimeVarying("numberInQueue");
            numberInQueueStat.setEventListID(id);
            simpleServer.addPropertyChangeListener(numberInQueueStat);

        }

        @Override
        public void run() {
            BasicEventList eventList = Schedule.getEventList(id);
            eventList.stopAtTime(STOP_TIME);
            eventList.reset();
            eventList.startSimulation();

            numberCompleted += 1;
            completedSims.put(id, Thread.currentThread().threadId());
            System.out.printf("Sim %,d (Thread %d) ended at time %,.2f with mean # in queue %,.3f%n",
                    id, Thread.currentThread().threadId(), eventList.getSimTime(), numberInQueueStat.getMean());
        }

    }

}
