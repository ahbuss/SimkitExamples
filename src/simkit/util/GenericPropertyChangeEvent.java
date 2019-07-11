package simkit.util;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

/**
 *
 * @author ahbuss
 * @param <T>
 * @param <S>
 */
public class GenericPropertyChangeEvent<T, S> extends PropertyChangeEvent {

    private static final Logger LOGGER = Logger.getLogger(GenericPropertyChangeEvent.class.getName());

    private final T key;

    /**
     *
     * @param key
     * @param source
     * @param propertyName
     * @param oldValue
     * @param newValue
     */
    public GenericPropertyChangeEvent(T key, Object source, String propertyName, S oldValue, S newValue) {
        super(source, propertyName, oldValue, newValue);
        this.key = key;
    }

    /**
     * @return the key
     */
    public T getKey() {
        return key;
    }

}
