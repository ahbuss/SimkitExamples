package finite;

import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.SimpleStatsTally;
import simkit.stat.SimpleStatsTimeVarying;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class RunFiniteCallingPopulation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numberCustomers = 10;
        RandomVariate thinkTimeGenerator = RandomVariateFactory.getInstance("Uniform", 0.0, 1.0);
        RandomVariate serviceTimeGenerator = RandomVariateFactory.getInstance("Exponential", 1.6);

        double targetMean = 4.0;
        CustomerGenerator customerGenerator
                = new CustomerGenerator(thinkTimeGenerator, serviceTimeGenerator, numberCustomers, targetMean);
        System.out.println(customerGenerator);

        int numberServers = 1;

        Server server = new Server(numberServers);
        System.out.println(server);

        customerGenerator.addSimEventListener(server);
        server.addSimEventListener(customerGenerator);

        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper();
//        server.addPropertyChangeListener(simplePropertyDumper);

        SimpleStatsTally delayInQueueStat = new SimpleStatsTally("delayInQueue");
        server.addPropertyChangeListener(delayInQueueStat);

        SimpleStatsTimeVarying numberAvailableServersStat = new SimpleStatsTimeVarying("numberAvailableServers");
        server.addPropertyChangeListener(numberAvailableServersStat);
//        Schedule.setVerbose(true);
        Schedule.stopOnEvent(20000, "EndService", Customer.class);

        SimpleStatsTally outerDelayInQueueStat = new SimpleStatsTally("Outer Delay");
        SimpleStatsTally outerUtilization = new SimpleStatsTally("Outer Utilization");

        ArrivalCounter arrivalCounter = new ArrivalCounter();
        customerGenerator.addSimEventListener(arrivalCounter);

        SimpleStatsTally interarrivalTimeStat = new SimpleStatsTally("interarrivalTime");
        arrivalCounter.addPropertyChangeListener(interarrivalTimeStat);

        SimpleStatsTally outerInterarrivalTimeStat = new SimpleStatsTally("outer interarrivalTime");
//        Schedule.reset();
//        Schedule.startSimulation();
//        
//        System.out.println(interarrivalTimeStat);
//
//        if (true) {
//            return;
//        }
        int numberCust = 100000;
        int numberReplications = 50;
        System.out.println("#_customers Avg_Delay Utilization");

        for (int num = numberServers; num < numberServers + numberCust; num += 1000) {
            outerDelayInQueueStat.reset();
            outerInterarrivalTimeStat.reset();
            outerUtilization.reset();
            for (int rep = 1; rep <= numberReplications; ++rep) {
                customerGenerator.setNumberCustomers(num);
                Schedule.reset();
                interarrivalTimeStat.reset();
                delayInQueueStat.reset();
                numberAvailableServersStat.reset();
                Schedule.startSimulation();
                double utilization = 1.0 - numberAvailableServersStat.getMean() / server.getTotalNumberServers();

                outerDelayInQueueStat.newObservation(delayInQueueStat.getMean());
                outerUtilization.newObservation(utilization);
                outerInterarrivalTimeStat.newObservation(interarrivalTimeStat.getMean());
            }

//        System.out.printf("Simulation ended at time %,.2f%n", Schedule.getSimTime());
            System.out.printf("   %,d          %,.4f\t%,.4f\t%,.4f%n",
                    customerGenerator.getNumberCustomers(), outerDelayInQueueStat.getMean(),
                    outerUtilization.getMean(), outerInterarrivalTimeStat.getMean());

//                System.out.printf("%,d: interarrival time %,.4f%n", num, outerInterarrivalTimeStat.getMean());
        }
    }

    public static class ArrivalCounter extends SimEntityBase {

        protected double lastTime;

        public void reset() {
            super.reset();
            lastTime = Schedule.getSimTime();
        }

        public void doArrival(Customer customer) {
            double thisTime = Schedule.getSimTime();
            firePropertyChange("interarrivalTime", thisTime - lastTime);
            lastTime = thisTime;
        }
    }

}
