package simkit.util;

/**
 *
 * @author ahbuss
 * @param <T> A key that is used to distinguish GenericPropertyChangeEvents
 * @param <S> The type of object in new/old values in a GenericPropertyChangeEvent
 */
public interface GenericPropertyChangeSource <T, S> {
    
    public void addPropertyChangeListener(GenericPropertyChangeListener<T, S> listener);
    
    public void addPropertyChangeListener(String property, GenericPropertyChangeListener<T, S> listener);
    
    public void removePropertyChangeListener(GenericPropertyChangeListener<T, S> listener);
    
    public void firePropertyChange(GenericPropertyChangeEvent<T, S> evt);
    
    public void firePropertyChange(T key, Object source, String propertyName, S oldValue, S newValue);
}
