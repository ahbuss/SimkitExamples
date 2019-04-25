package simkit.util;

/**
 *
 * @author ahbuss
 * @param <T>
 * @param <S>
 */
public interface GenericPropertyChangeListener <T, S> {

    /**
     *
     * @param evt
     */
    public void propertyChange(GenericPropertyChangeEvent<T, S> evt );
}
