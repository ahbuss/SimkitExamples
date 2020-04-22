package thread;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import simkit.SimEntityBase;
import simkit.SimEvent;
import simkit.stat.CollectionSizeTimeVaryingStats;
import simkit.stat.SampleStatistics;
import simkit.stat.SimpleStatsTally;
import simkit.stat.SimpleStatsTimeVarying;

/**
 *
 * @author ahbuss
 */
public class MetricsManager implements PropertyChangeListener {

    private static final Logger LOGGER = Logger.getLogger(MetricsManager.class.getName());

    private final Map<String, SampleStatistics> innerStats;

    private final Map<String, SimpleStatsTally> outerStats;

    private SimEntityBase simulation;
    
    private int simID;
    
    public MetricsManager(SimEntityBase simulation) {
        this.innerStats = new HashMap<>();
        this.outerStats = new HashMap<>();
        this.simulation = simulation;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "startSimulation": // clear all stats
                clearOuterStats();
                this.simID = (Integer) evt.getNewValue();
                break;
            case "startReplication": // clear inner stats
                clearInnerStats();
                break;
            case "endReplication": // update outer stats
                updateOuterStat();
                clearInnerStats();
                break;
            case "endSimulation": // update output
                for (String metricName : outerStats.keySet()) {
                    LOGGER.info(String.format("(%d) [%d] %s: %,.4f (%,d replications) ", 
                            simulation.getEventListID(), this.simID,
                            metricName, outerStats.get(metricName).getMean(), 
                            outerStats.get(metricName).getCount()));
                }
                break;
            default:
                String property = evt.getPropertyName();
                if (innerStats.containsKey(property)) {
                    SampleStatistics stat = innerStats.get(property);
                    stat.newObservation((Number)evt.getNewValue());
                }
                break;
        }
    }

    public void updateOuterStat() {
        for (String metricName : outerStats.keySet()) {
            if (innerStats.containsKey(metricName)) {
                SampleStatistics innerStats = this.innerStats.get(metricName);
                SimpleStatsTally outerStats = this.outerStats.get(metricName);
                outerStats.newObservation(innerStats.getMean());
            }
        }
    }

    private void clearInnerStats() {
        for (SampleStatistics stat : innerStats.values()) {
            stat.reset();
        }
    }

    private void clearOuterStats() {
        for (SimpleStatsTally stat : outerStats.values()) {
            stat.reset();
        }
    }

    public void addMetric(String metricName, MetricType type) {
        switch (type) {
            case TIME_VARYING:
                if (!innerStats.containsKey(metricName)) {
                    SimpleStatsTimeVarying stat = new SimpleStatsTimeVarying(metricName);
                    stat.setEventListID(simulation.getEventListID());
                    simulation.addPropertyChangeListener(metricName, stat);
                    innerStats.put(metricName, stat);
                }
                break;
            case TALLY:
                if (!innerStats.containsKey(metricName)) {
                    SimpleStatsTally stat = new SimpleStatsTally(metricName);
                    simulation.addPropertyChangeListener(metricName, stat);
                    innerStats.put(metricName, stat);
                }
                break;
            case COLLECTION:
                if (!innerStats.containsKey(metricName)) {
                    CollectionSizeTimeVaryingStats stat = new CollectionSizeTimeVaryingStats(metricName);
                    stat.setEventListID(simulation.getEventListID());
                    simulation.addPropertyChangeListener(metricName, stat);
                    innerStats.put(metricName, stat);
                }
                break;
            case COUNT:
                LOGGER.warning("COUNT metric not supportedd (yet)");
                break;

        }
        if (!outerStats.containsKey(metricName)) {
            SimpleStatsTally simpleStatsTally = new SimpleStatsTally(metricName);
            outerStats.put(metricName, simpleStatsTally);
        }
    }

}
