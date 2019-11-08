package random;

import simkit.random.GammaVariate;

/**
 *
 * @author ahbuss
 */
public class Gamma2Variate extends GammaVariate {

    public void setMeanAndVariance(double mean, double variance) {
        if (mean <= 0.0 || variance <= 0.0) {
            throw new IllegalArgumentException(String.format("Mean and variance must be > 0.0; mean=%f, "
                    + "variance=%f", mean, variance));
        }
        this.setAlpha(mean * mean / variance);
        this.setBeta(variance / mean);
    }

    @Override
    public void setParameters(Object... params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("Gamma2Variate requires 2 arguments: " + params.length);
        } else if (params[0] instanceof Number && params[1] instanceof Number) {
            double mean = ((Number) params[0]).doubleValue();
            double variance = ((Number) params[1]).doubleValue();
            setMeanAndVariance(mean, variance);
        } else {
            throw new IllegalArgumentException(String.format("Parameters must be numeric: "
                    + " (%s, %s)", params[0].getClass().getSimpleName(), params[1].getClass().getSimpleName()));
        }

    }

}
