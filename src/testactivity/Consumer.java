package testactivity;

import simkit.SimEntityBase;
import simkit.util.EnumBase;

/**
 * This will schedule two RequestReSupply events for different quantities of different
 * consumables. 
 * 
 * @author ahbuss
 */
public class Consumer extends SimEntityBase {

    /**
     * Schedule ResquestReSupply at times 2.5 for 10 items and 1.1 for 15
     */
    public void doRun() {
        SupplyOrder supplyOrder = new SupplyOrder();
        supplyOrder.setQuantity(10);
        supplyOrder.setConsumable(EnumBase.findOrCreate("STUFF", Consumable.class));
        waitDelay("RequestReSupply", 2.5, supplyOrder);

        supplyOrder = new SupplyOrder();
        supplyOrder.setQuantity(15);
        supplyOrder.setConsumable(EnumBase.findOrCreate("MORE_STUFF", Consumable.class));
        waitDelay("RequestReSupply", 1.1, supplyOrder);
    }

    /**
     * Just fires propertyChange event to show it has occurred
     * @param supplyOrder Given SupplyOrder
     */
    public void doRequestReSupply(SupplyOrder supplyOrder) {
        firePropertyChange("supplyOrder", supplyOrder);

    }

}
