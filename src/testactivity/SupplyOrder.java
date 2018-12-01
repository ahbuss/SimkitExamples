package testactivity;

import simkit.util.EnumBase;

/**
 * A stripped down version of a SupplyOrder for testing sake
 * @author ahbuss
 */
public class SupplyOrder {

    private EnumBase consumable;
    
    private int quantity;
    
    public SupplyOrder() {
        
    }

    /**
     * @return the consumable
     */
    public EnumBase getConsumable() {
        return consumable;
    }

    /**
     * @param consumable the consumable to set
     */
    public void setConsumable(EnumBase consumable) {
        this.consumable = consumable;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "SupplyOrder: " + quantity + " of " + consumable;
    }
    
}
