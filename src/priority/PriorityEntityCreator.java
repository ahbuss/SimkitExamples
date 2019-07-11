package priority;

import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class PriorityEntityCreator extends SimEntityBase {

    private RandomVariate interarrivalTimeGenerator;
    
    private int myPriority;
    
    public PriorityEntityCreator() {
    }
    
    public PriorityEntityCreator(RandomVariate interarrivalTimeGenerator, int priority) {
        this.setInterarrivalTimeGenerator(interarrivalTimeGenerator);
        this.setMyPriority(priority);
    }
    
    public void doRun() {
        waitDelay("Arrival", interarrivalTimeGenerator);
    }
    
    public void doArrival() {
        waitDelay("Arrival", interarrivalTimeGenerator);
        waitDelay("Enter", 0.0, new PriorityEntity(myPriority));
    }

    /**
     * @return the interarrivalTimeGenerator
     */
    public RandomVariate getInterarrivalTimeGenerator() {
        return interarrivalTimeGenerator;
    }

    /**
     * @param interarrivalTimeGenerator the interarrivalTimeGenerator to set
     */
    public void setInterarrivalTimeGenerator(RandomVariate interarrivalTimeGenerator) {
        this.interarrivalTimeGenerator = interarrivalTimeGenerator;
    }

    /**
     * @return the myPriority
     */
    public int getMyPriority() {
        return myPriority;
    }

    /**
     * @param priority the myPriority to set
     */
    public void setMyPriority(int priority) {
        this.myPriority = priority;
    }
    
}
