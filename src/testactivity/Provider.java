package testactivity;

import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class Provider extends SimEntityBase {

    public void doReceiveRequest(SupplyOrder supplyOrder) {
        firePropertyChange("supplyOrder", supplyOrder);
    }
}
