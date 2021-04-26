package random;

import static random.RandomVariateUtil.getMean;
import simkit.random.DiscreteIntegerVariate;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestRandomVariateUtil {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] values = new int[] {1,2,3,4};
        double[] probs = new double[] { 1,1,1,1};
        RandomVariate rv = RandomVariateFactory.getInstance("DiscreteInteger", values, probs);
        System.out.println(rv);
        
        double mean = getMean((DiscreteIntegerVariate)rv);
        System.out.printf("Mean: %,.2f%n", mean);
        
        double var = RandomVariateUtil.getVariance((DiscreteIntegerVariate)rv);
        double var2 = RandomVariateUtil.getVariance2((DiscreteIntegerVariate)rv);
        System.out.printf("%f & %f%n", var, var2);
    
    }
    
}
