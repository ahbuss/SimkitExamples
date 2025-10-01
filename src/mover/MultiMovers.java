package mover;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.BasicEventList;
import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.smd.BasicLinearMover;
import simkit.smd.Mover;
import simkit.smd.PatrolMoverManager;
import simkit.smd.WayPoint;
import simkit.stat.SimpleStatsTally;
import thread.MuliThreadQueues;

/**
 * Simple illustration of using multiple Movers in parallel simulations.<br>
 * A number of Movers will move to a certain numbers of randomly generated
 * waypoints and the number for each counted.
 *
 * @author ahbuss
 */
public class MultiMovers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        One Thread per processor
        int numberPrecessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberPrecessors);

//        These will generate the Waypoints
        RandomVariate[] coordinateGenerator = new RandomVariate[]{
            RandomVariateFactory.getInstance("Uniform", -100.0, 100.0),
            RandomVariateFactory.getInstance("Uniform", 0.0, 250.0)};

        int numberMovers = 200;
        int numberWaypoints = 30;
        double speed = 25;
        double stopTime = 100000.0;

//        Instentaite each one, populate a Task, and submit to the ExecutorService
        System.out.printf("Starting %d simulations on %d processors%n", numberMovers, numberPrecessors);
        for (int i = 0; i < numberMovers; ++i) {
            double xInit = coordinateGenerator[0].generate();
            double yInit = coordinateGenerator[1].generate();
            Point2D initialLocation = new Point2D.Double(xInit, yInit);
            BasicLinearMover mover = new BasicLinearMover("Mover-" + i, initialLocation, speed);
            List<WayPoint> path = new ArrayList<>();
            for (int j = 0; j < numberWaypoints; ++j) {
                double x = coordinateGenerator[0].generate();
                double y = coordinateGenerator[1].generate();
                WayPoint wayPoint = new WayPoint(x, y);
                path.add(wayPoint);
            }
            PatrolMoverManager moverManager = new PatrolMoverManager(mover, path, true);
            WaypointCounter counter = new WaypointCounter();
            mover.addSimEventListener(counter);

            Task task = new Task(mover, moverManager, counter, stopTime);
            executorService.execute(task);
        }

//        Badly named method - this simply notifies the ExecutorService that there
//        won't be any more submitted
        executorService.shutdown();
        try {
            executorService.awaitTermination(1L, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(MuliThreadQueues.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.printf("Avg # visited: %,.2f std dev: %,.2f%n", Task.COUNT_STATS.getMean(),
                Task.COUNT_STATS.getStandardDeviation());
    }

    /**
     * Each Task will execute a separate simulation on its own EventList
     * instance
     */
    private static class Task implements Runnable {

        /**
         * This is to prevent a race condition in Schedule when obtaining a new
         * EventList instance
         */
        private static final ReentrantLock LOCK = new ReentrantLock();

        /**
         * For overall stats on # Waypoints visited
         */
        public static final SimpleStatsTally COUNT_STATS = new SimpleStatsTally("Counts");

        /**
         * EventList for this Task
         */
        private final BasicEventList eventList;

        /**
         * Mover for this Task
         */
        private BasicLinearMover mover;

        /**
         * Mover Manager for this Task
         */
        private PatrolMoverManager moverManager;

        /**
         * This will count the number of Waypoints visited
         */
        private WaypointCounter counter;

        /**
         * Stop time for this Task's EventList
         */
        private double stopTime;

        /**
         *
         * @param mover Given Mover
         * @param moverManager given MoverManager
         * @param counter Given WaypointCounter
         * @param stopTime Given stop time for simulation
         */
        public Task(BasicLinearMover mover, PatrolMoverManager moverManager,
                WaypointCounter counter, double stopTime) {
            LOCK.lock();
            try {
                int id = Schedule.addNewEventList();
                eventList = Schedule.getEventList(id);
                this.mover = mover;
                this.mover.setEventList(eventList);
                this.moverManager = moverManager;
                this.moverManager.setEventList(eventList);
                this.counter = counter;
                this.counter.setEventList(eventList);
                this.stopTime = stopTime;
            } finally {
                LOCK.unlock();
            }
        }

        @Override
        public void run() {
            eventList.setVerbose(false);
            eventList.stopAtTime(stopTime);
            eventList.reset();
            eventList.startSimulation();

            COUNT_STATS.newObservation(counter.getCount());

            System.out.printf("Mover %s visited %,d waypoints%n", mover.getName(),
                    counter.getCount());
        }

    }

    /**
     * Keeps count of number of EndMove events
     */
    public static class WaypointCounter extends SimEntityBase {

        /**
         * Number of Waypoints visited
         */
        protected int count;

        /**
         * Initialize count to 0
         */
        @Override
        public void reset() {
            super.reset();
            count = 0;
        }

        /**
         * "Heard" from Mover<br>
         * Increment count
         *
         * @param mover Given Mover
         */
        public void doEndMove(Mover mover) {
            int oldCount = getCount();
            count += 1;
            firePropertyChange("count", oldCount, getCount());

        }

        /**
         * @return the count
         */
        public int getCount() {
            return count;
        }
    }

}
