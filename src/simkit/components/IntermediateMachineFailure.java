package simkit.components;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import static simkit.Priority.HIGH;
import static simkit.Priority.HIGHER;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class IntermediateMachineFailure extends SimEntityBase {

    private int numberRepairPeople;

    private ServerWithFailure[] allServers;

    private RandomVariate timeToFailureGenerator;

    private RandomVariate repairTimeGenerator;

    private RandomVariate processingTimeGenerator;

    protected int numberJobsInQueue;

    protected int numberAvailableRepairPeople;

    protected SortedSet<ServerWithFailure> repairQueue;

    protected SortedSet<ServerWithFailure> availableServers;
    
    protected int numberBrokenParts;

    public IntermediateMachineFailure() {
        this.repairQueue = new TreeSet<>();
        this.availableServers = new TreeSet<>();
    }

    public IntermediateMachineFailure(int numberRepairPeople, ServerWithFailure[] allServers,
            RandomVariate timeToFailureGenerator, RandomVariate repairTimeGenerator,
            RandomVariate processingTimeGenerator) {
        this();
        this.setNumberRepairPeople(numberRepairPeople);
        this.setAllServers(allServers);
        this.setTimeToFailureGenerator(timeToFailureGenerator);
        this.setRepairTimeGenerator(repairTimeGenerator);
        this.setProcessingTimeGenerator(processingTimeGenerator);
    }

    @Override
    public void reset() {
        super.reset();
        this.numberAvailableRepairPeople = getNumberRepairPeople();
        this.numberJobsInQueue = 0;
        this.numberBrokenParts = 0;
        this.availableServers.clear();
        this.repairQueue.clear();
    }

    public void doRun() {
        firePropertyChange("numberAvailableRepairPeople", getNumberAvailableRepairPeople());
        firePropertyChange("numberJobsInQueue", getNumberJobsInQueue());
        firePropertyChange("repairQueue", getRepairQueue());

        waitDelay("Init", 0.0, HIGHER, 0);
    }

    public void doInit(int i) {
        allServers[i].setTimeToFailure(timeToFailureGenerator.generate());
        availableServers.add(allServers[i]);
        firePropertyChange("availableServers", getAvailableServers());
        if (i < allServers.length - 1) {
            waitDelay("Init", 0.0, HIGHER, i + 1);
        }
    }

    public void doArrival() {
        int oldNumbeJobsInQueue = getNumberJobsInQueue();
        numberJobsInQueue += 1;
        firePropertyChange("numberJobsInQueue", oldNumbeJobsInQueue, getNumberJobsInQueue());

        if (!availableServers.isEmpty()) {
            waitDelay("StartProcessing", 0.0, HIGH);
        }

    }

    public void doStartProcessing() {

        int oldNumbeJobsInQueue = getNumberJobsInQueue();
        numberJobsInQueue -= 1;
        firePropertyChange("numberJobsInQueue", oldNumbeJobsInQueue, getNumberJobsInQueue());

        SortedSet<ServerWithFailure> oldAvailableServers = getAvailableServers();
        ServerWithFailure server = availableServers.first();
        availableServers.remove(server);
        server.stampTime();
        firePropertyChange("availableServers", oldAvailableServers, getAvailableServers());

        double processingTime = processingTimeGenerator.generate();
        if (server.getTimeToFailure() > processingTime) {
            waitDelay("EndProcessing", processingTime, server);
        }

        if (server.getTimeToFailure() <= processingTime) {
            waitDelay("Failure", server.getTimeToFailure(), server);
        }
    }

    public void doEndProcessing(ServerWithFailure server) {
        server.updateTimeToFailure();

        SortedSet<ServerWithFailure> oldAvailableServers = getAvailableServers();
        availableServers.add(server);
        firePropertyChange("availableServers", oldAvailableServers, getAvailableServers());
        
        if (getNumberJobsInQueue() > 0) {
            waitDelay("StartProcessing", 0.0, HIGH);
        }
    }

    public void doFailure(ServerWithFailure server) {
        SortedSet<ServerWithFailure> oldRepairQueue = getRepairQueue();
        repairQueue.add(server);
        firePropertyChange("repairQueue", oldRepairQueue, getRepairQueue());
        
        int oldNumberBrokenParts = getNumberBrokenParts();
        numberBrokenParts += 1;
        firePropertyChange("numberBrokenParts", oldNumberBrokenParts, getNumberBrokenParts());
        
        if (getNumberAvailableRepairPeople() > 0) {
            waitDelay("StartRepair", 0.0, HIGH);
        }
    }
    
    public void doStartRepair() {
        SortedSet<ServerWithFailure> oldRepairQueue = getRepairQueue();
        ServerWithFailure server = repairQueue.first();
        repairQueue.remove(server);
        firePropertyChange("repairQueue", oldRepairQueue, getRepairQueue());
        
        int oldNumberAvailableRepairPeople = getNumberAvailableRepairPeople();
        numberAvailableRepairPeople -= 1;
        firePropertyChange("numberAvailableRepairPeople", oldNumberAvailableRepairPeople, getNumberAvailableRepairPeople());
        
        waitDelay("EndRepair", getRepairTimeGenerator(), server);
    }
    
    public void doEndRepair(ServerWithFailure server) {
        int oldNumberAvailableRepairPeople = getNumberAvailableRepairPeople();
        numberAvailableRepairPeople += 1;
        firePropertyChange("numberAvailableRepairPeople", oldNumberAvailableRepairPeople, getNumberAvailableRepairPeople());
        
        server.setTimeToFailure(timeToFailureGenerator.generate());
        SortedSet<ServerWithFailure> oldAvailableServers = getAvailableServers();
        availableServers.add(server);
        firePropertyChange("availableServers", oldAvailableServers, getAvailableServers());
        
        if (getNumberJobsInQueue() > 0) {
            waitDelay("StartProcessing", 0.0, HIGH);
        }
    }
    
    /**
     * @return the numberRepairPeople
     */
    public int getNumberRepairPeople() {
        return numberRepairPeople;
    }

    /**
     * @param numberRepairPeople the numberRepairPeople to set
     */
    public void setNumberRepairPeople(int numberRepairPeople) {
        this.numberRepairPeople = numberRepairPeople;
    }

    /**
     * @return the allServers
     */
    public ServerWithFailure[] getAllServers() {
        return allServers.clone();
    }

    /**
     * @param allServers the allServers to set
     * @throws IllegalArgumentException if allServers.length == 0
     */
    public void setAllServers(ServerWithFailure[] allServers) {
        if (allServers.length == 0) {
            throw new IllegalArgumentException("IntermediateMachineRepair "
                    + "component requires at least 1 ServerWithFailure instance");
        }
        this.allServers = allServers.clone();
    }

    /**
     * @return the timeToFailureGenerator
     */
    public RandomVariate getTimeToFailureGenerator() {
        return timeToFailureGenerator;
    }

    /**
     * @param timeToFailureGenerator the timeToFailureGenerator to set
     */
    public void setTimeToFailureGenerator(RandomVariate timeToFailureGenerator) {
        this.timeToFailureGenerator = timeToFailureGenerator;
    }

    /**
     * @return the repairTimeGenerator
     */
    public RandomVariate getRepairTimeGenerator() {
        return repairTimeGenerator;
    }

    /**
     * @param repairTimeGenerator the repairTimeGenerator to set
     */
    public void setRepairTimeGenerator(RandomVariate repairTimeGenerator) {
        this.repairTimeGenerator = repairTimeGenerator;
    }

    /**
     * @return the processingTimeGenerator
     */
    public RandomVariate getProcessingTimeGenerator() {
        return processingTimeGenerator;
    }

    /**
     * @param processingTimeGenerator the processingTimeGenerator to set
     */
    public void setProcessingTimeGenerator(RandomVariate processingTimeGenerator) {
        this.processingTimeGenerator = processingTimeGenerator;
    }

    /**
     * @return the numberJobsInQueue
     */
    public int getNumberJobsInQueue() {
        return numberJobsInQueue;
    }

    /**
     * @return the numberAvailableRepairPeople
     */
    public int getNumberAvailableRepairPeople() {
        return numberAvailableRepairPeople;
    }

    /**
     * @return the repairQueue
     */
    public SortedSet<ServerWithFailure> getRepairQueue() {
        return new TreeSet<>(repairQueue);
    }

    /**
     * @return the availableServers
     */
    public SortedSet<ServerWithFailure> getAvailableServers() {
        return new TreeSet<>(availableServers);
    }

    /**
     * @return the numberBrokenParts
     */
    public int getNumberBrokenParts() {
        return numberBrokenParts;
    }

}
