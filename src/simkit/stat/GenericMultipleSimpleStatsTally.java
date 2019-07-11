package simkit.stat;

import static java.lang.Double.NaN;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import simkit.util.GenericPropertyChangeEvent;
import simkit.util.GenericPropertyChangeListener;

/**
 *
 * @author ahbuss
 * @param <T> The type of objects that will be used for the "keys"
 */
public class GenericMultipleSimpleStatsTally<T> implements GenericPropertyChangeListener<T, Number> {

    private static final Logger LOGGER = Logger.getLogger(GenericMultipleSimpleStatsTally.class.getName());

    private final Map<T, SimpleStatsTally> allStats;

    private final String property;

    private static final SimpleStatsTally EMPTY_STAT = new SimpleStatsTally();

    public GenericMultipleSimpleStatsTally(String property) {
        this.property = property;
        this.allStats = new HashMap<>();
    }

    @Override
    public void propertyChange(GenericPropertyChangeEvent<T, Number> evt) {
        if (evt.getPropertyName().matches(property)) {
            SimpleStatsTally tallyStat = allStats.get(evt.getKey());
            if (tallyStat == null) {
                tallyStat = new SimpleStatsTally(property);
                allStats.put(evt.getKey(), tallyStat);
            }
            tallyStat.propertyChange(evt);
        }
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return the allStats
     */
    public Map<T, SimpleStatsTally> getAllStats() {
        return new HashMap<>(allStats);
    }

    public double getMinObs(T key) {
        if (allStats.containsKey(key)) {
            return allStats.get(key).getMinObs();
        } else {
            return NaN;
        }
    }

    public double getMaxObs(T key) {
        if (allStats.containsKey(key)) {
            return allStats.get(key).getMaxObs();
        } else {
            return NaN;
        }
    }

    public double getMean(T key) {
        if (allStats.containsKey(key)) {
            return allStats.get(key).getMean();
        } else {
            return NaN;
        }
    }

    public double getVariance(T key) {
        if (allStats.containsKey(key)) {
            return allStats.get(key).getVariance();
        } else {
            return NaN;
        }
    }

    public double getStandardDeviation(T key) {
        if (allStats.containsKey(key)) {
            return allStats.get(key).getStandardDeviation();
        } else {
            return NaN;
        }
    }

    public int getCount(T key) {
        if (allStats.containsKey(key)) {
            return allStats.get(key).getCount();
        } else {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("property: " + getProperty());
        
        for (T key: allStats.keySet()) {
            SimpleStatsTally tallyStat = allStats.get(key);
            builder.append(System.getProperty("line.separator"));
            builder.append(key).append(": ");
            builder.append(allStats.get(key).getDataLine());
        }
        return builder.toString();
    }

}
