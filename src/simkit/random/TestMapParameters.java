package simkit.random;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ahbuss
 */
public class TestMapParameters {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("mean", 3.2);
        params.put("standardDeviation", 1.1);
        
        RandomVariate rv = RandomVariateFactory.getInstance("Normal", params);
        System.out.println(rv);
        
        params.clear();
        
        double[] values = new double[] {2, 4, 5, 6, 7,};
        params.put("traceValues", values);
        rv = RandomVariateFactory.getInstance("Trace", params);
        System.out.println(rv);
        
    }
    
}
