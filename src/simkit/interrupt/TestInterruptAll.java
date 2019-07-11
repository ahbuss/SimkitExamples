package simkit.interrupt;

import java.util.Arrays;
import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.util.SimplePropertyDumper;

/**
 * Illustration of using interruptAll to cancel all events of a given name
 * regardless of signature
 *
 * @author ahbuss
 */
public class TestInterruptAll extends SimEntityBase {

    /**
     * Schedule several AnEvent instances (with different signatures) and an
     * InteruptAll to occur prior to all (but one) of them.
     */
    public void doRun() {
        waitDelay("AnEvent", 2.0);
        waitDelay("AnEvent", 2.1, 3);
        waitDelay("AnEvent", 2.2, 4, 5, "Hi Mom!");
        waitDelay("AnEvent", 0.1, 10);
        waitDelay("InterruptAll", 1.0);
    }

    /**
     * Fire property change with argument if event occurs
     * @param signature 
     */
    public void doAnEvent(int signature) {
        firePropertyChange("inAnEvent", signature);
    }

    /**
     * InterruptAll "AnEvent" and schedule six more, plus
     * InterruptThese(4). This should cancel all the AnEvent(4)
     * but not the AnEvent(5) (or other arguments)
     * 
     */
    public void doInterruptAll() {
        Schedule.getDefaultEventList().interruptAll(this, "AnEvent");
        waitDelay("AnEvent", 1.0, 4);
        waitDelay("AnEvent", 2.3, 4, 5);
        waitDelay("AnEvent", 2.4, "Hi Mom!");
        waitDelay("AnEvent", 2.5, 4);
        waitDelay("AnEvent", 2.6, 4);
        waitDelay("AnEvent", 2.7, 5);
        waitDelay("InterruptThese", 1.5, 4);
    }

    /**
     * Interrupt only scheduled "anEvent" with the given parameter
     * @param parameter Given parameter
     */
    public void doInterruptThese(int parameter) {
        Schedule.getDefaultEventList().interruptAll(this, "AnEvent", parameter);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestInterruptAll instance = new TestInterruptAll();
        instance.addPropertyChangeListener(new SimplePropertyDumper());
        Schedule.setVerbose(true);
        Schedule.reset();
        Schedule.startSimulation();
    }

}
