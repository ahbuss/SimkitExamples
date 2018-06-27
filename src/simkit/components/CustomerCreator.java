package simkit.components;

import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class CustomerCreator extends ArrivalProcess {

    private RandomVariate serviceTimeGenerator;
    
    public CustomerCreator() {
        super();
    }
    
    public CustomerCreator(RandomVariate interarrivalTimeGenerator,
            RandomVariate serviceTimeGenerator) {
        this();
        setInterarrivalTimeGenerator(interarrivalTimeGenerator);
        setServiceTimeGenerator(serviceTimeGenerator);
    }

    @Override
    public void doArrival() {
        super.doArrival();
        Customer customer = new Customer(serviceTimeGenerator.generate());
        waitDelay("Arrival", 0.0, customer);
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
    public void setServiceTimeGenerator(RandomVariate serviceTimeGenerator) {
        this.serviceTimeGenerator = serviceTimeGenerator;
    }
    
}
