package nl.unionsoft.formatter;

import nl.unionsoft.formatter.requestrespons.ObjectRequest;
import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;
import nl.unionsoft.formatter.requestrespons.StringResponse;

/**
 * The Formatter interface is responsible for formatter non string data to strings and visa versa. When implementing, specify the transform-type T.
 * 
 * @author Drakandar
 * 
 * @param <T>
 *            The type you want to convert to and from.
 */
public interface Formatter {

    /**
     * Converts the given object in the objectRequest to a string in the StringResponse.
     * 
     * @param objectRequest
     * @return
     */
    public StringResponse objectToString(final ObjectRequest objectRequest);

    public abstract ObjectResponse stringToObject(StringRequest stringRequest);

}
