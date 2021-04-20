package finite;

import static simkit.Priority.HIGHER;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class CustomerGenerator extends SimEntityBase  {

    private RandomVariate thinkTimeGenerator;
    
    private RandomVariate serviceTimeGenerator;
    
    private int numberCustomers;
    
    private double targetMean;
    
    public CustomerGenerator(RandomVariate thinkTimeGenerator, 
            RandomVariate serviceTimeGenerator,
            int numberCustomers,
            double targetMean) {
        this.setThinkTimeGenerator(thinkTimeGenerator);
        this.setServiceTimeGenerator(serviceTimeGenerator);
        this.setNumberCustomers(numberCustomers);
        this.setTargetMean(targetMean);
    }
    
    public void doRun() {
        waitDelay("Init", 0.0, HIGHER, 0);
    }

    public void doInit(int i) {
        Customer customer = new Customer(serviceTimeGenerator.generate());
        waitDelay("Arrival", thinkTimeGenerator.generate() * numberCustomers* targetMean, customer);
        
        if (i < getNumberCustomers() - 1) {
            waitDelay("Init", 0.0, HIGHER, i + 1);
        }
    }
    
    public void doEndService(Customer customer) {
        customer.setServiceTime(serviceTimeGenerator.generate());
        waitDelay("Arrival", thinkTimeGenerator.generate() * numberCustomers * targetMean, customer);
    }
    
    /**
     * @return the thinkTimeGenerator
     */
    public RandomVariate getThinkTimeGenerator() {
        return thinkTimeGenerator;
    }

    /**
     * @param thinkTimeGenerator the thinkTimeGenerator to set
     */
    public final void setThinkTimeGenerator(RandomVariate thinkTimeGenerator) {
        this.thinkTimeGenerator = thinkTimeGenerator;
    }

    /**
     * @return the serviceTimeGenerator
     */
    public RandomVariate getServiceTimeGenerator() {
        return serviceTimeGenerator;
    }

    /**
     * @param serviceTimeGenerator the serviceTimeGenerator to set
     */
    public final void setServiceTimeGenerator(RandomVariate serviceTimeGenerator) {
        this.serviceTimeGenerator = serviceTimeGenerator;
    }

    /**
     * @return the numberCustomers
     */
    public int getNumberCustomers() {
        return numberCustomers;
    }

    /**
     * @param numberCustomers the numberCustomers to set
     */
    public final void setNumberCustomers(int numberCustomers) {
        if (numberCustomers <= 0) {
            throw new IllegalArgumentException("numberCustomers must be > 0: " + numberCustomers);
        }
        this.numberCustomers = numberCustomers;
    }

    /**
     * @return the targetMean
     */
    public double getTargetMean() {
        return targetMean;
    }

    /**
     * @param targetMean the targetMean to set
     */
    public final void setTargetMean(double targetMean) {
        if (targetMean <= 0.0) {
            throw new IllegalArgumentException("targetMena  must be > 0.0: " + targetMean);
        }
        this.targetMean = targetMean;
    }
    
    
    
}
