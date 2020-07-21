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

    private int k;

    private RandomVariate mixtureVariate;

    private double currentValue;

    public GammaARVariate() {
    }

    @Override
    public double generate() {
        currentValue = getRho() * currentValue + mixtureVariate.generate();
        return currentValue;
    }

    @Override
    public void setParameters(Object... params) {
        if (params.length == 2) {
            if (params[0] instanceof Number && params[1] instanceof Number) {
                setLambda(((Number) params[0]).doubleValue());
                setRho(((Number) params[1]).doubleValue());
                setK(2);
                setupMixture();
            } else {
                String message = String.format("parameters must be Number: (%s, %s)",
                        params[0].getClass().getSimpleName(),
                        params[1].getClass().getSimpleName());
                LOGGER.severe(message);
                throw new IllegalArgumentException(message);
            }
        } else if (params.length == 3) {
            if (params[0] instanceof Number && params[1] instanceof Number && params[2] instanceof Number) {
                setLambda(((Number) params[0]).doubleValue());
                setRho(((Number) params[1]).doubleValue());
                setK(((Number) params[2]).intValue());
                setupMixture();
            } else {
                String message = "GammaARVariate requires 2 parameters: " + params.length;
                LOGGER.severe(message);
                throw new IllegalArgumentException(message);
            }
        }
    }

    private void setupMixture() {
        RandomVariate[] all = new RandomVariate[k + 1];
        all[0] = RandomVariateFactory.getInstance("Constant", 0.0);
        all[1] = RandomVariateFactory.getInstance("Exponential", getLambda());
        for (int alpha = 2; alpha <= k; ++alpha) {
            all[alpha] = RandomVariateFactory.getInstance("Gamma", alpha, getLambda());
        }

        double oneMinusRho = 1.0 - rho;
        double[] mixture = new double[all.length];
        for (int i = 0; i <= k; ++i) {
            mixture[i] = binomialCoefficient(k, i) * pow(rho, k - i) * pow(oneMinusRho, i);
        }

        this.mixtureVariate = RandomVariateFactory.getInstance("Mixed", mixture, all);
//        this.currentValue = all[2].generate();
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{getLambda(), getRho()};
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
        return String.format("GammaAR (%.3f, %,.3f, %,d)", getLambda(), getRho(), getK());
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

    /**
     * @return the k
     */
    public int getK() {
        return k;
    }

    /**
     * @param k the k to set
     */
    public void setK(int k) {
        if (k < 1) {
            String message = "k must be ≥ 1: " + k;
            LOGGER.severe(message);
            throw new IllegalArgumentException(message);
        }
        this.k = k;
    }

    public static int binomialCoefficient(int n, int k) {
        if (k == 0 || k == n) {
            return 1;
        }
        return binomialCoefficient(n - 1, k - 1) + binomialCoefficient(n - 1, k);
    }

    /**
     * @return the mixtureVariate
     */
    public RandomVariate getMixtureVariate() {
        return mixtureVariate;
    }

}
