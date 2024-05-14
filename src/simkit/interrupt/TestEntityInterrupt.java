package simkit.interrupt;

import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestEntityInterrupt  extends ArrivalProcess {

    private double time;
    
    public TestEntityInterrupt(RandomVariate interarrivaltimeGenerator, double time) {
        super(interarrivaltimeGenerator);
        setTime(time);
    }
    
    @Override
    public void doRun() {
        super.doRun();
        waitDelay("Interrupt", time);
    }

    public void doInterrupt() {
        interrupt("Arrival");
        firePropertyChange("interrupt", getSerial());
    }
    
    /**
     * @return the time
     */
    public double getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(double time) {
        this.time = time;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestEntityInterrupt simone = 
                new TestEntityInterrupt(
                        RandomVariateFactory.getInstance("Constant", 1.0), 5.5);
        System.out.println(simone);
        
        ArrivalProcess arrivalProcess = new ArrivalProcess(RandomVariateFactory.getInstance("Constant", 1.0));
                System.out.println(arrivalProcess);
                
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        simone.addPropertyChangeListener(simplePropertyDumper);
        arrivalProcess.addPropertyChangeListener(simplePropertyDumper);
        
        Schedule.setVerbose(true);
        Schedule.stopAtTime(10.0);
        
        Schedule.reset();
        Schedule.startSimulation();
    }
    
}
