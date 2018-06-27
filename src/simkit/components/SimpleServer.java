package simkit.components;

import static simkit.Priority.HIGH;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 * Simplest component of a multiple server queue. States are merely counters for
 * # in queue and # available servers.
 * @author ahbuss
 */
public class SimpleServer extends SimEntityBase {

    private int totalNumberServers;

    private RandomVariate serviceTimeGenerator;

    protected int numberInQueue;

    protected int numberAvailableServers;

    protected int numberServed;

    public SimpleServer() {
    }

    /**
     *
     * @param totalNumberServers Given total number of servers
     * @param serviceTimeGenerator Given service time generator
     */
    public SimpleServer(int totalNumberServers, RandomVariate serviceTimeGenerator) {
        this();
        this.setTotalNumberServers(totalNumberServers);
        this.setServiceTimeGenerator(serviceTimeGenerator);
    }

    /**
     * Initialize numberInQueue to 0, numberServed to 0, numberAvailableServers
     * to totalNumberServers, and numberInQueue to 0.
     */
    @Override
    public void reset() {
        super.reset();
        this.numberInQueue = 0;
        this.numberAvailableServers = getTotalNumberServers();
        this.numberServed = 0;
    }

    /**
     * Just firing PropertyChangeEvents for state variables
     */
    public void doRun() {
        firePropertyChange("numberInQueue", getNumberInQueue());
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
        firePropertyChange("numberServed", getNumberServed());
    }

    /**
     * Increment numberInQueue; <br>if available server, schedule StartService with
     * 0,0 delay and HIGH priority
     */
    public void doArrival() {
        int oldNumberInQueue = getNumberInQueue();
        numberInQueue += 1;
        firePropertyChange("numberInQueue", oldNumberInQueue, getNumberInQueue());

        if (numberAvailableServers > 0) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }

    /**
     * Decrement numberInQueue and numberAvailableServers;<br> Schedule
     * EndService with delay generated from serviceTimeGenerator
     * 
     */
    public void doStartService() {
        int oldNumberInQueue = getNumberInQueue();
        numberInQueue -= 1;
        firePropertyChange("numberInQueue", oldNumberInQueue, getNumberInQueue());

        int oldNumberAvailableServers = getNumberAvailableServers();
        this.numberAvailableServers -= 1;
        firePropertyChange("numberAvailableServers", oldNumberAvailableServers, getNumberAvailableServers());

        waitDelay("EndService", getServiceTimeGenerator());
    }

    /**
     * Increment numberAvailableServers and numberServed;<br>
     * If numberInQueue &gt; 0, schedule StartService with delay 0.0 and
     * HIGH priority.
     */
    public void doEndService() {
        int oldNumberAvailableServers = getNumberAvailableServers();
        this.numberAvailableServers += 1;
        firePropertyChange("numberAvailableServers", oldNumberAvailableServers, getNumberAvailableServers());

        int oldNumberServed = getNumberServed();
        this.numberServed += 1;
        firePropertyChange("numberServed", oldNumberServed, getNumberServed());

        if (getNumberInQueue() > 0) {
            waitDelay("StartService", 0.0, HIGH);
        }

    }

    /**
     * @return the totalNumberServers
     */
    public int getTotalNumberServers() {
        return totalNumberServers;
    }

    /**
     * @param totalNumberServers the totalNumberServers to set
     */
    public void setTotalNumberServers(int totalNumberServers) {
        this.totalNumberServers = totalNumberServers;
    }

    /**
     * @return the serviceTimeGenerator
     */
    public RandomVariate getServiceTimeGenerator() {
        return serviceTimeGenerator;
    }

    /**
     * @param serviceTimeGenerator the serviceTimeGenerator to set
     */
    public void setServiceTimeGenerator(RandomVariate serviceTimeGenerator) {
        this.serviceTimeGenerator = serviceTimeGenerator;
    }

    /**
     * @return the numberInQueue
     */
    public int getNumberInQueue() {
        return numberInQueue;
    }

    /**
     * @return the numberAvailableServers
     */
    public int getNumberAvailableServers() {
        return numberAvailableServers;
    }

    /**
     * @return the numberServed
     */
    public int getNumberServed() {
        return numberServed;
    }

}
