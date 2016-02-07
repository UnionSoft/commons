package nl.unionsoft.formatter.impl.simple;

import nl.unionsoft.formatter.Formatter;
import nl.unionsoft.formatter.requestrespons.ObjectRequest;
import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;
import nl.unionsoft.formatter.requestrespons.StringResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class ClassFormatterImpl implements Formatter {

    private static final Logger LOGGER = Logger.getLogger(ClassFormatterImpl.class);

    public StringResponse objectToString(final ObjectRequest objectRequest) {
        StringResponse stringResponse = null;
        if (objectRequest != null) {
            stringResponse = new StringResponse();
            final Object sourceObject = objectRequest.getSourceObject();
            if (sourceObject instanceof Class) {
                stringResponse.setResponse(((Class<?>) sourceObject).getCanonicalName());
            }
        }
        return stringResponse;
    }

    public ObjectResponse stringToObject(final StringRequest stringRequest) {
        ObjectResponse objectResponse = null;
        if (stringRequest != null) {
            objectResponse = new ObjectResponse();
            final String sourceString = stringRequest.getSourceString();
            if (StringUtils.isNotEmpty(sourceString)) {
                try {
                    objectResponse.setResponse(Class.forName(sourceString));
                } catch (final ClassNotFoundException e) {
                    LOGGER.error("Unable to parse data '" + sourceString + " to class.", e);
                }
            }
        }
        return objectResponse;
    }
}
