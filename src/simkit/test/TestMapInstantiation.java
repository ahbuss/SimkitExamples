package simkit.test;

import java.util.HashMap;
import java.util.Map;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestMapInstantiation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("mean", 2.4);
        RandomVariate rv = RandomVariateFactory.getInstance("Exponential", params);
        System.out.println(rv);
        
        params.clear();
        params.put("alpha", 3.4);
        params.put("beta", 5.6);
        rv = RandomVariateFactory.getInstance("Gamma", params);
        System.out.println(rv);
    }

}
