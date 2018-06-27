package simkit.components;

import simkit.Entity;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class EntityCreator extends ArrivalProcess {

    public EntityCreator() {
        super();
    }
    
    public EntityCreator(RandomVariate interarrivalTimeGenerator) {
        this();
        this.setInterarrivalTimeGenerator(interarrivalTimeGenerator);
    }
    
    @Override
    public void doArrival() {
        super.doArrival();
        Entity entity = new Entity();
        waitDelay("Arrival", 0.0, entity);
    }
        
}
