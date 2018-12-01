package priority;

import java.util.SortedSet;
import java.util.TreeSet;
import static simkit.Priority.HIGH;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class PriorityServer extends SimEntityBase {

    private RandomVariate serviceTimeGenerator;
    
    private int totalNumberServers;
    
    private int numberAvailableServers;
    
    private SortedSet<PriorityEntity> queue;
    
    public PriorityServer() { }
    
    public PriorityServer(RandomVariate serviceTimeGenerator, int totalNumberServers) {
        this.setServiceTimeGenerator(serviceTimeGenerator);
        this.setTotalNumberServers(totalNumberServers);
        this.queue = new TreeSet<>();
    }
    
    @Override
    public void reset() {
        super.reset();
        this.numberAvailableServers = getTotalNumberServers();
        this.queue.clear();
    }
    
    public void doRun() {
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
        firePropertyChange("queue", getQueue());
    }

    public void doEnter(PriorityEntity entity) {
        entity.stampTime();
        SortedSet<PriorityEntity> oldQueue = getQueue();
        queue.add(entity);
        firePropertyChange("queue", oldQueue, getQueue());
        
        if (getNumberAvailableServers() > 0) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }
    
    public void doStartService() {
        int oldNumberAvailableServers = getNumberAvailableServers();
        numberAvailableServers -= 1;
        firePropertyChange("numberAvailableServers", oldNumberAvailableServers, getNumberAvailableServers());
        
        SortedSet<PriorityEntity> oldQueue = getQueue();
        PriorityEntity entity = queue.first();
        queue.remove(entity);
        firePropertyChange("queue", oldQueue, getQueue());
        
        fireIndexedPropertyChange(entity.getPriority(), "delayInQueue", entity.getElapsedTime());
        
        waitDelay("EndService", serviceTimeGenerator, entity);
    }
    
    public void doEndService(PriorityEntity entity) {
        int oldNumberAvailableServers = getNumberAvailableServers();
        numberAvailableServers += 1;
        firePropertyChange("numberAvailableServers", oldNumberAvailableServers, getNumberAvailableServers());
        
        fireIndexedPropertyChange(entity.getPriority(), "timeInSystem", entity.getElapsedTime());
        
        if (!queue.isEmpty()) {
            waitDelay("StartService", 0.0, HIGH);
        }
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
     * @return the numberAvailableServers
     */
    public int getNumberAvailableServers() {
        return numberAvailableServers;
    }

    /**
     * @return the queue
     */
    public SortedSet<PriorityEntity> getQueue() {
        return new TreeSet<>(queue);
    }
    
}
