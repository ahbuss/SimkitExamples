package random;

import simkit.random.GammaARVariate;

/**
 *
 * @author ahbuss
 */
public class TestBinomialCoefficients {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 3;
        
        for (int k = 0; k <= n; ++k) {
            System.out.printf("C(%d, %d) = %,d%n", n, k, binomialCoefficient(n, k));
        }
    }
    
    public static int binomialCoefficient(int n, int k) {
        return GammaARVariate.binomialCoefficient(n, k); 
    }

}
