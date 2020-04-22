package thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import simkit.BasicSimEntity;
import simkit.SimEntityBase;
import simkit.SimEvent;

/**
 *
 * @author ahbuss
 */
public class Executor extends BasicSimEntity  {
    
    public static final BlockingQueue<Executor> EXECUTORS = new LinkedBlockingQueue<>();

    private final SimEntityBase simulation;

    private final int numberReplications;
    
    private boolean verbose;
    
    public Executor(SimEntityBase simulation, int numberReplications) {
        this.simulation = simulation;
        this.numberReplications = numberReplications;
        this.setEventListID(simulation.getEventListID());
        this.setVerbose(false);
        EXECUTORS.add(this);
    }

    public void execute(int simID) {
        firePropertyChange("startSimulation", simID);
        for (int replication = 1; replication <= numberReplications; ++replication) {
            getEventList().setVerbose(verbose);
            getEventList().reset();
            firePropertyChange("startReplication", replication);
            getEventList().startSimulation();
            firePropertyChange("endReplication", replication);
        }
        firePropertyChange("endSimulation", null);
    }

    @Override
    public void handleSimEvent(SimEvent event) {
    }

    @Override
    public void processSimEvent(SimEvent event) {
    }

    /**
     * @return the verbose
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * @param verbose the verbose to set
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

}
