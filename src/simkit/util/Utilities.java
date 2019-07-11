package simkit.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class Utilities {

    private static final Logger LOGGER = Logger.getLogger(Utilities.class.getName());

    public static Map<String, Method> findStatesAndGetters(Object obj) {
        return findStatesAndGetters(obj.getClass());
    }

    public static Map<String, Method> findStatesAndGetters(Class clazz) {

        Map<String, Method> statesAndGetters = new TreeMap<>();

        try {
            BeanInfo beanInfo;
            if (SimEntityBase.class.isAssignableFrom(clazz)) {
                beanInfo = Introspector.getBeanInfo(clazz, SimEntityBase.class);
            } else {
                beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            }
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                Method setter = descriptor.getWriteMethod();
                if (setter == null) {
                    String propertyName = descriptor.getName();
                    Method getter = descriptor.getReadMethod();
                    if (getter != null && Modifier.isPublic(getter.getModifiers())) {
                        statesAndGetters.put(propertyName, getter);
                    }
                }
            }
        } catch (IntrospectionException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }

        return statesAndGetters;
    }

}
