package simkit.random;

/**
 *
 * @author ahbuss
 */
public class TestMarkovChainVariate {

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
        
        MarkovChainVariate mcv = 
                (MarkovChainVariate)RandomVariateFactory.getDiscreteRandomVariateInstance("MarkovChain", 
                        new Object[] {matrix});
        for (int i = 0; i < 10; ++i) {
            System.out.println(mcv.generateInt());
        }
    }

}
