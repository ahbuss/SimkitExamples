package simkit.components;

import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class IntArrivalProcess extends ArrivalProcess {
    
    protected int value;
    
    public IntArrivalProcess() {
        super();
    }
    
    public IntArrivalProcess(int value) {
        this();
        this.setValue(value);
    }
    
    public IntArrivalProcess(RandomVariate interarrivalTimeGenerator, int value){ 
        this(value);
        setInterarrivalTimeGenerator(interarrivalTimeGenerator);
    }
    
    @Override
    public void doArrival() {
        super.doArrival();
        waitDelay("Arrival", 0.0, value);
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
}
