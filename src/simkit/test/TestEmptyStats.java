package simkit.test;

import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.stat.SimpleStatsTally;
import simkit.stat.SimpleStatsTimeVarying;

/**
 * This verifies that SimpleStatsTally instances with no observations result
 * in a count of 0 and SimpleStatsTimeVarying with values that never change
 * during a replication will have a variance of 0.0.
 * 
 * @author ahbuss
 */
public class TestEmptyStats extends SimEntityBase {

    public void doRun() {
        firePropertyChange("timeVarying", 10);
        waitDelay("Foo", 20.0);
    }
    
    public void doFoo() {
        firePropertyChange("timeVarying", 10);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleStatsTally sst = new SimpleStatsTally("tally");
        SimpleStatsTimeVarying sstv = new SimpleStatsTimeVarying("timeVarying");
        TestEmptyStats testEmptyStats = new TestEmptyStats();
        
        testEmptyStats.addPropertyChangeListener(sstv);
        testEmptyStats.addPropertyChangeListener(sst);
        
        Schedule.setVerbose(true);
        Schedule.reset();
        Schedule.startSimulation();
        System.out.println(sst);
        System.out.println(sstv);
        
        System.out.printf("timeVaringStat.variance == 0.0 %b?%n", sstv.getVariance() == 0.0);
        System.out.printf("tallyStat.count == 0 %b?%n", sst.getCount()== 0);
        
    }
    
}
