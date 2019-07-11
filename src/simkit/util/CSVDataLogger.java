package simkit.util;

import com.opencsv.CSVWriter;
import com.sun.xml.internal.ws.policy.NestedPolicy;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.SimEvent;
import simkit.SimEventListener;

/**
 *
 * @author ahbuss
 */
public class CSVDataLogger implements SimEventListener, PropertyChangeListener {

    private static final Logger LOGGER = Logger.getLogger(CSVDataLogger.class.getName());

    private CSVWriter csvWriter;

    private boolean firstPropertyChange;

    private List<String> header;

    private Map<String, Method> statesAndGetters;
    
    private int replication;

    public CSVDataLogger(File outputFile) {
        this.firstPropertyChange = true;
        this.header = new ArrayList<>();
        this.header.add("Replication");
        this.header.add("SimTime");
        this.header.add("SimEvent");
        if (outputFile != null) {
            try {
                FileWriter fileWriter = new FileWriter(outputFile);
                csvWriter = new CSVWriter(fileWriter);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        } else {
            LOGGER.severe("Input file is null");
            throw new NullPointerException("Input file is null");
        }
    }

    public void close() {
        if (csvWriter != null) {
            try {
                csvWriter.close();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void processSimEvent(SimEvent event) {
        if (firstPropertyChange) {
            firstPropertyChange = false;
            statesAndGetters = Utilities.findStatesAndGetters(event.getSource());
            for (String state : statesAndGetters.keySet()) {
                header.add(state);
            }
            csvWriter.writeNext(header.toArray(new String[0]));
        }
        List<String> nextLine = new ArrayList<>();
        nextLine.add(Integer.toString(replication));
        nextLine.add(Double.toString(event.getScheduledTime()));
        nextLine.add(event.getEventName());
        for (String state : statesAndGetters.keySet()) {
            Method getter = statesAndGetters.get(state);
            try {
                Object value = getter.invoke(event.getSource());
                if (value != null) {
                    nextLine.add(value.toString());
                } else {
                    nextLine.add("null");
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(CSVDataLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        csvWriter.writeNext(nextLine.toArray(new String[0]));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("replication")) {
            if (evt.getNewValue() instanceof Number) {
                this.replication = ((Number) evt.getNewValue()).intValue();
            }
        }
    }

}
