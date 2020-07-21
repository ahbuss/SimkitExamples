package random;

import simkit.Schedule;
import simkit.random.DiscreteRandomVariate;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestDemands {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double min = 2.3;
        double max = 4.5;
        RandomVariate interDemandGenerator = RandomVariateFactory.getInstance("Uniform", min, max);
        double r = 3.0;
        double p = 0.8;
        
        double truncationTime = 2000.0;
        
        DiscreteRandomVariate demandGenerator = RandomVariateFactory.getDiscreteRandomVariateInstance("NegativeBinomial", r, p);
        Demands demands = new Demands(interDemandGenerator, demandGenerator, truncationTime);
        System.out.println(demands);
        
        double meanInterdemandTime = (min + max) * 0.5;
        double meanDemand = r * p/(1.0 - p);
        
        System.out.printf("theoretical: %,.3f%n", meanDemand / meanInterdemandTime);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
//        demands.addPropertyChangeListener(simplePropertyDumper);
        
//        Schedule.setVerbose(true);
        
        double stopTime = 10000000.0;

        Schedule.stopAtTime(stopTime);
        
        Schedule.reset();
        Schedule.startSimulation();
        
        double demandRate = demands.getNumberDemands() / (Schedule.getSimTime() - truncationTime);
        System.out.printf("estimated = %,.3f%n", demandRate);
        
    }

}
