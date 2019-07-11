package simkit.test;

import java.lang.reflect.Method;
import java.util.Map;
import simkit.components.ServerWithReneges;
import simkit.util.Utilities;

/**
 *
 * @author ahbuss
 */
public class TestStatesAndGetters {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, Method> statesAndGetters = Utilities.findStatesAndGetters(ServerWithReneges.class);
        for (String state: statesAndGetters.keySet()) {
            System.out.printf("%s: %s%n", state, statesAndGetters.get(state));
        }
    }
    
}
