package random;

import java.util.Map;
import java.util.TreeMap;
import simkit.random.RandomVariate;
import simkit.util.ClassFinder;

/**
 *
 * @author ahbuss
 */
public class TestClassFinder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, Class<? extends RandomVariate>> rvs = 
                new TreeMap<>(ClassFinder.getINSTANCE().getRandomVariateClasses());
        for (String name: rvs.keySet()) {
            System.out.printf("%s = %s%n", name, rvs.get(name).getSimpleName());
        }
    }

}
