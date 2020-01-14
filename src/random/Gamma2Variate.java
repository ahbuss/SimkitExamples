package random;

import simkit.random.GammaVariate;

/**
 *
 * @author ahbuss
 */
public class Gamma2Variate extends GammaVariate {

    private double mean;
    private double variance;
    
    public void setMeanAndVariance(double mean, double variance) {
        if (mean <= 0.0 || variance <= 0.0) {
            throw new IllegalArgumentException(String.format("Mean and variance must be > 0.0; mean=%f, "
                    + "variance=%f", mean, variance));
        }
        this.mean = mean;
        this.variance = variance;
        this.setAlpha(mean * mean / variance);
        this.setBeta(variance / mean);
    }

    @Override
    public void setParameters(Object... params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("Gamma2Variate requires 2 arguments: " + params.length);
        } else if (params[0] instanceof Number && params[1] instanceof Number) {
            setMeanAndVariance(((Number) params[0]).doubleValue(), ((Number) params[1]).doubleValue());
        } else {
            throw new IllegalArgumentException(String.format("Parameters must be numeric: "
                    + " (%s, %s)", params[0].getClass().getSimpleName(), params[1].getClass().getSimpleName()));
        }

    }
    
    @Override
    public String toString() {
        return String.format("Gamma2 (%,.3f, %,.3f)", mean, variance);
    }

}
