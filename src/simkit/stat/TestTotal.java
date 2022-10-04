package simkit.stat;

import simkit.random.DiscreteRandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestTotal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleStatsTally sst = new SimpleStatsTally();
        DiscreteRandomVariate rv = RandomVariateFactory.getDiscreteRandomVariateInstance("Poisson", 3.4);
        System.out.println(rv);
        
        int number = 100000000;
        
        for (int i = 0; i < number; ++i) {
            sst.newObservation(rv.generateInt());
        }
        
        double total = sst.getMean() * sst.getCount();
        long totalInt = Math.round(total);
        
        System.out.printf("Total (double): %,f (long): %,d%n", total, totalInt);
    }
    
}
