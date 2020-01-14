package random;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import simkit.random.DiscreteIntegerVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestConsolidate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] values = new int[] { 1, 1, 3, 4, 5, 5, 6, 1, 3};
        double[] frequencies = new double[values.length];
        Arrays.fill(frequencies, 1.0);
        DiscreteIntegerVariate rv = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, frequencies);
        System.out.println(rv);
        consolidateFrequenciesFor(rv);
        System.out.println(rv);
    }

    public static void consolidateFrequenciesFor(DiscreteIntegerVariate variate) {
        int[] values = variate.getValues();
        double[] frequencies = variate.getFrequencies();
        Map<Integer, Double> map = new TreeMap<>();
        for (int i = 0; i < values.length; ++i) {
            int value = values[i];
            double newFrequency = map.getOrDefault(value, 0.0);
            map.put(value, newFrequency + frequencies[i]);
        }
        values = new int[map.size()];
        frequencies = new double[map.size()];
        int count = 0;
        for (int value: map.keySet()) {
            values[count] = value;
            frequencies[count] = map.get(value);
            count += 1;
        }
        variate.setParameters(values, frequencies);
    }
}
