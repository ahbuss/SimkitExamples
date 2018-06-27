package simkit.components;

import simkit.Entity;

/**
 *
 * @author ahbuss
 */
public class ServerWithEfficiencies2 extends ExplicitServerComponent {

    public ServerWithEfficiencies2() {
        super();
    }

    public ServerWithEfficiencies2(Entity[] allServers) {
        this();
        this.setAllServers(allServers);
    }

    @Override
    public void doStartService() {
        Customer customer = queue.first();

        delayInQueue = customer.getElapsedTime();
        firePropertyChange("delayInQueue", getDelayInQueue());

        queue.remove(customer);
        firePropertyChange("queue", getQueue());

        Entity server = availableServers.iterator().next();
        availableServers.remove(server);
        firePropertyChange("availableServers", getAvailableServers());

        fireIndexedPropertyChange(server.getID(), "busy", 1.0);

        double nominalServiceTime = customer.getServiceTime();
        double efficiency = ((Server) server).getEfficiency();

        waitDelay("EndService", nominalServiceTime * efficiency, server, customer);
    }
    
    /**
     * 
     * @param servers Given array of Servers
     * @throws IllegalArgumentException if elements are not all instances of Server
     */
    @Override
    public void setAllServers(Entity[] servers) {
        for (Entity entity: servers) {
            if (!(entity instanceof Server)) {
                throw new IllegalArgumentException("Entities must be instances of Server: " +
                        entity.getClass().getSimpleName());
            }
        }
        super.setAllServers(servers);
    }
}
