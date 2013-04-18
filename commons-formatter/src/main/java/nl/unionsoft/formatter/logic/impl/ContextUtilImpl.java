package nl.unionsoft.formatter.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.unionsoft.formatter.logic.ContextUtil;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Class which holds a reference to the
 * {@link org.springframework.context.ApplicationContext}
 * <p/>
 * This class provides a static way to access the
 * {@link org.springframework.context.ApplicationContext} and also provides a way to
 * obtain a named bean from the {@link org.springframework.context.ApplicationContext}.
 */

@Component
public final class ContextUtilImpl implements ContextUtil {

    private ApplicationContext applicationContext;

    public <T> T getBeanByClass(final Class<T> beanType) {
        return getBeanByClass(beanType, applicationContext);
    }

    public static <T> T getBeanByClass(final Class<T> beanType, final ApplicationContext applicationContext) {
        final Map<String, T> beans = applicationContext.getBeansOfType(beanType);

        T result = null;
        if (beans == null || beans.isEmpty()) {
            throw new NoSuchBeanDefinitionException("No bean found of type: " + beanType);
        } else if (beans.size() > 1) {
            for (final T bean : beans.values()) {
                if (bean.getClass().equals(beanType)) {
                    result = (T) bean;
                    break;
                }
            }
        } else {
            final List<T> values = new ArrayList<T>();
            values.addAll(beans.values());
            result = (T) values.get(0);
        }
        return result;
    }

    /**
     * @return the applicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBeanByName(final Class<T> beanType, final String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

}
