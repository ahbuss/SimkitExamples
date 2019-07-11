package simkit.stat;

import java.util.logging.Logger;
/**
 *
 * @author ahbuss
 */
public class MOE {

    private static final Logger LOGGER = Logger.getLogger(MOE.class.getName());

    private final String propertyName;
    
    private final String dbName;
    
    private boolean mean;
    
    private boolean min;
    
    private boolean max;
    
    private boolean variance;
    
    private boolean stdDeviation;
    
    public MOE(String propertyName, String dbName) {
        this.propertyName = propertyName;
        this.dbName = dbName;
        this.setMean(true);
        this.setStdDeviation(true);
    }

    public MOE(String propertyName) {
        this(propertyName, propertyName);
    }
    
    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @return the mean
     */
    public boolean isMean() {
        return mean;
    }

    /**
     * @param mean the mean to set
     */
    public void setMean(boolean mean) {
        this.mean = mean;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @return the min
     */
    public boolean isMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(boolean min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public boolean isMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(boolean max) {
        this.max = max;
    }

    /**
     * @return the variance
     */
    public boolean isVariance() {
        return variance;
    }

    /**
     * @param variance the variance to set
     */
    public void setVariance(boolean variance) {
        this.variance = variance;
    }

    /**
     * @return the stdDeviation
     */
    public boolean isStdDeviation() {
        return stdDeviation;
    }

    /**
     * @param stdDeviation the stdDeviation to set
     */
    public void setStdDeviation(boolean stdDeviation) {
        this.stdDeviation = stdDeviation;
    }
    
}
