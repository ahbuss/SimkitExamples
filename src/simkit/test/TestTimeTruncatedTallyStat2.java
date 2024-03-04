package simkit.test;

import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.stat.TimeTruncatedTallyStat;

/**
 *
 * @author ahbuss
 */
public class TestTimeTruncatedTallyStat2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int newID = Schedule.addNewEventList();
        
        double warmup = 100.0;
        double steadyState = 1000.0;
        
        Pinger pinger = new Pinger(1.0);
        pinger.setEventListID(newID);
        
        TimeTruncatedTallyStat stat = new TimeTruncatedTallyStat("ping", warmup);
        stat.setEventList(Schedule.getEventList(newID));
        
        pinger.addPropertyChangeListener("ping", stat);
        System.out.println(pinger);
        
        Schedule.getEventList(newID).stopAtTime(warmup + steadyState);
        
        Schedule.getEventList(newID).reset();
        Schedule.getEventList(newID).startSimulation();
        
        System.out.println(stat.getStatsAtTruncation());
        System.out.println(stat);
    }
    
    public static class Pinger extends SimEntityBase {
        
        private double interPingTime;
        
        public Pinger() {}
        
        public Pinger(double interPingTime) {
            this.setInterPingTime(interPingTime);
        }

        public void doRun() {
            waitDelay("Ping", getInterPingTime());
        }
        
        public void doPing() {
            firePropertyChange("ping", 1.0);
            waitDelay("Ping", getInterPingTime());
        }
        
        /**
         * @return the interPingTime
         */
        public double getInterPingTime() {
            return interPingTime;
        }

        /**
         * @param interPingTime the interPingTime to set
         */
        public void setInterPingTime(double interPingTime) {
            if (interPingTime <= 0.0) {
                throw new IllegalArgumentException("interPingTime must be > 0.0: " + interPingTime);
            }
            this.interPingTime = interPingTime;
        }
    }
    
}
