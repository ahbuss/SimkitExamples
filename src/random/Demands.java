package random;

import simkit.SimEntityBase;
import simkit.random.DiscreteRandomVariate;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class Demands extends SimEntityBase  {

    private RandomVariate interdemandTimeGenerator;

    private DiscreteRandomVariate demandGenerator;
    
    private double truncationTime;

    protected int numberDemands;

    /**
     *
     * @param interdemandTimeGenerator
     * @param demandGenerator
     */
    public Demands(RandomVariate interdemandTimeGenerator, 
            DiscreteRandomVariate demandGenerator,
            double truncationTime) {
        this.setDemandGenerator(demandGenerator);
        this.setInterdemandTimeGenerator(interdemandTimeGenerator);
        this.setTruncationTime(truncationTime);
    }

    public void reset() {
        super.reset();
        this.numberDemands = 0;
    }
    
    public void doRun() {
        firePropertyChange("numberDemands", getNumberDemands());
        
        waitDelay("Truncate", getTruncationTime());
        
        waitDelay("Demand", interdemandTimeGenerator);
    }
    
    public void doTruncate() {
        this.numberDemands = 0;
        firePropertyChange("numberDemands", getNumberDemands());
    }
    
    public void doDemand() {
        this.numberDemands += demandGenerator.generateInt();
        firePropertyChange("numberDemands", getNumberDemands());
        
        waitDelay("Demand", interdemandTimeGenerator);
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

}
