package simkit.random;

import java.util.Arrays;
import static simkit.random.MarkovChainVariate.deepCopy;
import static simkit.random.MarkovChainVariate.isSquare;
import static simkit.random.MarkovChainVariate.normalize;

/**
 *
 * @author ahbuss
 */
public class TestDeepCopy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[][] matrix = new double[][]{
            new double[]{1, 2, 3, 4},
            new double[]{5, 6, 7, 8},
            new double[]{9, 10, 11, 12},
            new double[]{13, 14, 15, 16}
        };

        System.out.println("Original:");
        System.out.println(matrixToString(matrix));

        double[][] copy = deepCopy(matrix);
        System.out.println("copy:");
        System.out.println(matrixToString(copy));

        normalize(copy);
        System.out.println("Normalized:");
        System.out.println(matrixToString(copy));
        
        System.out.println(isSquare(copy));
        copy[0] = new double[5];
        System.out.println(isSquare(copy));
    }

    public static String matrixToString(double[][] matrix) {
        StringBuilder builder = new StringBuilder(Arrays.toString(matrix[0]));
        for (int i = 1; i < matrix.length; ++i) {
            builder.append('\n').append(Arrays.toString(matrix[i]));
        }
        return builder.toString();
    }

}
