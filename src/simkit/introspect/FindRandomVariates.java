package simkit.introspect;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import simkit.random.DiscreteRandomVariate;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class FindRandomVariates {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, Class<? extends RandomVariate>> classes = simkit.util.ClassFinder.getINSTANCE().getRandomVariateClasses();
        Set<String> randomVariates = new TreeSet<>();
        Set<String> discreteVariates = new TreeSet<>();
        for (String name : classes.keySet()) {
            if (!name.contains(".") && !name.contains("_64")) {
                if (!name.contains("Variate")) {
                    Class<? extends RandomVariate> clazz = classes.get(name);
                    if (!clazz.isInterface()) {
                        if (DiscreteRandomVariate.class.isAssignableFrom(clazz)) {
                            discreteVariates.add(name);
                        } else {
                            randomVariates.add(name);
                        }
                    }
                }
            }
        }
        System.out.println("RandomVariates:\n============");
        for (String name : randomVariates) {
            System.out.println(name);
        }
        System.out.println("\nDiscrete Variates:\n==========");
        for (String name : discreteVariates) {
            System.out.println(name);
        }
        
    }

}
