package random;

import simkit.SimEntityBase;
import simkit.random.DiscreteRandomVariate;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class CorrelatedDemands extends SimEntityBase {

    private RandomVariate interdemandTimeGenerator;

    private DiscreteRandomVariate demandGenerator;

    private double truncationTime;
    
    private double multiplier;

    protected int numberDemands;

    /**
     *
     * @param interdemandTimeGenerator
     * @param demandGenerator
     * @param truncationTime
     */
    public CorrelatedDemands(RandomVariate interdemandTimeGenerator,
            DiscreteRandomVariate demandGenerator,
            double multiplier,
            double truncationTime) {
        this.setDemandGenerator(demandGenerator);
        this.setInterdemandTimeGenerator(interdemandTimeGenerator);
        this.setMultiplier(multiplier);
        this.setTruncationTime(truncationTime);
    }

    public void reset() {
        super.reset();
        this.numberDemands = 0;
    }

    public void doRun() {
        firePropertyChange("numberDemands", getNumberDemands());

        waitDelay("Truncate", getTruncationTime());

        double nextDemandTime = interdemandTimeGenerator.generate();
        demandGenerator.setParameters(nextDemandTime * multiplier);

        waitDelay("Demand", nextDemandTime);
    }

    public void doTruncate() {
        this.numberDemands = 0;
        firePropertyChange("numberDemands", getNumberDemands());
    }

    public void doDemand() {
        this.numberDemands += demandGenerator.generateInt();
        firePropertyChange("numberDemands", getNumberDemands());

        double nextDemandTime = interdemandTimeGenerator.generate();
        demandGenerator.setParameters(nextDemandTime * multiplier);

        waitDelay("Demand", nextDemandTime);
    }

    /**
     * @return the interdemandDTimeGenerator
     */
    public RandomVariate getInterdemandTimeGenerator() {
        return interdemandTimeGenerator;
    }

    /**
     * @param interdemandTimeGenerator the interdemandTimeGenerator to set
     */
    public final void setInterdemandTimeGenerator(RandomVariate interdemandTimeGenerator) {
        this.interdemandTimeGenerator = interdemandTimeGenerator;
    }

    /**
     * @return the demandGenerator
     */
    public DiscreteRandomVariate getDemandGenerator() {
        return demandGenerator;
    }

    /**
     * @param demandGenerator the demandGenerator to set
     */
    public final void setDemandGenerator(DiscreteRandomVariate demandGenerator) {
        this.demandGenerator = demandGenerator;
    }

    /**
     * @return the numberDemands
     */
    public int getNumberDemands() {
        return numberDemands;
    }

    /**
     * @return the truncationTime
     */
    public double getTruncationTime() {
        return truncationTime;
    }

    /**
     * @param truncationTime the truncationTime to set
     */
    public void setTruncationTime(double truncationTime) {
        this.truncationTime = truncationTime;
    }

    /**
     * @return the multiplier
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * @param multiplier the multiplier to set
     */
    public final void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

}
