package simkit.stat;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahbuss
 */
public class TestStudentT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String outputFileName = args.length > 0 ? args[0] : "studentt.csv";
        
        double alpha = 0.05;
        double p = 1.0 - 0.5 * alpha;
        try {
            PrintStream out = new PrintStream(outputFileName);
            
            for (int i = 1; i <= 200; ++i) {
                out.printf("%f,%f%n", StudentT.getQuantile(p, i),
                        StudentT.getQuantile2(p, i));
            }
            
            out.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestStudentT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
