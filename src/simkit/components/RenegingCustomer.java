package simkit.components;

import simkit.Entity;

/**
 * Entity with a Renege Time
 *
 * @author ahbuss
 */
public class RenegingCustomer extends Entity {

    protected double renegeTime;

    /**
     * Instantiate RenegingCustomer with given renege time.
     *
     * @param renegeTime amount of time customer is willing to wait in a queue.
     * Must be &gt; 0.0
     */
    public RenegingCustomer(double renegeTime) {
        super("Customer");
        this.setRenegeTime(renegeTime);
    }

    /**
     * @return time this customer is willing to wait in queue
     */
    public double getRenegeTime() {
        return renegeTime;
    }

    /**
     * @return String description, including creation time (fromEntity) and
     * renegeTime
     */
    @Override
    public String toString() {
        return super.toString()
                + String.format(" %.3f", getRenegeTime());
    }

    /**
     * 
     * @param renegeTime Given renegeTime
     * @throws IllegalArgumentException if renegeTime \u2264 0
     */
    public void setRenegeTime(double renegeTime) {
        if (renegeTime <= 0.0) {
            throw new IllegalArgumentException("renegeTime must be > 0.0: " + renegeTime);
        }
        this.renegeTime = renegeTime;
    }
}
