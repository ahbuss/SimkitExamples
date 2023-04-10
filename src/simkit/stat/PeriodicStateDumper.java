package simkit.stat;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class PeriodicStateDumper implements PropertyChangeListener {

    private AbstractSimpleStats statObject;

    private double period;

    private PrintStream output;
    
    private StateDump stateDump;

    public PeriodicStateDumper(AbstractSimpleStats statObject, double period,
            PrintStream output) {
        this.setStatObject(statObject);
        this.setPeriod(period);
        this.setOutput(output);
        this.stateDump = new StateDump();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.statObject.propertyChange(evt);
    }

    /**
     * @return the statObject
     */
    public AbstractSimpleStats getStatObject() {
        return statObject;
    }

    /**
     * @param statObject the statObject to set
     */
    public final void setStatObject(AbstractSimpleStats statObject) {
        this.statObject = statObject;
    }

    /**
     * @return the period
     */
    public double getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public final void setPeriod(double period) {
        if (period <= 0.0) {
            throw new IllegalArgumentException("period must be > 0: " + period);
        }
        this.period = period;
    }

    /**
     * @return the output
     */
    public PrintStream getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public final void setOutput(PrintStream output) {
        this.output = output;
    }

    protected class StateDump extends SimEntityBase {

        @Override
        public void reset() {
            statObject.reset();
        }

        public void doRun() {
            waitDelay("Dump", period);
        }

        public void doDump() {
            output.printf("%.2f,%s%n", getEventList().getSimTime(), statObject.getDataLine().replaceAll("\\s", ","));
            statObject.reset();
            waitDelay("Dump", period);
        }
    }

}
