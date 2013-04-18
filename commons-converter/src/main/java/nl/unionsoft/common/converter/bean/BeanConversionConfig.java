package nl.unionsoft.common.converter.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.unionsoft.common.converter.Converter;

@SuppressWarnings({ "rawtypes" })
public class BeanConversionConfig {
    private List<PropertyMapping> propertyMappings;
    private Class<?> targetClass;

    public BeanConversionConfig(Class<?> targetClass, String[] sourceProperties, String[] targetProperties, Map<String, Converter> converterMappings) {
        this.targetClass = targetClass;
        propertyMappings = new ArrayList<PropertyMapping>();
        for (int i = 0; i < sourceProperties.length; i++) {
            PropertyMapping propertyMapping = new PropertyMapping();
            propertyMapping.setSourceName(sourceProperties[i]);
            propertyMapping.setTargetName(targetProperties[i]);
            if (converterMappings != null) {
                propertyMapping.setConverter(converterMappings.get(sourceProperties[i]));
            }
            propertyMappings.add(propertyMapping);
        }

    }

    public BeanConversionConfig(Class<?> targetClass, String[] properties) {
        this(targetClass, properties, null);
    }

    public BeanConversionConfig(Class<?> targetClass, String[] properties, Map<String, Converter> converterMappings) {
        this(targetClass, properties, properties, converterMappings);
    }

    public List<PropertyMapping> getPropertyMappings() {
        return propertyMappings;
    }

    public void setPropertyMappings(List<PropertyMapping> propertyMappings) {
        this.propertyMappings = propertyMappings;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public class PropertyMapping {
        private String sourceName;
        private String targetName;
        private Converter converter;

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public String getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }

        public Converter getConverter() {
            return converter;
        }

        public void setConverter(Converter converter) {
            this.converter = converter;
        }

    }

}
