package simkit.random;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.logging.Logger;

/**
 *
 * @author ahbuss
 */
public class GammaARVariate extends RandomVariateBase {

    private static final Logger LOGGER = Logger.getLogger(GammaARVariate.class.getName());
    
    private double rho;
    
    private double lambda;
    
    private RandomVariate mixtureVariate;
    
    private double currentValue;
    
    public GammaARVariate() { }
    
    @Override
    public double generate() {
        currentValue = getRho() * currentValue + mixtureVariate.generate();
        return currentValue;
    }

    @Override
    public void setParameters(Object... params) {
        if (params.length == 2) {
            if (params[0] instanceof Number && params[1] instanceof Number) {
                setLambda(((Number)params[0]).doubleValue());
                setRho(((Number)params[1]).doubleValue());
                setupMixture();
            } else {
                String message = String.format("parameters must be Number: (%s, %s)",
                        params[0].getClass().getSimpleName(),
                        params[1].getClass().getSimpleName());
                LOGGER.severe(message);
                throw new IllegalArgumentException(message);
            }
        } else {
            String message = "GammaARVariate requires 2 parameters: " + params.length;
            LOGGER.severe(message);
            throw new IllegalArgumentException(message);
        }
    }

    private void setupMixture() {
        RandomVariate[] all = new RandomVariate[3];
        all[0] = RandomVariateFactory.getInstance("Constant", 0.0);
        all[1] = RandomVariateFactory.getInstance("Exponential", getLambda());
        all[2] = RandomVariateFactory.getInstance("Gamma", 2.0, getLambda());
        
        double[] mixture = new double[3];
        mixture[0] = getRho() * getRho();
        mixture[1] = 2.0 * getRho() * (1.0 - getRho());
        mixture[2] = pow(1.0 - getRho(), 2);
        
        this.mixtureVariate = RandomVariateFactory.getInstance("Mixed", mixture, all);
        this.currentValue = all[2].generate();
    }
    
    @Override
    public Object[] getParameters() {
        return new Object[] {getLambda(), getRho()};
    }

    /**
     * @return the rho
     */
    public double getRho() {
        return rho;
    }

    /**
     * @param rho the rho to set
     */
    public void setRho(double rho) {
        if (abs(rho) > 1.0) {
            String message = String.format("rho must be ∈ [-1,1]: %,f", rho);
            LOGGER.severe(message);
            throw new IllegalArgumentException(message);
        }
        this.rho = rho;
    }

    /**
     * @return the lambda
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * @param lambda the lambda to set
     */
    public void setLambda(double lambda) {
        if (lambda <= 0.0) {
            String message = "Lambda must be > 0.0: " + lambda;
            LOGGER.severe(message);
            throw new IllegalArgumentException(message);
        }
        this.lambda = lambda;
    }
    
    public String toString() {
        return String.format("GammaAR (%.3f, %,.3f)", getLambda(), getRho());
    }

    /**
     * @return the currentValue
     */
    public double getCurrentValue() {
        return currentValue;
    }

    /**
     * @param currentValue the currentValue to set
     */
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

}
