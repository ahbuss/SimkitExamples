package thread;

import java.util.logging.Level;
import java.util.logging.Logger;
import static thread.Executor.EXECUTORS;

/**
 *
 * @author ahbuss
 */
public class SimRunner implements Runnable {
    
    private static final Logger LOGGER = Logger.getLogger(SimRunner.class.getName());

    private static int NEXT_ID = 0;
    
    private int id;
    
    public SimRunner() {
        this.id = ++NEXT_ID;
    }
    
    @Override
    public void run() {
        try {
            Executor executor = EXECUTORS.take();
            LOGGER.log(Level.INFO, "Starting SimRunner {2} on Thread {0} and eventListID {1}", 
                    new Object[]{Thread.currentThread().getId(), executor.getEventListID(), id});
            executor.execute(id);
            EXECUTORS.put(executor);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
