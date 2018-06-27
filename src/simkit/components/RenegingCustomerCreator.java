package simkit.components;

import simkit.random.RandomVariate;

/**
 * @version $Id: CustomerArrivalProcess.java 1974 2015-05-16 01:10:11Z ahbuss $
 * @author ahbuss
 */
public class RenegingCustomerCreator extends ArrivalProcess {

    private RandomVariate renegeTimeGenerator;

    public RenegingCustomerCreator() { }
    
    public RenegingCustomerCreator(RandomVariate interarrivaltimeGenerator,
            RandomVariate renegeTimeGenerator) {
        super(interarrivaltimeGenerator);
        this.setRenegeTimeGenerator(renegeTimeGenerator);
    }
    
    @Override
    public void doArrival() {
        super.doArrival();
        RenegingCustomer customer = new RenegingCustomer(renegeTimeGenerator.generate());
        waitDelay("Arrival", 0.0, customer);
    }
    
    public void doArrival(RenegingCustomer customer) { }
    
    public RandomVariate getRenegeTimeGenerator() {
        return renegeTimeGenerator;
    }

    public void setRenegeTimeGenerator(RandomVariate renegetimeGenerator) {
        this.renegeTimeGenerator = renegetimeGenerator;
    }
    
}
