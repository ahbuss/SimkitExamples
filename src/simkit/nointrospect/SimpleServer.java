package simkit.nointrospect;

import simkit.BasicSimEntity;
import static simkit.Priority.HIGH;
import simkit.SimEvent;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class SimpleServer extends BasicSimEntity {

    private int totalNumberServers;
    
    private RandomVariate serviceTimeGenerator;
    
    protected int numberInQueue;
    
    protected int numberAvailableServers;
    
    protected int numberServed;
    
    public SimpleServer() { }
    
    public SimpleServer(int totalNumberServers, RandomVariate serviceTimeGenerator) {
        this.setTotalNumberServers(totalNumberServers);
        this.setServiceTimeGenerator(serviceTimeGenerator);
    }

    public void reset() {
        super.reset();
        this.numberInQueue = 0;
        this.numberAvailableServers = getTotalNumberServers();
        this.numberServed = 0;
    }
    
    public void doRun() {
        firePropertyChange("numberInQueue", getNumberInQueue());
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
        firePropertyChange("numberServed", getNumberServed());
    }
    
    public void arrival() {
        int oldNumberInQueue = getNumberInQueue();
        numberInQueue += 1;
        firePropertyChange("numberInQueue", oldNumberInQueue, getNumberInQueue());
        
        if (numberAvailableServers > 0) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }
    
    public void startService() {
        int oldNumberInQueue = getNumberInQueue();
        numberInQueue -= 1;
        firePropertyChange("numberInQueue", oldNumberInQueue, getNumberInQueue());
        
        int oldNumberAvailableServers = getNumberAvailableServers();
        numberAvailableServers -= 1;
        firePropertyChange("numberAvailableServers", oldNumberAvailableServers, getNumberAvailableServers());
        
        waitDelay("EndService", serviceTimeGenerator);
    }
    
    public void endService() {
        int oldNumberAvailableServers = getNumberAvailableServers();
        numberAvailableServers += 1;
        firePropertyChange("numberAvailableServers", oldNumberAvailableServers, getNumberAvailableServers());

        int oldNumberServed = getNumberServed();
        numberServed += 1;
        firePropertyChange("numberServed", oldNumberServed, getNumberServed());
        
        if (numberInQueue > 0) {
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
     * @return the serviceTimeGenerator
     */
    public RandomVariate getServiceTimeGenerator() {
        return serviceTimeGenerator;
    }

    /**
     * @param serviceTimeGenerator the serviceTimeGenerator to set
     */
    public final void setServiceTimeGenerator(RandomVariate serviceTimeGenerator) {
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
    
    @Override
    public void handleSimEvent(SimEvent event) {
        switch(event.getEventName()) {
            case "Run":
                doRun();
                break;
            case "StartService":
                startService();
                break;
            case "EndService":
                endService();
                break;
            default:
                break;
        }
    }

    @Override
    public void processSimEvent(SimEvent event) {
        switch(event.getEventName()) {
            case "Arrival":
            case "EndService":
                arrival();
                break;
            default:
                break;
        }
    }
    
}
