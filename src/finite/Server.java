package finite;

import static java.lang.Double.NaN;
import java.util.SortedSet;
import java.util.TreeSet;
import static simkit.Priority.HIGH;
import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class Server extends SimEntityBase {

    private int totalNumberServers;
    
    protected int numberAvailableServers;
    
    protected SortedSet<Customer> queue;
    
    protected double delayInQueue;
    
    protected double timeInSystem;
    
    public Server(int totalNumberServers) {
        this();
        this.setTotalNumberServers(totalNumberServers);
    }
    
    public Server() {
        this.queue = new TreeSet<>();
    }
    
    public void reset() {
        super.reset();
        this.queue.clear();
        this.numberAvailableServers = getTotalNumberServers();
        this.delayInQueue = NaN;
        this.timeInSystem = NaN;
    }
    
    public void doRun() {
        firePropertyChange("queue", getQueue());
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
    }

    public void doArrival(Customer customer) {
        customer.stampTime();
        queue.add(customer);
        firePropertyChange("queue", getQueue());
        
        if (numberAvailableServers > 0) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }
    
    public void doStartService() {
        Customer customer = queue.first();
        queue.remove(customer);
        firePropertyChange("queue", getQueue());
        
        numberAvailableServers -= 1;
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
        
        delayInQueue = customer.getElapsedTime();
        firePropertyChange("delayInQueue", getDelayInQueue());
        
        waitDelay("EndService", customer.getServiceTime(), customer);        
    }
    
    public void doEndService(Customer customer) {
        numberAvailableServers += 1;
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
        
        timeInSystem = customer.getElapsedTime();
        firePropertyChange("timeInSystem", getTimeInSystem());
        
        if (!queue.isEmpty()) {
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
    public final void setTotalNumberServers(int totalNumberServers) {
        if (totalNumberServers <= 0) {
            throw new IllegalArgumentException("totalNumberServers must be > 0: " +
                    totalNumberServers);
        }
        this.totalNumberServers = totalNumberServers;
    }

    /**
     * @return the numberAvailableServers
     */
    public int getNumberAvailableServers() {
        return numberAvailableServers;
    }

    /**
     * @return the queue
     */
    public SortedSet<Customer> getQueue() {
        return new TreeSet<>(queue);
    }

    /**
     * @return the delayInQueue
     */
    public double getDelayInQueue() {
        return delayInQueue;
    }

    /**
     * @return the timeInSystem
     */
    public double getTimeInSystem() {
        return timeInSystem;
    }
    
}
