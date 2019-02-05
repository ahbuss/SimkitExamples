package misctests;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.random.DiscreteRandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;
/**
 *
 * @author ahbuss
 */
public class LocalDateComponent extends SimEntityBase {

    private static final Logger LOGGER = Logger.getLogger(LocalDateComponent.class.getName());
    
    private LocalDate startingDate;
        
    private DiscreteRandomVariate interarrivalTimeGenerator;
    
    private SortedSet<LocalDate> arrivals;
    
    public LocalDateComponent() { }
    
    public LocalDateComponent(LocalDate startingDate, DiscreteRandomVariate interarrivalTimeGenerator,
            SortedSet<LocalDate> arrivals) {
        this.setStartingDate(startingDate);
        this.setInterarrivalTimeGenerator(interarrivalTimeGenerator);
        this.setArrivals(arrivals);
    }

    public void doRun() {
        for (LocalDate arrival: arrivals) {
            long delay = ChronoUnit.DAYS.between(startingDate, arrival);
            waitDelay("Receipt", delay);
        }
        waitDelay("Arrival", interarrivalTimeGenerator.generateInt());
    }
    
    public void doReceipt() {
        firePropertyChange("receiptDate", startingDate.plusDays((long) Schedule.getSimTime()));
    }
    
    public void doArrival() {
        firePropertyChange("arrivalDate", startingDate.plusDays((long) Schedule.getSimTime()));
        
        int delay = interarrivalTimeGenerator.generateInt();
        firePropertyChange("delay", delay);
        
        waitDelay("Arrival", delay);
    }
    
    /**
     * @return the startingDate
     */
    public LocalDate getStartingDate() {
        return startingDate;
    }

    /**
     * @param startingDate the startingDate to set
     */
    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * @return the interarrivalTimeGenerator
     */
    public DiscreteRandomVariate getInterarrivalTimeGenerator() {
        return interarrivalTimeGenerator;
    }

    /**
     * @param interarrivalTimeGenerator the interarrivalTimeGenerator to set
     */
    public void setInterarrivalTimeGenerator(DiscreteRandomVariate interarrivalTimeGenerator) {
        this.interarrivalTimeGenerator = interarrivalTimeGenerator;
    }

    /**
     * @return the arrivals
     */
    public SortedSet<LocalDate> getArrivals() {
        return new TreeSet<>(arrivals);
    }

    /**
     * @param arrivals the arrivals to set
     */
    public void setArrivals(SortedSet<LocalDate> arrivals) {
        this.arrivals = new TreeSet<>(arrivals);
    }

    public static void main(String[] args) {
        DiscreteRandomVariate interDemandGenerator =
                RandomVariateFactory.getDiscreteRandomVariateInstance(
                        "DiscreteInteger", new int[] { 1, 2, 3, 4}, new double[] { 1,1,1,1});
//        System.out.println(interDemandGenerator);
        
        LocalDate startingDate = LocalDate.now().minusDays(10);
        SortedSet<LocalDate> arrivals = new TreeSet<>();
        arrivals.add(startingDate.plusDays(4));
        arrivals.add(startingDate.plusDays(2));
        arrivals.add(startingDate.plusDays(10));
        
        LocalDateComponent component = new LocalDateComponent();
        component.setArrivals(arrivals);
        component.setInterarrivalTimeGenerator(interDemandGenerator);
        component.setStartingDate(startingDate);
        
        System.out.println(component);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
        component.addPropertyChangeListener(simplePropertyDumper);
        
        Schedule.stopAtTime(20.0);
        Schedule.setVerbose(true);
        
        Schedule.reset();
        Schedule.startSimulation();
    }
    
}
