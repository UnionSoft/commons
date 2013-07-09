package nl.unionsoft.common.param;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamContextLogicImpl {

    private static final Logger LOG = LoggerFactory.getLogger(ParamContextLogicImpl.class);

    public List<Context> getContext(final Class<?> theClass) {
        List<Context> results = new ArrayList<Context>();
        for (PropertyDescriptor propertyDescriptor : PropertyUtils.getPropertyDescriptors(theClass)) {
            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod != null && readMethod != null) {
                Param param = readMethod.getAnnotation(Param.class);
                if (param != null) {
                    Context context = new Context();
                    String name = propertyDescriptor.getName();
                    context.setId(name);
                    StringBuilder titleBuilder = new StringBuilder();
                    String title = param.title();
                    if (StringUtils.isEmpty(title)) {
                        titleBuilder.append(theClass.getSimpleName());
                        titleBuilder.append('.');
                        titleBuilder.append(name);
                        titleBuilder.append(".title");
                    } else {
                        titleBuilder.append(title);
                    }
                    context.setTitle(titleBuilder.toString());
                    results.add(context);
                }
            }
        }
        // Collections.sort(params, new BeanComparator("order", new NullComparator(ComparableComparator.getInstance())));
        return results;
    }

    public void setBeanValues(final List<Context> contextList, final Object bean, final Map<String, Object> beanValues) {
        for (Context context : contextList) {
            Object beanValue = beanValues.get(context.getId());
            setBeanValue(context, bean, beanValue);
        }
    }

    private void setBeanValue(final Context context, final Object bean, final Object beanValue) {
        String id = context.getId();
        LOG.info("Handing context with id: {}", id);
        try {
            PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(bean, context.getId());
            if (propertyDescriptor == null) {
                LOG.warn("No propertyDescritor found for id '{}'", id);
            } else {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (writeMethod != null) {
                    writeMethod.invoke(bean, new Object[] { beanValue });
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setBeanValues(final Object bean, final Map<String, Object> beanValues) {
        if (bean != null) {
            List<Context> contextList = getContext(bean.getClass());
            setBeanValues(contextList, bean, beanValues);
        }
    }

}
