package testactivity;

import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class ActivityNetwork extends SimEntityBase {

    /**
     * Start this activity network
     * @param obj Given argument 
     */
    public void doStart(Object obj) {
        waitDelay("TransShipment", 3.4, obj);
    }
    
    public void doTransShipment(Object obj) {
        waitDelay("End", 4.5, obj);
    }
    
    public void doEnd(Object obj) {
        firePropertyChange("end", obj);
    }
    
}
