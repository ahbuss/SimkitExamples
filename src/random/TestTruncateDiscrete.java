package random;

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
        double[] probs = new double[]{4, 3, 2, 1};

        DiscreteIntegerVariate div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);
        System.out.println(div);

        double mean = getMean(div);
        double var = getVariance(div);

        System.out.printf("mean = %,.4f, var = %,.4f%n", mean, var);

        values = Arrays.copyOf(values, 3);
        probs = Arrays.copyOf(probs, 3);
        div = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, probs);
        System.out.println(div);
        mean = getMean(div);
        var = getVariance(div);

        System.out.printf("mean = %,.4f, var = %,.4f%n", mean, var);

    }

}
