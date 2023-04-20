package simkit.components;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static simkit.Priority.HIGH;
import static simkit.Priority.HIGHEST;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class TransferLineWithBlocking1 extends SimEntityBase {

    private int numberStations;

    private int[] totalNumberMachines;

    private RandomVariate[] processingTimeGenerator;

    private int[] queueCapacity;

    protected int[] numberInQueue;

    protected int[] numberAvailableMachines;

    protected int[] numberBlockedMachines;

    public TransferLineWithBlocking1() {

    }

    public TransferLineWithBlocking1(int numberStations, int[] totalNumberMachines,
            RandomVariate[] processingTimeGenerator, int[] queueCapacity) {
        setNumberStations(numberStations);
        setTotalNumberMachines(totalNumberMachines);
        setProcessingTimeGenerator(processingTimeGenerator);
        setQueueCapacity(queueCapacity);
        validate();
    }

    private void validate() {
        Map<String, Map<Integer, Integer>> badValues = new HashMap<>();
        boolean valid = numberStations == totalNumberMachines.length
                && numberStations == processingTimeGenerator.length
                && numberStations == queueCapacity.length;

        if (!valid) {
            String message = String.format("arrays don't match number stations: "
                    + "numberStations = %d, totalNumberMachines = %d,"
                    + "processingTimeGenerator = %d, queueCapacity = %d",
                    numberStations, totalNumberMachines.length,
                    processingTimeGenerator.length, queueCapacity.length);
            throw new IllegalArgumentException(message);
        }

        for (int i = 0; i < totalNumberMachines.length; ++i) {
            if (totalNumberMachines[i] <= 0) {
                if (!badValues.containsKey("totalNumberMachines")) {
                    badValues.put("totalNumberMachines", new LinkedHashMap<>());
                }
                badValues.get("totalNumberMachines").put(i, totalNumberMachines[i]);
            }
        }

        for (int i = 0; i < queueCapacity.length; ++i) {
            if (queueCapacity[i] <= 0) {
                if (!badValues.containsKey("queueCapacity")) {
                    badValues.put("queueCapacity", new LinkedHashMap<>());
                }
                badValues.get("totalNumberMachines").put(i, queueCapacity[i]);
            }
        }

        if (!badValues.isEmpty()) {
            String message = "One or more parameter values are invalid : " + badValues;
            throw new IllegalArgumentException(message);
        }

    }

    @Override
    public void reset() {
        super.reset();
        validate();
        numberInQueue = new int[numberStations];
        numberAvailableMachines = new int[numberStations];
        numberBlockedMachines = new int[numberStations];
    }

    public void doRun() {
        waitDelay("Init", 0.0, HIGHEST, 0);
    }

    public void doInit(int station) {
        numberInQueue[station] = 0;
        fireIndexedPropertyChange(station, "numberInQueue", getNumberInQueue(station));

        numberAvailableMachines[station] = totalNumberMachines[station];
        fireIndexedPropertyChange(station, "numberAvailableMachines", getNumberAvailableMachines(station));

        numberBlockedMachines[station] = 0;
        fireIndexedPropertyChange(station, "numberBlockedMachines", getNumberBlockedMachines(station));

        if (station < numberStations - 1) {
            waitDelay("Init", 0.0, HIGHEST, station + 1);
        }
    }

    public void doArrival(int station) {
        int oldNumberInQueue = getNumberInQueue(station);
        numberInQueue[station] += 1;
        fireIndexedPropertyChange(station, "numberInQueue", oldNumberInQueue, getNumberInQueue(station));

        if (getNumberAvailableMachines(station) > 0) {
            waitDelay("StartProcessing", 0.0, HIGH, station);
        }
    }

    public void doStartProcessing(int station) {
        int oldNumberInQueue = getNumberInQueue(station);
        numberInQueue[station] -= 1;
        fireIndexedPropertyChange(station, "numberInQueue", oldNumberInQueue, getNumberInQueue(station));

        int oldNumberAvailableMachines = getNumberAvailableMachines(station);
        numberAvailableMachines[station] -= 1;
        fireIndexedPropertyChange(station, "numberAvailableMachines", oldNumberAvailableMachines, getNumberAvailableMachines(station));

        waitDelay("EndProcessing", processingTimeGenerator[station], station);

        if (station > 0 && getNumberBlockedMachines(station - 1) > 0) {
            waitDelay("Unblock", 0.0, station - 1);
        }

    }

    public void doEndProcessing(int station) {
        int oldNumberBlockedMachines = getNumberBlockedMachines(station);
        numberBlockedMachines[station] += 1;
        fireIndexedPropertyChange(station, "numberBlockedMachines", oldNumberBlockedMachines, getNumberBlockedMachines(station));

        if (station == numberStations - 1 || (station < numberStations - 1
                && getNumberInQueue(station + 1) < getQueueCapacity(station + 1))) {
            waitDelay("Unblock", 0.0, station);
        }
    }

    public void doUnblock(int station) {
        int oldNumberBlockedMachines = getNumberBlockedMachines(station);
        numberBlockedMachines[station] -= 1;
        fireIndexedPropertyChange(station, "numberBlockedMachines", oldNumberBlockedMachines, getNumberBlockedMachines(station));

        int oldNumberAvailableMachines = getNumberAvailableMachines(station);
        numberAvailableMachines[station] += 1;
        fireIndexedPropertyChange(station, "numberAvailableMachines", oldNumberAvailableMachines, getNumberAvailableMachines(station));

        if (station < numberStations - 1) {
            waitDelay("Arrival", 0.0, station + 1);
        }

        if (getNumberInQueue(station) > 0) {
            waitDelay("StartProcessing", 0.0, HIGH, station);
        }

    }

    /**
     * @return the numberStations
     */
    public int getNumberStations() {
        return numberStations;
    }

    /**
     * @param numberStations the numberStations to set
     */
    public void setNumberStations(int numberStations) {
        if (numberStations <= 0) {
            throw new IllegalArgumentException("numberStations must be > 0: " + numberStations);
        }
        this.numberStations = numberStations;
    }

    /**
     * @return the totalNumberMachines
     */
    public int[] getTotalNumberMachines() {
        return totalNumberMachines.clone();
    }

    public int getTotalNumberMachines(int station) {
        return totalNumberMachines[station];
    }

    /**
     * @param totalNumberMachines the totalNumberMachines to set
     */
    public void setTotalNumberMachines(int[] totalNumberMachines) {
        this.totalNumberMachines = totalNumberMachines.clone();
    }

    public void setTotalNumberMachines(int station, int totalNumberMachines) {
        this.totalNumberMachines[station] = totalNumberMachines;
    }

    /**
     * @return the processingTimeGenerator
     */
    public RandomVariate[] getProcessingTimeGenerator() {
        return processingTimeGenerator.clone();
    }

    public RandomVariate getProcessingTimeGenerator(int station) {
        return processingTimeGenerator[station];
    }

    /**
     * @param processingTimeGenerator the processingTimeGenerator to set
     */
    public void setProcessingTimeGenerator(RandomVariate[] processingTimeGenerator) {
        this.processingTimeGenerator = processingTimeGenerator.clone();
    }

    public void setProcessingTimeGenerator(int station, RandomVariate processingTimeGenerator) {
        this.processingTimeGenerator[station] = processingTimeGenerator;
    }

    /**
     * @return the queueCapacity
     */
    public int[] getQueueCapacity() {
        return queueCapacity.clone();
    }

    public int getQueueCapacity(int station) {
        return queueCapacity[station];
    }

    /**
     * @param queueCapacity the queueCapacity to set
     */
    public void setQueueCapacity(int[] queueCapacity) {
        this.queueCapacity = queueCapacity.clone();
    }

    public void setQueueCapacity(int station, int queueCapacity) {
        this.queueCapacity[station] = queueCapacity;
    }

    /**
     * @return the numberInQueue
     */
    public int[] getNumberInQueue() {
        return numberInQueue.clone();
    }

    public int getNumberInQueue(int station) {
        return numberInQueue[station];
    }

    /**
     * @return the numberAvailableMachines
     */
    public int[] getNumberAvailableMachines() {
        return numberAvailableMachines.clone();
    }

    public int getNumberAvailableMachines(int station) {
        return numberAvailableMachines[station];
    }

    /**
     * @return the numberBlockedMachines
     */
    public int[] getNumberBlockedMachines() {
        return numberBlockedMachines.clone();
    }

    public int getNumberBlockedMachines(int station) {
        return numberBlockedMachines[station];
    }
}
