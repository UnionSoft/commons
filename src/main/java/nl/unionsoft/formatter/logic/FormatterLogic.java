package nl.unionsoft.formatter.logic;

import java.util.Map;

import nl.unionsoft.formatter.FormatterConfig;
import nl.unionsoft.formatter.requestrespons.ObjectRequest;
import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;
import nl.unionsoft.formatter.requestrespons.StringResponse;

public interface FormatterLogic {

    public ObjectResponse stringToObject(String formatterId, StringRequest stringRequest);

    public ObjectResponse stringToObject(Class<?> formatterClass, StringRequest stringRequest);

    public StringResponse objectToString(String formatterId, final ObjectRequest objectRequest);

    public StringResponse objectToString(Class<?> formatterClass, final ObjectRequest objectRequest);

    public String simpleObjectToString(Object valueObject);

    public String simpleObjectToString(Object valueObject, Map<String, Object> parameters);

    public <OC> OC simpleStringToObject(Class<OC> objectClass, String stringValue);

    public <OC> OC simpleStringToObject(Class<OC> objectClass, String stringValue, Map<String, Object> parameters);

    /**
     * Register a new formatterConfig, optionally replace existing formatterConfigs (based on id and handlers)
     * 
     * @param formatterConfig
     *            the formatterConfig to add.
     * @param replaceExisting
     *            if you want to replace existing FormatterConfig's if handlers or id's match
     */
    public void registerFormatterConfig(FormatterConfig formatterConfig, Boolean replaceExisting);

    /**
     * Removes the formatterConfig with the given id.
     * 
     * @param id
     *            the id to search and delete on.
     */
    public void deleteFormatterConfigById(String id);

    /**
     * Removes the FormatterConfig with the given handlerType.
     * 
     * @param handlerType
     *            the handlerType to search and delete on.
     */
    public void deleteFormatterConfigByHandlerType(final Class<?> handlerType);

    /**
     * Searches for a FormatterConfig with the given handlerType.
     * 
     * @param handlerType
     *            the handlerType to search on.
     * @return the formatterConfig corresponding with this handlerType.
     */
    public FormatterConfig getFormatterConfigByHandlerType(final Class<?> handlerType);

    /**
     * Searches for a FormatterConfig with the given id.
     * 
     * @param id
     *            the id to search on
     * @return the formatterConfig corresponding with this id.
     */
    public FormatterConfig getFormatterConfigById(String id);
}
