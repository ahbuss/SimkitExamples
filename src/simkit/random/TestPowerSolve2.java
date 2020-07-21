package simkit.random;

import java.util.Arrays;
import static simkit.random.MarkovChainVariate.getSteadyState;
import static simkit.random.MarkovChainVariate.matrixToString;
import static simkit.random.MarkovChainVariate.normalize;

/**
 *
 * @author ahbuss
 */
public class TestPowerSolve2 {

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

        normalize(matrix);
        System.out.println(matrixToString(matrix));
        
        double[] steadyState = getSteadyState(matrix);
        
        System.out.println(Arrays.toString(steadyState));
    }

}
