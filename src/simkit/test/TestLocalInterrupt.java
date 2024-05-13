package simkit.test;

import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestLocalInterrupt extends ArrivalProcess {

    private double stopTime;
    
    public TestLocalInterrupt() {        
    }
    
    public TestLocalInterrupt(RandomVariate interarrivalTimeGenerator,
            double stopTime) {
        super(interarrivalTimeGenerator);
        setStopTime(stopTime);
    }
    
    public void doRun() {
        super.doRun();
        waitDelay("Interrupt", stopTime);
    }

    public void doInterrupt() {
        this.interrupt("Arrival");
    }
    
    /**
     * @return the stopTime
     */
    public double getStopTime() {
        return stopTime;
    }

    /**
     * @param stopTime the stopTime to set
     */
    public void setStopTime(double stopTime) {
        this.stopTime = stopTime;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate interarrivalTimeGenerator =
                RandomVariateFactory.getInstance("Constant", 1.0);
        TestLocalInterrupt instance = new TestLocalInterrupt(interarrivalTimeGenerator, 5.5);
        System.out.println(instance);
        
        Schedule.setVerbose(true);
        Schedule.reset();
        Schedule.startSimulation();
    }
    
}
