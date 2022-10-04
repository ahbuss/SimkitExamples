package simkit.nointrospect;

import simkit.BasicSimEntity;
import simkit.SimEvent;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class ArrivalProcess extends BasicSimEntity {
    
    private RandomVariate interarrivalTimeGenerator;
    
    protected int numberArrivals;
    
    public ArrivalProcess() {        
    }
    
    public ArrivalProcess(RandomVariate interarrivalTimeGenerator) {
        this.setInterarrivalTimeGenerator(interarrivalTimeGenerator);
    }
    
    public void reset() {
        super.reset();
        this.numberArrivals = 0;
    }
    
    public void doRun() {
        firePropertyChange("numberArrivals", getNumberArrivals());
        
        waitDelay("Arrival", interarrivalTimeGenerator);
    }

    protected void arrival() {
        int oldNumberArrivals = getNumberArrivals();
        this.numberArrivals += 1;
        firePropertyChange("numberArrivals", oldNumberArrivals, getNumberArrivals());
        
        waitDelay("Arrival", interarrivalTimeGenerator);
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
    public final void setInterarrivalTimeGenerator(RandomVariate interarrivalTimeGenerator) {
        this.interarrivalTimeGenerator = interarrivalTimeGenerator;
    }

    /**
     * @return the numberArrivals
     */
    public int getNumberArrivals() {
        return numberArrivals;
    }

    @Override
    public void handleSimEvent(SimEvent event) {
        switch(event.getEventName()) {
            case "Run":
                doRun();
                break;
            case "Arrival":
                arrival();
                break;
            default:
                break;
        }
    }

    @Override
    public void processSimEvent(SimEvent event) {
    }
    
}
