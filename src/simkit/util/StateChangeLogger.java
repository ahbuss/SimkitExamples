package simkit.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import java.util.logging.Logger;
import simkit.Schedule;
import simkit.SimEvent;

/**
 *
 * @author ahbuss
 */
public class StateChangeLogger implements PropertyChangeListener {

    private static final Logger LOGGER = Logger.getLogger(StateChangeLogger.class.getName());

    private PrintStream printStream;

    public StateChangeLogger(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "end":
                printStream.printf("%,.3f: Simulation finished%n", Schedule.getSimTime());
                printStream.close();
                break;
            default:
                if (evt.getNewValue() instanceof Number) {
                    SimEvent currentEvent = Schedule.getCurrentEvent();
                    printStream.printf("%,.3f: %s; %s: %s%n",
                            currentEvent.getScheduledTime(), currentEvent.getEventName(),
                            evt.getPropertyName(), evt.getNewValue());
                }
        }

    }

}
