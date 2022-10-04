package random;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import simkit.random.DiscreteIntegerVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestOutlier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "discrete.csv";
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return;
        }
        try {
            Scanner scanner = new Scanner(file);
            String valueLine = scanner.nextLine();
            String frequenciesLine = scanner.nextLine();

            String[] valuesStrings = valueLine.split("[ ,]+");
            String[] frequenciesStrings = frequenciesLine.split("[ ,]+");
            scanner.close();

            if (valuesStrings.length != frequenciesStrings.length) {
                System.err.printf("incompataible lengths: %d - %d%n", valuesStrings, frequenciesStrings);
                return;
            }
            int[] values = new int[valuesStrings.length];
            double[] frequencies = new double[frequenciesStrings.length];

            for (int i = 0; i < values.length; ++i) {
                values[i] = Integer.parseInt(valuesStrings[i].trim());
                frequencies[i] = Double.parseDouble(frequenciesStrings[i].trim());
            }
            DiscreteIntegerVariate original = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", values, frequencies);
            System.out.println("Original:");
            System.out.println(original);

            int[] truncatedValues = Arrays.copyOf(values, values.length - 1);
            double[] truncatedFrequecies = Arrays.copyOf(frequencies,frequencies.length - 1);

            DiscreteIntegerVariate truncated = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", truncatedValues,
                    truncatedFrequecies);
//            truncated = original;
            truncated = truncateBiggestValue(original);
//            truncated.setParameters(truncatedValues, truncatedFrequecies);
            System.out.println("Truncated:");
            System.out.println(truncated);

            double[] meanAndVariance = getMeanAndVariance(truncated);
            System.out.println(Arrays.toString(meanAndVariance));

            double outlier = values[values.length - 1];

            double delta = abs(meanAndVariance[0] - outlier) / sqrt(meanAndVariance[1]);
            System.out.println("Delta: " + delta);
            
            int[] truncatedValues2 = Arrays.copyOf(truncatedValues, truncatedValues.length - 1);
            double[] truncatedFrequencies2 = Arrays.copyOf(truncatedFrequecies, truncatedFrequecies.length - 1);
            
//             truncated = (DiscreteIntegerVariate) RandomVariateFactory.getInstance("DiscreteInteger", truncatedValues2,
//                    truncatedFrequencies2);
            truncated.setParameters(truncatedValues2, truncatedFrequencies2);
            System.out.println("Truncated (again):");
            System.out.println(truncated);

            meanAndVariance = getMeanAndVariance(truncated);
            System.out.println(Arrays.toString(meanAndVariance));
            outlier = truncatedValues2[truncatedValues2.length - 1];
            delta = abs(meanAndVariance[0] - outlier) / sqrt(meanAndVariance[1]);
            System.out.println("Delta: " + delta);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestOutlier.class.getName()).log(Level.SEVERE, null, ex);
        }
//        testIndex();
//        testRemoveIndexFromDouble();
        

    }

    public static double[] getMeanAndVariance(DiscreteIntegerVariate div) {
        double mean = 0.0;
        double variance = 0.0;

        int[] values = div.getValues();
        double[] probs = div.getFrequencies();

        for (int i = 0; i < values.length; ++i) {
            mean += values[i] * probs[i];
        }

        for (int i = 0; i < values.length; ++i) {
            variance += (values[i] - mean) * (values[i] - mean) * probs[i];
        }

        return new double[]{mean, variance};
        
    }
    
    public static DiscreteIntegerVariate truncateBiggestValue(DiscreteIntegerVariate rv) {
        
        int[] values = rv.getValues();
        double[] frequencies = rv.getFrequencies();
        
        int indexOfLargest = getIndexOfLargestValue(values);
        
        values = removeIndexFrom(values, indexOfLargest);
        frequencies = removeIndexFrom(frequencies, indexOfLargest);
        rv.setParameters(values, frequencies);
        return rv;
    }
    
    public static void testIndex() {
        int[] array = new int[10];
        Random random = new Random();
        for (int i = 0; i < array.length; ++i) {
            array[i] = random.nextInt(1000);
        }
        System.out.println(Arrays.toString(array));
        int index = getIndexOfLargestValue(array);
        System.out.printf("Index of largest: %d%n", index);
        
        int[] removed = removeIndexFrom(array, index);
        System.out.println(Arrays.toString(removed));
    }
    
    public static void testRemoveIndexFromDouble() {
        double[] array= new double[10];
        Random random = new Random();
        for (int i = 0; i < array.length; ++i) {
            array[i] = random.nextGaussian();
        }
        System.out.println("Original:");
        DoubleStream.of(array).forEach(System.out::println);
        int index = 5;
        System.out.println("Removing index " + index);
        double[] removed = removeIndexFrom(array, index);
        DoubleStream.of(removed).forEach(System.out::println);
        
    }
    
    public static int getIndexOfLargestValue(int[] array) {
        int index = 0;
        
        int largest = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] > largest) {
                largest = array[i];
                index = i;
            }
        }
        
        return index;
    }
    
    public static int[] removeIndexFrom(int[] array, int index) {
        int[] removed;
        if (array == null || index < 0 || index >= array.length) {
            removed = array;
        } else {
            removed = IntStream.range(0, array.length).
                    filter(i -> i != index).
                    map(i -> array[i]).
                    toArray();
        }
        return removed;
    }
    public static double[] removeIndexFrom(double[] array, int index) {
        double[] removed;
        if (array == null || index < 0 || index >= array.length) {
            removed = array;
        } else {
            removed = new double[array.length - 1];
            int k = 0;
            for (int i = 0; i< array.length; ++i) {
                if (i == index) { continue; }
                removed[k++] = array[i];
            }
            
        }
        return removed;
    }

}
