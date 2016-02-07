package nl.unionsoft.formatter.requestrespons;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private final Map<String, Object> parameters;

    public Request() {
        parameters = new HashMap<String, Object>();
    }

    public void setParameter(final String key, final Object value) {
        parameters.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <PARAMCLASS> PARAMCLASS getParameter(final Class<PARAMCLASS> parameterClass, final String key) {
        return (PARAMCLASS) parameters.get(key);
    }

    @SuppressWarnings("unchecked")
    public <PARAMCLASS> PARAMCLASS getParameter(final Class<PARAMCLASS> parameterClass, final String key, final Object defaultValue) {
        Object result = getParameter(parameterClass, key);
        if (result == null) {
            result = defaultValue;
        }
        return (PARAMCLASS) result;
    }
}
