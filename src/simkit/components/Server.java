package simkit.components;

import simkit.Entity;

/**
 *
 * @author ahbuss
 */
public class Server extends Entity {
    
    private double efficiency;
    
    public Server() {
        super("Server");
        this.setEfficiency(1.0);
    }
    
    public Server(double efficiency) {
        this();
        this.setEfficiency(efficiency);
    }

    /**
     * @return the efficiency
     */
    public double getEfficiency() {
        return efficiency;
    }

    /**
     * @param efficiency the efficiency to set
     */
    public void setEfficiency(double efficiency) {
        if (efficiency <= 0.0) {
            throw new IllegalArgumentException(
                "efficiency must be > 0.0: " + efficiency);
        }
        this.efficiency = efficiency;
    }
    
    @Override
    public String toString() {
        return String.format("%s.%d %.3f", getName(), getID(), getEfficiency());
    }

}
