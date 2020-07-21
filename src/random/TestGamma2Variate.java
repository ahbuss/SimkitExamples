package random;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestGamma2Variate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Not needed as of version 1.5.2
//        RandomVariateFactory.addSearchPackage("random");
        RandomVariate rv = RandomVariateFactory.getInstance("Gamma2", 1.0, 1.0);
        System.out.println(rv);
        rv.setParameters(2.0, 3.0);
        System.out.println(rv);
        
        RandomVariate rv2 = RandomVariateFactory.getInstance(rv.toString());
        System.out.println(rv2);
        
    }
    
    public static void setMeanAndVarianceOn(RandomVariate rv, double mean, double variance) {
        Class<? extends RandomVariate> clazz = rv.getClass();
        try {
            Method method = clazz.getMethod("setMeanAndVariance", double.class, double.class);
            if (method != null) {
                method.invoke(rv, mean, variance);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(TestGamma2Variate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
