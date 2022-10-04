package simkit.stat;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author ahbuss
 */
public class QuarterlyTallyStat extends SimpleStatsTally {

    protected final LocalDate startingDate;

    protected final SortedMap<LocalDate, Double> data;

    public QuarterlyTallyStat(String property, LocalDate startingDate) {
        super(property);
        this.startingDate = startingDate;
        this.data = new TreeMap<>();
    }

    public QuarterlyTallyStat(LocalDate startingDate) {
        this(DEFAULT_NAME, startingDate);
    }

    public void newObservation(double value) {
        super.newObservation(value);
    }
    /**
     * @return the startingDate
     */
    public LocalDate getStartingDate() {
        return startingDate;
    }

    /**
     * @return the data
     */
    public SortedMap<LocalDate, Double> getData() {
        return new TreeMap<>(data);
    }
    
    public static enum TIME_UNIT {
        
    }

}
