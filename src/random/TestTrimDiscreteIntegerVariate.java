package random;

import java.util.Arrays;
import simkit.random.DiscreteIntegerVariate;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestTrimDiscreteIntegerVariate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] values = new double[]{2, 4, 8, 100};
        double[] frequencies = new double[values.length];
        Arrays.fill(frequencies, 1);
        RandomVariate rv = RandomVariateFactory.getInstance("DiscreteInteger", values, frequencies);
        System.out.println("Before trim");
        System.out.println(rv);
        trimDiscreteIntegerVariate((DiscreteIntegerVariate)rv, 50);
        System.out.println("After trim:");
        System.out.println(rv);
        trimDiscreteIntegerVariate((DiscreteIntegerVariate)rv, 5);
        System.out.println("After trim to 5:");
        System.out.println(rv);
    }
    
    public static void trimDiscreteIntegerVariate(DiscreteIntegerVariate variate, double threshold) {
        int[] values = variate.getValues();
        double[] frequencies = variate.getFrequencies();
        for (int i = 0; i < values.length; ++i) {
            if (values[i] > threshold) {
                values[i] = (int) threshold;
            }
        }
        variate.setParameters(values, frequencies);
    }
    
}
