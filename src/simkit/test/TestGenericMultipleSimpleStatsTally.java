package simkit.test;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;
import simkit.random.DiscreteRandomVariate;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.GenericMultipleSimpleStatsTally;
import simkit.util.GenericPropertyChangeEvent;
import simkit.util.GenericPropertyChangeListener;
import simkit.util.GenericPropertyChangeSource;

/**
 *
 * @author ahbuss
 */
public class TestGenericMultipleSimpleStatsTally {

    private static final Logger LOGGER = Logger.getLogger(TestGenericMultipleSimpleStatsTally.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DiscreteRandomVariate keyGen = RandomVariateFactory.getDiscreteRandomVariateInstance("DiscreteUniform", 0, Beatles.values().length - 1);
        RandomVariate valueGen = RandomVariateFactory.getInstance("Normal", 3.4, 1.2);
        String property = "beatle";
        GenericMultipleSimpleStatsTally<Beatles> stat = new GenericMultipleSimpleStatsTally<>(property);

        Source<Beatles> source = new Source<>(property);
        source.addPropertyChangeListener(stat);

        int number = 10000;

        for (int i = 1; i <= number; ++i) {
            Beatles key = Beatles.values()[keyGen.generateInt()];
            double value = valueGen.generate() * i;
            source.firePropertyChange(key, source, property, value, value);
        }
        System.out.println(stat);

        property = "stones";
        GenericMultipleSimpleStatsTally<Stones> stat2 = new GenericMultipleSimpleStatsTally<>(property);
        
        Source<Stones> source2 = new Source<>(property);
        source2.addPropertyChangeListener(stat2);
        for (int i = 1; i <= number; ++i) {
            Stones key = Stones.values()[keyGen.generateInt()];
            double value = valueGen.generate() * i;
            source2.firePropertyChange(key, source, property, value, value);
        }
        System.out.println(stat2);

    }

    private static enum Beatles {
        GEORGE, PAUL, JOHN, RINGO, PETE
    }

    private static enum Stones {
        BRIAN, MICK, KEITH, CHARLIE, BILL, RON
    }

    private static class Source <T> implements GenericPropertyChangeSource<T, Number> {

        private final Set<GenericPropertyChangeListener<T, Number>> listeners;

        private final String property;

        public Source(String property) {
            this.property = property;
            this.listeners = new LinkedHashSet<>();
        }

        @Override
        public void addPropertyChangeListener(GenericPropertyChangeListener<T, Number> listener) {
            this.listeners.add(listener);
        }

        @Override
        public void addPropertyChangeListener(String property, GenericPropertyChangeListener<T, Number> listener) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removePropertyChangeListener(GenericPropertyChangeListener<T, Number> listener) {
            listeners.remove(listener);
        }

        @Override
        public void firePropertyChange(GenericPropertyChangeEvent<T, Number> evt) {
            for (GenericPropertyChangeListener<T, Number> listener : listeners) {
                listener.propertyChange(evt);
            }
        }

        @Override
        public void firePropertyChange(T key, Object source, String propertyName, Number oldValue, Number newValue) {
            firePropertyChange(new GenericPropertyChangeEvent<>(key, source, propertyName,
                    oldValue, newValue));
        }

    }

}
