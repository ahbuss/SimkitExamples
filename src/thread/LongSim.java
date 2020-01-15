package thread;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.random.RandomNumber;
import simkit.random.RandomNumberFactory;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class LongSim extends SimEntityBase {

    private int numberEvents;

    private RandomVariate timeBetweenEvents;

    public LongSim() {
    }

    public LongSim(int numberEvents, RandomVariate timeBetweenEvents) {
        this.setNumberEvents(numberEvents);
        this.setTimeBetweenEvents(timeBetweenEvents);
    }

    public void doRun() {
        for (int i = 0; i < getNumberEvents(); ++i) {
            waitDelay("Ping", timeBetweenEvents);
        }
    }

    public void doPing() {
        waitDelay("Ping", timeBetweenEvents);
    }

    /**
     * @return the numberEvents
     */
    public int getNumberEvents() {
        return numberEvents;
    }

    /**
     * @param numberEvents the numberEvents to set
     */
    public final void setNumberEvents(int numberEvents) {
        this.numberEvents = numberEvents;
    }

    /**
     * @return the timeBetweenEvents
     */
    public RandomVariate getTimeBetweenEvents() {
        return timeBetweenEvents;
    }

    /**
     * @param timeBetweenEvents the timeBetweenEvents to set
     */
    public final void setTimeBetweenEvents(RandomVariate timeBetweenEvents) {
        this.timeBetweenEvents = timeBetweenEvents;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.printf("Running on %s with %s architecture%n", System.getProperty("os.name"), System.getProperty("os.arch"));
        int numberProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberProcessors);

        long[] seeds = new long[numberProcessors];
        RandomNumber rng = RandomNumberFactory.getInstance("Tausworthe");
        for (int i = 0; i < seeds.length; ++i) {
            seeds[i] = rng.drawLong();
        }
        
        int numberEvents = 1000;
        double stopTime = 10000;
        Schedule.stopAtTime(stopTime);
        LocalDateTime start = LocalDateTime.now();
        int numberRuns = numberProcessors * 2;
        System.out.printf("Starting %d runs with %d processors ...%n", numberRuns, numberProcessors);
        for (int sim = 1; sim <= numberRuns; ++sim) {
            RandomNumber rand = RandomNumberFactory.getInstance(seeds[sim % seeds.length]);
             RandomVariate timeBetweenEvents = RandomVariateFactory.getInstance("Exponential", rand, 1.7 );
            Task task = new Task(numberEvents, timeBetweenEvents, stopTime);
            executorService.execute(task);
//            System.out.println(longSim);
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(3L, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(LongSim.class.getName()).log(Level.SEVERE, null, ex);
        }
        executorService.shutdown();
        LocalDateTime end = LocalDateTime.now();
        long millis = ChronoUnit.MILLIS.between(start, end);

        System.out.printf(" Ended at %s, %,3f secs all runs%n", end, millis * 0.001);
    }

    private static class Task implements Runnable {

        private final LongSim longSim;

        public Task(int numberEvents, RandomVariate timeBetweenEvents, double stopTime) {
            int id = Schedule.addNewEventList();
            longSim = new LongSim(numberEvents, timeBetweenEvents);
            longSim.setEventList(Schedule.getEventList(id));
            longSim.getEventList().stopAtTime(stopTime);
//            Schedule.getEventList(id).setVerbose(true);
        }

        @Override
        public void run() {
//            System.out.printf("Starting  with id %d%n", longSim.getEventListID());

            LocalDateTime startRun = LocalDateTime.now();

            longSim.getEventList().reset();
            longSim.getEventList().startSimulation();
            LocalDateTime endRun = LocalDateTime.now();

            long millis = ChronoUnit.MILLIS.between(startRun, endRun);
            System.out.printf("\t%,3f secs id %d%n", millis * 0.001, longSim.getEventListID());

        }

    }

}
