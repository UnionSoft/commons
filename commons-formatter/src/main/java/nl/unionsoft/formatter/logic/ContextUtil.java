package nl.unionsoft.formatter.logic;

import org.springframework.context.ApplicationContext;

public interface ContextUtil {
    public ApplicationContext getApplicationContext();

    public void setApplicationContext(final ApplicationContext applicationContext);

    public <T> T getBeanByName(final Class<T> beanType, final String beanName);

    public <T> T getBeanByClass(final Class<T> beanType);

}
