package simkit.random;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.stat.SimpleStatsTally;

/**
 *
 * @author ahbuss
 */
public class TestMarkovChainVariate {

    private static final Logger LOGGER = Logger.getLogger(TestMarkovChainVariate.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[][] matrix = new double[][]{
            new double[]{10, 1, 0, 0},
            new double[]{1, 10, 1, 0},
            new double[]{0, 1, 10, 1},
            new double[]{0, 0, 1, 10}
        };

        MarkovChainVariate rv
                = (MarkovChainVariate) RandomVariateFactory.getDiscreteRandomVariateInstance("MarkovChain",
                        new Object[]{matrix});
        
        SimpleStatsTally sst = new SimpleStatsTally(rv.toString());

        int number = 1000000;

        File outputFile = new File("MarkovChain.csv");
        FileWriter fileWriter = null;
        CSVWriter csvWriter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(new String[]{rv.toString()});

            for (int i = 0; i < number; ++i) {
                double x = rv.generate();
                sst.newObservation(x);
                csvWriter.writeNext(new String[]{Double.toString(x)});
            }

            System.out.println(sst);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (csvWriter != null) {
                try {
                    csvWriter.close();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
