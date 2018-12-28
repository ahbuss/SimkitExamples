package random;

import java.util.Arrays;
import java.util.logging.Logger;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.Histogram;

/**
 * Verify that giving equal weight to values even if duplicated will give
 * correct results.
 *
 * @author ahbuss
 */
public class TestDiscreteVariate {

    private static final Logger LOGGER = Logger.getLogger(TestDiscreteVariate.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Given values with duplicates
        double[] values = new double[]{1, 1, 2, 1, 3, 1, 2, 2, 1, 3};
//        Sort (should be in DiscreteVariate)
        Arrays.sort(values);
//        Fill frequncies with 1
        double[] frequecies = new double[values.length];
        Arrays.fill(frequecies, 1);

//        Get DiscreteVariate instanve
        RandomVariate rv = RandomVariateFactory.getInstance("Discrete", values, frequecies);
        System.out.println(rv);

//        Verify generated proportions
//        should be: 1 -> 0.5, 2 -> 0.3, 3 -> 0.2
        Histogram histogram = new Histogram("Discrete", 1, 3, 2);
        int number = 1000000;
        for (int i = 0; i < number; ++i) {
            histogram.newObservation(rv.generate());
        }
        System.out.println(histogram);
    }

}
