package finite;

import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.SimpleStatsTally;

/**
 *
 * @author ahbuss
 */
public class TestMinUniform {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 10;
        RandomVariate rng = RandomVariateFactory.getInstance("Uniform", 0.0, 1.0);
        double target = 4.0;

        double reps = 10000;
        SimpleStatsTally sst = new SimpleStatsTally(String.format("%,d min of %s", n, rng));
        for (n = 1; n < 100; ++n) {
            sst.reset();
            for (int rep = 1; rep <= reps; ++rep) {
                double min = Double.POSITIVE_INFINITY;
                for (int i = 0; i < n; ++i) {
                    double next = rng.generate() * n * target;
                    if (next <= min) {
                        min = next;
                    }
                }
                sst.newObservation(min);
            }
            System.out.printf("mean of min of %,d: %,.4f%n", n, sst.getMean());
        }

    }

}
