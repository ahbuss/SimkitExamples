package thread;

/**
 *
 * @author ahbuss
 */
public class NumberProcessers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        int availableProcessors = runtime.availableProcessors();
        System.out.printf("%d processors%n", availableProcessors);
    }
    
}
