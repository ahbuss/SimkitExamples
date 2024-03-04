package misctests;

import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class TwoArgs extends SimEntityBase {

    private int a;
    
    private double b;
    
    public void doA() {
        waitDelay("B", 0.0, a, b);
    }
    
    public void doB(int i, double j) {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
