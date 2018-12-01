package testactivity;

import simkit.Adapter;
import simkit.Schedule;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestNetwork {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        Provider provider = new Provider();
        ActivityNetwork network = new ActivityNetwork();
        
        Adapter consumerToNetwork = new Adapter();
        consumerToNetwork.setHeardEvent("RequestReSupply");
        consumerToNetwork.setPassedEvent("Start");
        consumerToNetwork.addConnect(consumer, network);
        
        Adapter networkToProvider = new Adapter();
        networkToProvider.setHeardEvent("End");
        networkToProvider.setPassedEvent("ReceiveRequest");
        networkToProvider.addConnect(network, provider);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        consumer.addPropertyChangeListener(simplePropertyDumper);
        provider.addPropertyChangeListener(simplePropertyDumper);
        network.addPropertyChangeListener(simplePropertyDumper);
        
        Schedule.setVerbose(true);
        
        Schedule.reset();
        Schedule.startSimulation();
    }

}
