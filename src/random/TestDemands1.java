package random;

import java.util.Arrays;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import simkit.Schedule;
import simkit.random.GammaARVariate;
import simkit.random.MarkovChainVariate;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestDemands1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double lambda = 2.5;
        double rho = 0.7;

        RandomVariate interDemandGenerator = RandomVariateFactory.getInstance("GammaAR", lambda, rho);

        double truncationTime = 2000.0;
        double[][] matrix = new double[][]{
            new double[]{1, 2, 3, 4},
            new double[]{5, 6, 7, 8},
            new double[]{9, 10, 11, 12},
            new double[]{13, 14, 15, 16}
        };
//        matrix = new double[][] {new double[]{1, 9}, new double[] {8,2}};
//
        MarkovChainVariate demandGenerator
                = (MarkovChainVariate) RandomVariateFactory.getDiscreteRandomVariateInstance("MarkovChain",
                        new Object[]{matrix});
        Demands demands = new Demands(interDemandGenerator, demandGenerator, truncationTime);
        System.out.println(demands);

        MarkovChainVariate.normalize(matrix);
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
        RealMatrix coefficients = new Array2DRowRealMatrix(matrix).transpose();

        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        double[] rhs = new double[matrix.length];
        Arrays.fill(rhs, 0.0);
        rhs[0] = 1.0;

        RealVector rhsVector = new ArrayRealVector(rhs);

        RealVector solution = solver.solve(rhsVector);

        double meanDemand = 0.0;
        for (int index = 0; index < solution.getDimension(); ++index) {
            meanDemand += index * solution.getEntry(index);
        }

        System.out.println(solution);

        double meanInterdemandTime = ((GammaARVariate) interDemandGenerator).getLambda() * 2;
        System.out.printf("theoretical: %,.3f%n", meanDemand / meanInterdemandTime);

        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
//        demands.addPropertyChangeListener(simplePropertyDumper);

//        Schedule.setVerbose(true);
        double stopTime = 10000000.0;

        Schedule.stopAtTime(stopTime);

        Schedule.reset();
        Schedule.startSimulation();

        double demandRate = demands.getNumberDemands() / (Schedule.getSimTime() - truncationTime);
        System.out.printf("estimated = %,.3f%n", demandRate);

    }

}
