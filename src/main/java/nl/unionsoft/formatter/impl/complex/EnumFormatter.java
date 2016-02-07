package nl.unionsoft.formatter.impl.complex;

import nl.unionsoft.formatter.Formatter;
import nl.unionsoft.formatter.requestrespons.ObjectRequest;
import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;
import nl.unionsoft.formatter.requestrespons.StringResponse;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("unchecked")
public class EnumFormatter implements Formatter {

    public static final String ENUM_CLASS = "formatter.enum.class";

    public StringResponse objectToString(final ObjectRequest objectRequest) {
        StringResponse stringResponse = null;
        if (objectRequest != null) {
            stringResponse = new StringResponse();
            final Object sourceObject = objectRequest.getSourceObject();
            if (sourceObject instanceof Enum) {
                final Enum<?> sourceEnum = (Enum<?>) sourceObject;
                stringResponse.setResponse(sourceEnum.name());
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
                final Class<?> localEnumClass = stringRequest.getParameter(Class.class, ENUM_CLASS);
                if (localEnumClass == null) {
                    throw new IllegalStateException("enumClass is required!");
                }
                Enum<?> result = null;
                if (localEnumClass.isEnum()) {
                    final Object[] enumConstants = localEnumClass.getEnumConstants();
                    for (final Object object : enumConstants) {
                        final Enum<?> enumConstant = (Enum<?>) object;
                        if (enumConstant.name().equals(sourceString)) {
                            result = enumConstant;
                            break;
                        }
                    }
                }
                objectResponse.setResponse(result);
            }
        }
        return objectResponse;
    }

}
