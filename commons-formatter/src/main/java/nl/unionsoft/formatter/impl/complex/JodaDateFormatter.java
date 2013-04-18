package nl.unionsoft.formatter.impl.complex;

import nl.unionsoft.formatter.requestrespons.ObjectRequest;
import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;
import nl.unionsoft.formatter.requestrespons.StringResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

public class JodaDateFormatter extends BaseDateFormatter {

    public StringResponse objectToString(final ObjectRequest objectRequest) {
        StringResponse stringResponse = null;
        if (objectRequest != null) {
            stringResponse = new StringResponse();
            final Object sourceObject = objectRequest.getSourceObject();
            if (sourceObject instanceof DateTime) {
                final DateTime dateTime = (DateTime) sourceObject;
                stringResponse.setResponse(getDateTimeFormatter(objectRequest).print(dateTime.getMillis()));
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
                objectResponse.setResponse(getDateTimeFormatter(stringRequest).parseDateTime(sourceString));
            }
        }
        return objectResponse;
    }
}
