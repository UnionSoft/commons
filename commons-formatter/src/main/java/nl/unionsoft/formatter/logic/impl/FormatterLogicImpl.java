package nl.unionsoft.formatter.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.unionsoft.formatter.Formatter;
import nl.unionsoft.formatter.FormatterConfig;
import nl.unionsoft.formatter.FormatterConfig.FormatterScope;
import nl.unionsoft.formatter.impl.complex.EnumFormatter;
import nl.unionsoft.formatter.logic.FormatterLogic;
import nl.unionsoft.formatter.requestrespons.ObjectRequest;
import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;
import nl.unionsoft.formatter.requestrespons.StringResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FormatterLogicImpl implements FormatterLogic {

    private static final Logger LOGGER = Logger.getLogger(FormatterLogicImpl.class);
    private final List<FormatterConfig> formatterConfigs;

    private Map<String, Formatter> singletonFormatters;

    public FormatterLogicImpl() {
        formatterConfigs = new ArrayList<FormatterConfig>();
        singletonFormatters = new HashMap<String, Formatter>();
    }

    public StringResponse objectToString(Class<?> formatterClass, ObjectRequest objectRequest) {
        FormatterConfig formatterConfig = getFormatterConfigByHandlerType(formatterClass);
        StringResponse stringResponse = null;
        if (formatterConfig != null) {
            Formatter formatter = getFormatter(formatterConfig);
            stringResponse = objectToString(formatter, objectRequest);
        }
        return stringResponse;
    }

    public StringResponse objectToString(String formatterId, final ObjectRequest objectRequest) {
        FormatterConfig formatterConfig = getFormatterConfigById(formatterId);
        StringResponse stringResponse = null;
        if (formatterConfig != null) {
            Formatter formatter = getFormatter(formatterConfig);
            stringResponse = objectToString(formatter, objectRequest);
        }
        return stringResponse;
    }

    public ObjectResponse stringToObject(String formatterId, final StringRequest stringRequest) {
        FormatterConfig formatterConfig = getFormatterConfigById(formatterId);
        ObjectResponse objectResponse = null;
        if (formatterConfig != null) {
            Formatter formatter = getFormatter(formatterConfig);
            objectResponse = stringToObject(formatter, stringRequest);
        }
        return objectResponse;
    }

    public ObjectResponse stringToObject(Class<?> formatterClass, StringRequest stringRequest) {
        FormatterConfig formatterConfig = getFormatterConfigByHandlerType(formatterClass);
        ObjectResponse objectResponse = null;
        if (formatterConfig != null) {
            Formatter formatter = getFormatter(formatterConfig);
            objectResponse = stringToObject(formatter, stringRequest);
        }
        return objectResponse;
    }

    private ObjectResponse stringToObject(Formatter formatter, StringRequest stringRequest) {
        return formatter.stringToObject(stringRequest);
    }

    private StringResponse objectToString(Formatter formatter, final ObjectRequest objectRequest) {
        return formatter.objectToString(objectRequest);
    }

    public void registerFormatterConfig(final FormatterConfig formatterConfig, Boolean replaceExisting) {
        LOGGER.info("Adding formatterConfig '" + formatterConfig + "'.");

        if (formatterConfig == null) {
            throw new IllegalArgumentException("formatterConfig is required!");
        }

        // Validate properties set
        String id = formatterConfig.getId();
        final Class<?> formatterClass = formatterConfig.getFormatterClass();
        if (StringUtils.isEmpty(id)) {
            throw new IllegalStateException("formatterConfig has no id!");
        } else if (formatterClass == null) {
            throw new IllegalStateException("formatterConfig has no formatterClass!");
        }

        // Validate handlers not empty
        final List<Class<?>> handlers = formatterConfig.getHandlers();
        if (handlers == null || handlers.size() == 0) {
            throw new IllegalStateException("formatterConfig '" + formatterConfig + "' has no handlerTypes!");
        }

        if (formatterConfig != null) {
            synchronized (formatterConfigs) {

                // Validate id not yet registered.
                if (getFormatterConfigById(id) != null) {
                    if (BooleanUtils.isNotTrue(replaceExisting)) {
                        throw new IllegalStateException("formatterConfig already found for id " + id);
                    } else {
                        deleteFormatterConfigById(id);
                    }
                }

                // Check if none of the handlerTypes are registered.
                for (final Class<?> handlerType : handlers) {
                    final FormatterConfig formatterConfigForHandlerType = getFormatterConfigByHandlerType(handlerType);
                    if (formatterConfigForHandlerType != null) {
                        if (BooleanUtils.isNotTrue(replaceExisting)) {
                            throw new IllegalStateException("already found a formatterConfig for handlerType " + handlerType + " for config " + formatterConfig);
                        } else {
                            deleteFormatterConfigByHandlerType(handlerType);
                        }
                    }
                }
                // add
                formatterConfigs.add(formatterConfig);
            }
        }
    }

    public void deleteFormatterConfigById(String id) {
        List<FormatterConfig> configsToRemove = new ArrayList<FormatterConfig>();
        for (final FormatterConfig formatterConfig : formatterConfigs) {
            if (id.equals(formatterConfig.getId())) {
                configsToRemove.add(formatterConfig);
            }
        }
        for (FormatterConfig formatterConfig : configsToRemove) {
            formatterConfigs.remove(formatterConfig);
        }
    }

    public void deleteFormatterConfigByHandlerType(final Class<?> handlerType) {
        List<FormatterConfig> configsToRemove = new ArrayList<FormatterConfig>();
        for (final FormatterConfig formatterConfig : formatterConfigs) {
            if (formatterConfig.getHandlers().contains(handlerType)) {
                configsToRemove.add(formatterConfig);
            }
        }
        for (FormatterConfig formatterConfig : configsToRemove) {
            formatterConfigs.remove(formatterConfig);
        }
    }

    public FormatterConfig getFormatterConfigByHandlerType(final Class<?> handlerType) {
        FormatterConfig result = null;
        for (final FormatterConfig formatterConfig : formatterConfigs) {
            if (formatterConfig.getHandlers().contains(handlerType)) {
                result = formatterConfig;
                break;
            }
        }
        return result;
    }

    public FormatterConfig getFormatterConfigById(String id) {
        FormatterConfig result = null;
        for (final FormatterConfig formatterConfig : formatterConfigs) {
            if (id.equals(formatterConfig.getId())) {
                result = formatterConfig;
                break;
            }
        }
        return result;
    }

    public Formatter getFormatter(FormatterConfig formatterConfig) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("getFormatter(" + formatterConfig + ")");
        }
        Formatter formatter = null;
        if (formatterConfig != null) {
            FormatterScope formatterScope = formatterConfig.getFormatterScope();
            if (FormatterScope.INSTANCE.equals(formatterScope)) {

                formatter = createNewFormatter(formatterConfig.getFormatterClass());
            } else {
                String formatterId = formatterConfig.getId();
                formatter = singletonFormatters.get(formatterId);
                if (formatter == null) {
                    formatter = createNewFormatter(formatterConfig.getFormatterClass());
                    singletonFormatters.put(formatterId, formatter);
                }
            }
        }
        return formatter;
    }

    private Formatter createNewFormatter(Class<?> formatterClass) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new formatter for Class: " + formatterClass);
        }
        Formatter formatter = null;
        try {
            formatter = (Formatter) formatterClass.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("Unable to instantiate formatter for formatterClass '" + formatterClass + "'.", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Unable to access formatter for formatterClass '" + formatterClass + "'.", e);
        }
        return formatter;
    }

    public String simpleObjectToString(Object valueObject, Map<String, Object> parameters) {
        String response = null;
        ObjectRequest objectRequest = new ObjectRequest();
        objectRequest.setSourceObject(valueObject);
        if (parameters != null) {
            for (Entry<String, Object> entry : parameters.entrySet()) {
                objectRequest.setParameter(entry.getKey(), entry.getValue());
            }
        }

        Class<?> formatterClass = null;
        if (valueObject == null) {
            formatterClass = Object.class;
        } else {
            // Special enum handler
            Class<?> valueObjectClass = valueObject.getClass();
            if (valueObjectClass.isEnum()) {
                formatterClass = Enum.class;
                objectRequest.setParameter(EnumFormatter.ENUM_CLASS, valueObjectClass);
            } else {
                formatterClass = valueObject.getClass();
            }

        }
        StringResponse stringResponse = objectToString(formatterClass, objectRequest);
        if (stringResponse != null) {
            response = stringResponse.getResponse();
        }
        return response;
    }

    public String simpleObjectToString(Object valueObject) {
        return simpleObjectToString(valueObject, null);
    }

    public <OC> OC simpleStringToObject(Class<OC> objectClass, String stringValue, Map<String, Object> parameters) {

        StringRequest stringRequest = new StringRequest();
        stringRequest.setSourceString(stringValue);
        if (parameters != null) {
            for (Entry<String, Object> entry : parameters.entrySet()) {
                stringRequest.setParameter(entry.getKey(), entry.getValue());
            }
        }
        // Special enum logic..
        ObjectResponse objectResponse = null;
        if (objectClass != null && objectClass.isEnum()) {
            stringRequest.setParameter(EnumFormatter.ENUM_CLASS, objectClass);
            objectResponse = stringToObject(Enum.class, stringRequest);
        } else {
            objectResponse = stringToObject(objectClass, stringRequest);
        }

        Object result = null;
        if (objectResponse != null) {
            result = objectResponse.getResponse();
        }
        return (OC) result;
    }

    public <OC> OC simpleStringToObject(Class<OC> objectClass, String stringValue) {
        return simpleStringToObject(objectClass, stringValue, null);
    }

}
