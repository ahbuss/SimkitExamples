package simkit.random;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import static simkit.random.MarkovChainVariate.normalize;
import static simkit.random.TestDeepCopy.matrixToString;

/**
 *
 * @author ahbuss
 */
public class TestSolve {

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

//        matrix = new double[][] {new double[]{1, 9}, new double[] {8,2}};
        MarkovChainVariate mcv
                = (MarkovChainVariate) RandomVariateFactory.getDiscreteRandomVariateInstance("MarkovChain",
                        new Object[]{matrix});
        System.out.println(matrixToString(mcv.getTransitionMatrix()));

        normalize(matrix);
        for (int row = 0; row < matrix.length; ++row) {
            for (int col = 0; col < matrix[row].length; ++col) {
                if (row == col) {
                    matrix[row][col] = matrix[row][col] - 1.0;
                }
            }
        }
        for (int row = 0; row < matrix.length; ++row) {
            matrix[row][0] = 1.0;
        }

        double[] rhs = new double[matrix.length];
        Arrays.fill(rhs, 0.0);
        rhs[0] = 1.0;

        System.out.println(Arrays.toString(rhs));

        RealMatrix coefficients = new Array2DRowRealMatrix(matrix).transpose();

        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

        RealVector rhsVector = new ArrayRealVector(rhs);

        RealVector solution = solver.solve(rhsVector);

        System.out.println(solution);

        for (int index = 0; index < solution.getDimension(); ++index) {
            System.out.println(index + ": " + solution.getEntry(index));
        }

        int[] frequencies = new int[matrix.length];
        Arrays.fill(frequencies, 0);

        int number = 1000000;

        for (int i = 0; i < number; ++i) {
            int nextState = mcv.generateInt();
            frequencies[nextState] += 1;
        }

        double[] probs = new double[frequencies.length];
        for (int i = 0; i < frequencies.length; ++i) {
            probs[i] = (double) frequencies[i] / number;
        }

        System.out.println(Arrays.toString(frequencies));
        System.out.println(Arrays.toString(probs));

    }

}
