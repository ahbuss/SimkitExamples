package random;

import simkit.Schedule;
import simkit.random.DiscreteRandomVariate;
import simkit.random.GammaARVariate;
import simkit.random.MarkovChainVariate;
import simkit.random.PoissonVariate;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestCorrelatedDemands {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double lambda = 0.5;
        double rho = 0.8;

        RandomVariate interDemandGenerator = RandomVariateFactory.getInstance("GammaAR", lambda, rho, 3);
//        interDemandGenerator = RandomVariateFactory.getInstance("Gamma", 1.2, 3.4);
//        System.out.println(((GammaARVariate)interDemandGenerator).getMixtureVariate());
        
        double truncationTime = 2000.0;
        double[][] matrix = new double[][]{
            new double[]{1, 2, 3, 4},
            new double[]{5, 6, 7, 8},
            new double[]{9, 10, 11, 12},
            new double[]{13, 14, 15, 16}
        };
//        matrix = new double[][]{
//            new double[]{10, 1, 0, 0},
//            new double[]{1, 10, 1, 0},
//            new double[]{0, 1, 10, 1},
//            new double[]{0, 0, 1, 10}
//        };

//        matrix = new double[][] {new double[]{1, 9}, new double[] {8,2}};
//
        MarkovChainVariate demandGenerator
                = (MarkovChainVariate) RandomVariateFactory.getDiscreteRandomVariateInstance("MarkovChain",
                        new Object[]{matrix});
        
        DiscreteRandomVariate demandGenerator2 = RandomVariateFactory.getDiscreteRandomVariateInstance("Poisson", 1.0);
        double multiplier = 5.0;
        CorrelatedDemands demands = new CorrelatedDemands(
                interDemandGenerator, demandGenerator2, multiplier, truncationTime);
        System.out.println(demands);

        

        double meanInterdemandTime = ((GammaARVariate) interDemandGenerator).getLambda() 
                * ((GammaARVariate) interDemandGenerator).getK();
//        double meanInterdemandTime = ((GammaVariate)interDemandGenerator).getAlpha() *
//                ((GammaVariate)interDemandGenerator).getBeta();
//        System.out.printf("Mean interdemand time = %,.3f%n", meanInterdemandTime);
        
        double meanDemand = ((PoissonVariate)demandGenerator2).getMean();
        
        System.out.printf("theoretical: %,.3f%n", multiplier);

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
