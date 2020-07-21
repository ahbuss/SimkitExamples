package simkit.random;

import static java.lang.Math.abs;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * @author ahbuss
 */
public class TestMean {
    
    private static final Logger LOGGER = Logger.getLogger(TestMean.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] values = new int[1001];
        for (int i = 0; i < values.length; ++i) {
            values[i] = i;
        }
        double[] probs = new double[values.length];
        for (int i = 0; i < probs.length; ++i) {
            probs[i] = 1.0;
        }
        
        System.out.printf("values: %s%n", Arrays.toString(values));
        System.out.printf("probs: %s%n", Arrays.toString(probs));
        double mean = mean(values, probs);
        System.out.println(mean);
        
        
        DiscreteIntegerVariate rv = new DiscreteIntegerVariate();
        rv.setParameters(values, probs);
//        System.out.println(rv);
        
//        Object[] params = rv.getParameters();
//        System.out.println(params[0].getClass().getName());
//        System.out.println(params[1].getClass().getName());

        double mean2 = mean(rv);
        System.out.println(mean2);
        
        System.out.println((double)abs(mean - mean2));
        
    }
    
    public static double mean(DiscreteIntegerVariate rv) {
        return mean(rv.getValues(), rv.getFrequencies());
    }
    
    public static double mean(double[] values, double[] frequencies) {
        if (values.length != frequencies.length) {
            String message = String.format("values & frequencies must be same length: %,d %,d",
                    values.length, frequencies.length);
        }
        double mean = 0.0;
        double[] probs = normalize(frequencies);
        for (int i = 0; i < values.length; ++i) {
            mean += values[i] * probs[i];
        }
        return mean;
    }
    
    public static double mean(int[] values, double[] frequencies) {
        return mean(copyTo(values), frequencies);
    }
    
    public static double[] copyTo(int[] intArray) {
        double[] doubleArray = new double[intArray.length];
        for (int i = 0; i < doubleArray.length; ++i) {
            doubleArray[i] = (double) intArray[i];
        }
        return doubleArray;
    }
    
    public static double[] normalize(double[] frequencies) {
        double[] probs = new double[frequencies.length];
        
        double sum = 0.0;
        for (double val: frequencies) {
            sum += val;
        }
        for (int i = 0; i < frequencies.length; ++i) {
            probs[i] = frequencies[i] / sum;
        }
        
        return probs;
    }

}
