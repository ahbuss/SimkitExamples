package finite;

import simkit.Entity;

/**
 *
 * @author ahbuss
 */
public class Customer extends Entity {

    private double serviceTime;
    
    public Customer(double serviceTime) {
        super("Customer");
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
    public final void setServiceTime(double serviceTime) {
        if (serviceTime < 0.0) {
            throw new IllegalArgumentException("serviceTime must be ≥ 0.0: " + serviceTime);
        }
        this.serviceTime = serviceTime;
    }
    
    public String toString() {
        return String.format("%s %,.4f", super.toString(), getServiceTime());
    }
    
}
