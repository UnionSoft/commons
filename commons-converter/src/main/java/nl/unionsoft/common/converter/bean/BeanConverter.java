package nl.unionsoft.common.converter.bean;

import java.lang.reflect.InvocationTargetException;

import nl.unionsoft.common.converter.Converter;
import nl.unionsoft.common.converter.ConverterWithConfig;
import nl.unionsoft.common.converter.bean.BeanConversionConfig.PropertyMapping;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanConverter implements ConverterWithConfig<Object, Object, BeanConversionConfig> {

    @Override
    public Object convert(Object source, BeanConversionConfig configObject) {
        Object target = null;
        if (source != null) {
            try {
                target = configObject.getTargetClass().newInstance();
                for (PropertyMapping propertyMapping : configObject.getPropertyMappings()) {
                    setSourceOnTarget(source, target, propertyMapping);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return target;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setSourceOnTarget(Object source, Object target, PropertyMapping propertyMapping) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String sourcePropertyName = propertyMapping.getSourceName();
        Converter converter = propertyMapping.getConverter();
        Object sourceProperty = PropertyUtils.getProperty(source, sourcePropertyName);
        Object targetProperty = null;
        if (converter == null) {
            targetProperty = sourceProperty;
        } else {
            targetProperty = converter.convert(sourceProperty);
        }
        PropertyUtils.setProperty(target, propertyMapping.getTargetName(), targetProperty);
    }

}
