package random;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;
import java.util.Arrays;
import static random.RandomVariateUtil.getMean;
import static random.RandomVariateUtil.getVariance;
import simkit.random.DiscreteIntegerVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestTruncateDiscrete {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] values = new int[]{5, 10, 20, 100};
        double[] probs = new double[]{4, 3, 2, .1};

        DiscreteIntegerVariate div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);
        System.out.println(div);

        double mean = getMean(div);
        double var = getVariance(div);
        double base = mean;
        double baseVar = var;

        System.out.printf("mean = %,.4f, var = %,.4f stdev = %,.4f%n", mean, var, sqrt(var));

        values = Arrays.copyOf(values, 3);
        probs = Arrays.copyOf(probs, 3);
        div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);
        System.out.println();
        System.out.println(div);
        mean = getMean(div);
        var = getVariance(div);

        System.out.printf("mean = %,.4f, var = %,.4f stdev = %,.4f%n", mean, var, sqrt(var));
        System.out.printf("%% reduction in mean: %,.2f%%%n", 100 * (1.0 - mean / base));
        System.out.printf("%% reduction in variance: %,.2f%%%n", 100 * (1.0 - var / baseVar));
        System.out.printf("%% reduction in stdev: %,.2f%%%n", 100 * (1.0 - sqrt(var) / sqrt(baseVar)));

        probs = new double[]{4, 3, 2, .1};
        values = new int[]{5, 10, 20, 90};
        div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);

        System.out.println();
        System.out.println(div);
        mean = getMean(div);
        var = getVariance(div);

        System.out.printf("mean = %,.4f, var = %,.4f stdev = %,.4f%n", mean, var, sqrt(var));
        System.out.printf("%% reduction in mean: %,.2f%%%n", 100 * (1.0 - mean / base));
        System.out.printf("%% reduction in variance: %,.2f%%%n", 100 * (1.0 - var / baseVar));
        System.out.printf("%% reduction in stdev: %,.2f%%%n", 100 * (1.0 - sqrt(var) / sqrt(baseVar)));

        values = new int[]{5, 10, 20, 75};
        div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);

        System.out.println();
        System.out.println(div);
        mean = getMean(div);
        var = getVariance(div);

        System.out.printf("mean = %,.4f, var = %,.4f stdev = %,.4f%n", mean, var, sqrt(var));
        System.out.printf("%% reduction in mean: %,.2f%%%n", 100 * (1.0 - mean / base));
        System.out.printf("%% reduction in variance: %,.2f%%%n", 100 * (1.0 - var / baseVar));
        System.out.printf("%% reduction in stdev: %,.2f%%%n", 100 * (1.0 - sqrt(var) / sqrt(baseVar)));

        values = new int[]{5, 10, 20, 75};
        div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);

        System.out.println();
        System.out.println(div);
        mean = getMean(div);
        var = getVariance(div);

        System.out.printf("mean = %,.4f, var = %,.4f stdev = %,.4f%n", mean, var, sqrt(var));
        System.out.printf("%% reduction in mean: %,.2f%%%n", 100 * (1.0 - mean / base));
        System.out.printf("%% reduction in variance: %,.2f%%%n", 100 * (1.0 - var / baseVar));
        System.out.printf("%% reduction in stdev: %,.2f%%%n", 100 * (1.0 - sqrt(var) / sqrt(baseVar)));

        probs = ((DiscreteIntegerVariate) div).getFrequencies();
        double prob1 = (0.10 - probs[3]);
        double prob2 = probs[2] - prob1;

        System.out.println(Arrays.toString(probs));
        System.out.printf("%f + %f =? %f%n", prob1, prob2, prob1 + prob2);

        probs = new double[]{probs[0], probs[1], prob1, probs[2] - prob1, probs[3]};
        values = new int[]{values[0], values[1], values[2] * 3 / 4, values[2], values[3]};
//        System.out.println(Arrays.toString(probs));
//        System.out.println(Arrays.toString(values));

        div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);
        System.out.println();
        System.out.println(div);
        mean = getMean(div);
        var = getVariance(div);

//        System.out.printf("base: %f baseVar: %f%n", base, baseVar);
        System.out.printf("mean = %,.4f, var = %,.4f stdev = %,.4f%n", mean, var, sqrt(var));
        System.out.printf("%% reduction in mean: %,.2f%%%n", 100 * (1.0 - mean / base));
        System.out.printf("%% reduction in variance: %,.2f%%%n", 100 * (1.0 - var / baseVar));
        System.out.printf("%% reduction in stdev: %,.2f%%%n", 100 * (1.0 - sqrt(var) / sqrt(baseVar)));

        probs = new double[]{4, 3, 2, .1};
        values = new int[]{5, 10, 20, 100};
        for (int i = 0; i < values.length; ++i) {
            values[i] = (int) ceil((values[i] * 0.75));
        }
        div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);
        System.out.println();
        System.out.println(div);
        mean = getMean(div);
        var = getVariance(div);

//        System.out.printf("base: %f baseVar: %f%n", base, baseVar);
        System.out.printf("mean = %,.4f, var = %,.4f stdev = %,.4f%n", mean, var, sqrt(var));
        System.out.printf("%% reduction in mean: %,.2f%%%n", 100 * (1.0 - mean / base));
        System.out.printf("%% reduction in variance: %,.2f%%%n", 100 * (1.0 - var / baseVar));
        System.out.printf("%% reduction in stdev: %,.2f%%%n", 100 * (1.0 - sqrt(var) / sqrt(baseVar)));

    }

}
