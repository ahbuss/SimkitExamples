package simkit.test;

import simkit.Schedule;
import simkit.components.IntArrivalProcess;
import simkit.components.TransferLineWithBlocking;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestTransferLineWithBlocking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate interarrivalTimeGenerator = RandomVariateFactory.getInstance("Uniform", 0.0, 0.5);
        int value = 0;
        IntArrivalProcess intArrivalProcess = new IntArrivalProcess(interarrivalTimeGenerator, value);
        System.out.println(intArrivalProcess);
        
        int numberMachines = 4;
        int[] totalNumberMachines = {2, 1, 1, 1};
        RandomVariate[] processingTimeGenerator = new RandomVariate[]{
            RandomVariateFactory.getInstance("Uniform", 0.0, 1.0),
            RandomVariateFactory.getInstance("Uniform", 0.0, 1.0),
            RandomVariateFactory.getInstance("Uniform", 0.0, 1.0),
            RandomVariateFactory.getInstance("Uniform", 10.0, 20.0),};
        int[] queueCapacity = {Integer.MAX_VALUE, 1, 1, 1};
        
        TransferLineWithBlocking transferLineWithBlocking = new TransferLineWithBlocking(numberMachines, totalNumberMachines, processingTimeGenerator, queueCapacity);
        System.out.println(transferLineWithBlocking);
        
        intArrivalProcess.addSimEventListener(transferLineWithBlocking);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
        transferLineWithBlocking.addPropertyChangeListener(simplePropertyDumper);

        Schedule.stopOnEvent(20, "Unblock", Integer.class);
//        Schedule.stopAtTime(100.0);
        Schedule.setVerbose(true);
        
        Schedule.reset();
        Schedule.startSimulation();
    }
    
}
