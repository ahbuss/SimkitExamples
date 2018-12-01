package priority;

import simkit.Entity;

/**
 *
 * @author ahbuss
 */
public class PriorityEntity extends Entity{

    private final int priority;
    
    public PriorityEntity(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Entity other) {
        int ret = 0;
        if (other instanceof PriorityEntity) {
            PriorityEntity otherEntity = (PriorityEntity) other;
            if (otherEntity.getPriority() == this.getPriority()) {
                return super.compareTo(other);
            } else {
                return this.getPriority() - otherEntity.getPriority();
            }
        } else {
            throw new RuntimeException("Need PriorityEntity, found " + other.getClass().getSimpleName());
        }
    }
    
    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }
    
    @Override
    public String toString() {
        return super.toString().concat(" ".concat(Integer.toString(priority)));
    }
}
