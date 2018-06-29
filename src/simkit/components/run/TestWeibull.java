package simkit.components.run;

import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.SimpleStatsTally;

/**
 *
 * @author ahbuss
 */
public class TestWeibull {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate rv = RandomVariateFactory.getInstance("Gamma (5.200, 1.300)");
        System.out.println(rv);
        
        SimpleStatsTally stat = new SimpleStatsTally(rv.toString());
        int number = 1000000;
        
        for (int i = 0; i < number; ++i) {
            stat.newObservation(rv.generate());
        }
        System.out.println(stat);
    }

}
