package random;

import java.util.logging.Logger;
import simkit.random.RandomNumber;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestResetSeed {

    private static final Logger LOGGER = Logger.getLogger(TestResetSeed.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomNumber rng = RandomVariateFactory.getDefaultRandomNumber();
        
        long seed = 12345L;
        rng.setSeed(seed);
        
        for (int i = 0; i < 10; ++i) {
            System.out.println(rng.drawLong());
        }
        
        rng.setSeed(seed);
        
        System.out.println("-------------");
        for (int i = 0; i < 10; ++i) {
            System.out.println(rng.drawLong());
        }
        
        RandomVariateFactory.getDefaultRandomNumber().setSeed(seed);
        
        System.out.println("-------------");
        for (int i = 0; i < 10; ++i) {
            System.out.println(rng.drawLong());
        }
        
    }

}
