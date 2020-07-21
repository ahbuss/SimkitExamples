package simkit.random;

import static simkit.random.MarkovChainVariate.matrixSquare;
import static simkit.random.MarkovChainVariate.matrixToString;
import static simkit.random.MarkovChainVariate.normalize;
import static simkit.random.MarkovChainVariate.score;

/**
 *
 * @author ahbuss
 */
public class TestPowerSolve {

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

//        matrix = new double[][]{new double[]{9, 1}, new double[]{3, 7}};
        System.out.println(matrixToString(matrix));
        System.out.println("====");

        normalize(matrix);
        double[][] matrixSq = matrixSquare(matrix);
        System.out.println(matrixToString(matrixSq));
        System.out.println("====");
        matrixSq = matrixSquare(matrixSq);
        System.out.println(matrixToString(matrixSq));
        System.out.println("====");
        matrixSq = matrixSquare(matrixSq);
        System.out.println(matrixToString(matrixSq));
        System.out.println("====");
        matrixSq = matrixSquare(matrixSq);
        System.out.println(matrixToString(matrixSq));
        System.out.println("====");
        matrixSq = matrixSquare(matrixSq);
        System.out.println(matrixToString(matrixSq));
        System.out.println("====");
        matrixSq = matrixSquare(matrixSq);
        System.out.println(matrixToString(matrixSq));

        System.out.println(score(matrix));
        System.out.println(score(matrixSq));

    }

}
