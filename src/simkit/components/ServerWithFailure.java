package simkit.components;

import simkit.Entity;

/**
 *
 * @author ahbuss
 */
public class ServerWithFailure extends Entity {

    private double timeToFailure;
    
    public ServerWithFailure() {
        super("ServerWithFailures");
    }
    
    public ServerWithFailure(double timeToFailure) {
        this();
        this.setTimeToFailure(timeToFailure);
    }

    public void updateTimeToFailure() {
        this.setTimeToFailure(this.getTimeToFailure() - this.getElapsedTime());
    }
    
    /**
     * @return the timeToFailure
     */
    public double getTimeToFailure() {
        return timeToFailure;
    }

    /**
     * @param timeToFailure the timeToFailure to set
     */
    public void setTimeToFailure(double timeToFailure) {
        if (timeToFailure <= 0.0) {
            throw new IllegalArgumentException("timeToFailure must be \u2265 0.0: " + timeToFailure);
        }
        this.timeToFailure = timeToFailure;
    }
    
    @Override
    public String toString() {
        return String.format("%s.%d [%.3f] %.3f", getName(), getID(), getTimeStamp(), getTimeToFailure());
    }
    
}
