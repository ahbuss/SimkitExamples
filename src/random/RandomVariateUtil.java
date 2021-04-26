package random;

import simkit.random.DiscreteIntegerVariate;

/**
 *
 * @author ahbuss
 */
public class RandomVariateUtil {

    public static double getMean(DiscreteIntegerVariate rv) {
        double mean = 0.0;

        int[] values = rv.getValues();
        double[] probs = rv.getFrequencies();

        for (int i = 0; i < values.length; ++i) {
            mean += probs[i] * values[i];
        }

        return mean;
    }

    public static double getVariance(DiscreteIntegerVariate rv) {
        double mean = getMean(rv);
        int[] values = rv.getValues();
        double[] probs = rv.getFrequencies();

        double var = 0.0;
        for (int i = 0; i < values.length; ++i) {
            var += probs[i] * values[i] * values[i];
        }
        return var - mean * mean;
    }

    public static double getVariance2(DiscreteIntegerVariate rv) {
        double var = 0.0;
        double mean = getMean(rv);
        int[] values = rv.getValues();
        double[] probs = rv.getFrequencies();

        for (int i = 0; i < values.length; ++i) {
            double diff = values[i] - mean;
            var += probs[i] * diff * diff;
        }

        return var;
    }

}
