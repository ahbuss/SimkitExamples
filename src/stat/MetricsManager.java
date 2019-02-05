package stat;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.stat.SampleStatistics;
import simkit.stat.SimpleStatsTally;
import simkit.stat.SimpleStatsTimeVarying;

/**
 *
 * @author Arnold Buss
 */
public class MetricsManager implements PropertyChangeListener {
    
    private static final Logger LOGGER = Logger.getLogger(MetricsManager.class.getName());
    
    private final Map<String, SampleStatistics> innerStats;
    
    private final Map<String, SimpleStatsTally> outerStats;
    
    private Statement statement;
    
    private String simulationID;
    
    private double pmss;
    
    public MetricsManager(String simulationID, Statement statement) {
        this.setSimulationID(simulationID);
        this.setStatement(statement);
        this.innerStats = new HashMap<>();
        this.outerStats = new HashMap<>();
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String property = pce.getPropertyName();
        if (innerStats.containsKey(property)) {
            SampleStatistics stat = innerStats.get(property);
            stat.newObservation((Number) pce.getNewValue());
        } else {
            switch (property) {
                case "pmss":
                    double pmss = ((Number)pce.getNewValue()).doubleValue();
                    setPmss(pmss);
                    break;
                case "replication":
                    clearInnerStats();
                    break;
                case "designPoint":
                    writeOutput();
                    clearInnerStats();
                    clearOuterStats();
                    break;
                default:
                    break;
            }
        }
    }
    
    protected void clearInnerStats() {
        for (SampleStatistics stats : innerStats.values()) {
            stats.reset();
        }
    }
    
    protected void clearOuterStats() {
        for (SampleStatistics stats : outerStats.values()) {
            stats.reset();
        }
    }
    
    protected void writeOutput() {
        
    }
    
    /**
     * Adds the given property (state) as a tally variable
     * @param property Given property (state)
     */
    public void addTallyStat(String property) {
        if (innerStats.containsKey(property)) {
            LOGGER.log(Level.WARNING, "Already contains stats for {0} - will be overwritten", property);
        }
        this.innerStats.put(property, new SimpleStatsTally(property));
        this.outerStats.put(property, new SimpleStatsTally(property));
    }
    
    /**
     * Adds the given property (state) as a time-varying variable
     * @param property Given property (state)
     */
    public void addTimeVaryingStat(String property) {
        if (innerStats.containsKey(property)) {
            LOGGER.log(Level.WARNING, "Already contains stats for {0} - will be overwritten", property);
        }
        this.innerStats.put(property, new SimpleStatsTimeVarying(property));
        this.outerStats.put(property, new SimpleStatsTally(property));
    }

    /**
     * @return the innerStats
     */
    public Map<String, SampleStatistics> getInnerStats() {
        return innerStats;
    }

    /**
     * @return the outerStats
     */
    public Map<String, SimpleStatsTally> getOuterStats() {
        return outerStats;
    }

    /**
     * @return the statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * @param statement the statement to set
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * @return the simulationID
     */
    public String getSimulationID() {
        return simulationID;
    }

    /**
     * @param simulationID the simulationID to set
     */
    public void setSimulationID(String simulationID) {
        this.simulationID = simulationID;
    }

    /**
     * @return the pmss
     */
    public double getPmss() {
        return pmss;
    }

    /**
     * @param pmss the pmss to set
     */
    public void setPmss(double pmss) {
        this.pmss = pmss;
    }
    
}
