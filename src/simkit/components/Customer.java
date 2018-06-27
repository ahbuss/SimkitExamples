package simkit.components;

import simkit.Entity;

/**
 *
 * @author ahbuss
 */
public class Customer extends Entity {

    private double serviceTime;

    public Customer() {
        super("Customer");
    }
    
    public Customer(double serviceTime) {
        this();
        this.setServiceTime(serviceTime);
    }
    
    /**
     * @return the serviceTime
     */
    public double getServiceTime() {
        return serviceTime;
    }

    /**
     * @param serviceTime the serviceTime to set
     */
    public void setServiceTime(double serviceTime) {
        if (serviceTime <= 0.0) {
            throw new IllegalArgumentException(
                "serviceTime must be > 0.0: " + serviceTime);
        }
        this.serviceTime = serviceTime;
    }
    
    @Override
    public String toString() {
        return String.format("%s %,.3f", super.toString(), getServiceTime());
    }
    
}
