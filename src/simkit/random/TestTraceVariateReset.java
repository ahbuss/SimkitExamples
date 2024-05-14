package simkit.random;

/**
 *
 * @author ahbuss
 */
public class TestTraceVariateReset {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] values = new double[] {2,4,5,6};
        RandomVariate trace = RandomVariateFactory.getInstance("Trace", new Object[]{values});
        System.out.println(trace);
        
        for (int i = 0; i < 5; ++i) {
            System.out.println(trace.generate());
        }
        
        trace.getRandomNumber().resetSeed();
        System.out.println("After reset");
        for (int i = 0; i < 5; ++i) {
            System.out.println(trace.generate());
        }
        
        int[] intValues = new int[]{5, 4, 3, 2, 1};
        trace = RandomVariateFactory.getInstance("IntegerTrace", new Object[]{intValues});
        
        System.out.println(trace);
        for (int i = 0; i < 6; ++i) {
            System.out.println(trace.generate());
        }
        
        trace.getRandomNumber().resetSeed();
        System.out.println("After reset");
        for (int i = 0; i < 6; ++i) {
            System.out.println(trace.generate());
        }
    }
    
}
