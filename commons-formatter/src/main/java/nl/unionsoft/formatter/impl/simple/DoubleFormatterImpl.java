package nl.unionsoft.formatter.impl.simple;

import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DoubleFormatterImpl extends StringFormatterImpl {

    private static final Logger LOGGER = Logger.getLogger(DoubleFormatterImpl.class);

    @Override
    public ObjectResponse stringToObject(final StringRequest stringRequest) {
        ObjectResponse objectResponse = null;
        if (stringRequest != null) {
            objectResponse = new ObjectResponse();
            final String sourceString = stringRequest.getSourceString();
            if (StringUtils.isNotEmpty(sourceString)) {
                try {
                    objectResponse.setResponse(Double.parseDouble(sourceString));
                } catch (final NumberFormatException nfe) {
                    LOGGER.warn("Caught NumberFormaterException", nfe);
                }
            }
        }
        return objectResponse;
    }

}
