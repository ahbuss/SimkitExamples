package simkit.test;

import simkit.Schedule;
import simkit.components.EntityCreator;
import simkit.components.EntityServer;
import simkit.random.RandomVariateFactory;
import simkit.stat.CollectionSizeTimeVaryingStats;
import simkit.stat.SimpleStatsTally;

/**
 *
 * @author ahbuss
 */
public class MM1Queue {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityCreator entityCreator = new EntityCreator(RandomVariateFactory.getInstance("Exponential", 1.7));
        EntityServer entityServer = new EntityServer(1, RandomVariateFactory.getInstance("Exponential", 1.3));
        entityCreator.addSimEventListener(entityServer);
        
        SimpleStatsTally delayInQueueStat = new SimpleStatsTally("delayInQueue");
        entityServer.addPropertyChangeListener(delayInQueueStat);
        SimpleStatsTally timeInSystemStat = new SimpleStatsTally("timeInSystem");
        entityServer.addPropertyChangeListener(timeInSystemStat);
        
        CollectionSizeTimeVaryingStats numberInQueueStat = new CollectionSizeTimeVaryingStats("queue");
        entityServer.addPropertyChangeListener(numberInQueueStat);
        
        double stopTime = 10000000.0;
        Schedule.stopAtTime(stopTime);
        
        Schedule.reset();
        Schedule.startSimulation();
        
        System.out.println(timeInSystemStat);
        System.out.println(delayInQueueStat);
        System.out.println(numberInQueueStat);
    }

}
