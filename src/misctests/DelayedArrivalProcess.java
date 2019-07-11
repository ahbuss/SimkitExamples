package misctests;

import simkit.components.ArrivalProcess;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class DelayedArrivalProcess extends ArrivalProcess {

    private long startingDate;
    
    public DelayedArrivalProcess(RandomVariate interarrivalTimeGenerator, long startingDate) {
        super(interarrivalTimeGenerator);
        this.setStartingDate(startingDate);
    }

    @Override
    public void doRun() {
        waitDelay("Init", getStartingDate());
    }
    
    public void doInit() {
        super.doRun();
    }
    
    /**
     * @return the startingDate
     */
    public long getStartingDate() {
        return startingDate;
    }

    /**
     * @param startingDate the startingDate to set
     */
    public void setStartingDate(long startingDate) {
        this.startingDate = startingDate;
    }

}
