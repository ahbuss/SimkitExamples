package simkit.components;

import static java.lang.Double.NaN;
import java.util.SortedSet;
import java.util.TreeSet;
import simkit.Priority;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 * @author ahbuss
 */
public class ServerWithReneges extends SimEntityBase {

    private int totalNumberServers;
    private RandomVariate serviceTimeGenerator;

    protected int numberAvailableServers;
    protected SortedSet<RenegingCustomer> queue;
    protected int numberReneges;

    protected double delayInQueueServed;
    protected double delayInQueueReneged;
    protected double timeInSystem;

    /*
     * Instantiate a ServerWithReneges with the given number of
     * servers and service time generator
     * @param servers Number of servers
     * @param service Generates service times
     */
    public ServerWithReneges(int totalNumberServers, RandomVariate serviceTimeGenerator) {
        setTotalNumberServers(totalNumberServers);
        setServiceTimeGenerator(serviceTimeGenerator);
        queue = new TreeSet<>();
    }

    /**
     * Initialize numberAvailableServers to total number of servers. Empty
     * queue. Initialize counters to 0.
     */
    @Override
    public void reset() {
        super.reset();
        numberAvailableServers = getTotalNumberServers();
        queue.clear();
        numberReneges = 0;
        delayInQueueServed = NaN;
        delayInQueueReneged = NaN;
        timeInSystem = NaN;
    }

    /**
     * Only fire property change for states
     */
    public void doRun() {
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
        firePropertyChange("queue", getQueue());
        firePropertyChange("numberReneges", getNumberReneges());

        firePropertyChange("delayInQueueServed", getDelayInQueueServed());
        firePropertyChange("delayInQueueReneged", getDelayInQueueReneged());
        firePropertyChange("timeInSystem", getTimeInSystem());
    }

    /**
     * <p>
     * Add customer to queue.
     * <p>
     * Schedule Renege event with delay from arriving Entity.
     * <p>
     * If there is an available server, schedule StartService
     *
     * @param customer Arriving RenegingCustomer
     */
    public void doArrival(RenegingCustomer customer) {
        customer.stampTime();

        SortedSet<RenegingCustomer> oldQueue = getQueue();
        queue.add(customer);
        firePropertyChange("queue", oldQueue, getQueue());

        waitDelay("Renege", customer.getRenegeTime(), customer);
        if (getNumberAvailableServers() > 0) {
            waitDelay("StartService", 0.0, Priority.HIGH);
        }
    }

    /**
     * <p>
     * Remove customer from queue.
     * <p>
     * Decrement number of available servers.
     * <p>
     * Compute delay in queue as delayInQueueServed.
     * <p>
     * Cancel Renege event for that customer.
     * <p>
     * Schedule EndService event.
     */
    public void doStartService() {
        SortedSet<RenegingCustomer> oldQueue = getQueue();
        RenegingCustomer customer = queue.first();
        queue.remove(customer);
        firePropertyChange("queue", oldQueue, getQueue());

        int oldNumberAvailableServers = getNumberAvailableServers();
        numberAvailableServers = numberAvailableServers - 1;
        firePropertyChange("numberAvailableServers", oldNumberAvailableServers, getNumberAvailableServers());

        delayInQueueServed = customer.getElapsedTime();
        firePropertyChange("delayInQueueServed", getDelayInQueueServed());

        interrupt("Renege", customer);

        waitDelay("EndService", getServiceTimeGenerator().generate(), customer);
    }

    /**
     * <p>
     * Increment number of available servers.
     * <p>
     * Compute total time in system as timeInSystem.
     * <p>
     * If customer(s) in queue, schedule StartService with HIGH priority. This
     * prevents the (unlikely) simultaneous Arrival event from occurring first.
     *
     * @param customer Entity ending service
     */
    public void doEndService(RenegingCustomer customer) {
        int oldNumberAvailableServers = getNumberAvailableServers();
        numberAvailableServers = numberAvailableServers + 1;
        firePropertyChange("numberAvailableServers", oldNumberAvailableServers, getNumberAvailableServers());

        timeInSystem = customer.getElapsedTime();
        firePropertyChange("timeInSystem", getTimeInSystem());

        if (!queue.isEmpty()) {
            waitDelay("StartService", 0.0, Priority.HIGH);
        }
    }

    /**
     * <p>
     * Remove reneging Entity from queue.
     * <p>
     * Increment total number of reneges.
     *
     * @param customer RenegingCustomer who leaves queue ("reneges")
     */
    public void doRenege(RenegingCustomer customer) {
        SortedSet<RenegingCustomer> oldQueue = getQueue();
        queue.remove(customer);
        firePropertyChange("queue", oldQueue, getQueue());

        int oldNumberReneges = getNumberReneges();
        numberReneges = getNumberReneges() + 1;
        firePropertyChange("numberReneges", oldNumberReneges, getNumberReneges());

        delayInQueueReneged = customer.getElapsedTime();
        firePropertyChange("delayInQueueReneged", getDelayInQueueReneged());
    }

    /**
     *
     * @return totalNumberServers
     */
    public int getTotalNumberServers() {
        return totalNumberServers;
    }

    /**
     *
     * @param totalNumberServers Given totalNumberServers
     * @throws IllegalArgumentException if totalNumberServers \u2264 0
     */
    public void setTotalNumberServers(int totalNumberServers) {
        if (totalNumberServers <= 0) {
            throw new IllegalArgumentException(
                    "totalNumberServers must be > 0:" + totalNumberServers);
        }
        this.totalNumberServers = totalNumberServers;
    }

    /**
     *
     * @return the serviceTimeGenerator
     */
    public RandomVariate getServiceTimeGenerator() {
        return serviceTimeGenerator;
    }

    /**
     *
     * @param serviceTimeGenerator the serviceTimeGenerator
     */
    public void setServiceTimeGenerator(RandomVariate serviceTimeGenerator) {
        this.serviceTimeGenerator = serviceTimeGenerator;
    }

    /**
     *
     * @return numberAvailableServers
     */
    public int getNumberAvailableServers() {
        return numberAvailableServers;
    }

    /**
     * Returns a copy
     *
     * @return current queue
     */
    public SortedSet<RenegingCustomer> getQueue() {
        return new TreeSet<>(queue);
    }

    /**
     *
     * @return numberReneges
     */
    public int getNumberReneges() {
        return numberReneges;
    }

    /**
     *
     * @return delayInQueueServed
     */
    public double getDelayInQueueServed() {
        return delayInQueueServed;
    }

    /**
     *
     * @return timeInSystem
     */
    public double getTimeInSystem() {
        return timeInSystem;
    }

    /**
     *
     * @return delayInQueueReneged
     */
    public double getDelayInQueueReneged() {
        return delayInQueueReneged;
    }
}
