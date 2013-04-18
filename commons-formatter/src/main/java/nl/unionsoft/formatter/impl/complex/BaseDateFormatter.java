package nl.unionsoft.formatter.impl.complex;

import java.util.Locale;

import nl.unionsoft.formatter.Formatter;
import nl.unionsoft.formatter.requestrespons.Request;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * The BaseDateFormatter helps extending DateFormatter classes to get the right JodaTime DateTimeFormat
 * 
 * @author Drakandar
 * 
 * @param <T>
 *            the transformer type, same as with Formatter<T>
 */

public abstract class BaseDateFormatter implements Formatter {
    private static final String DEFAULT_FORMAT = "dd/MM/yyyy HH:mm:ss S";
    public static final String FORMAT = "formatter.date.format";

    private static final String DEFAULT_LOCALE_COUNTRY = null;
    private static final String DEFAULT_LOCALE_LANGUAGE = "en";
    public static final String LOCALE_COUNTRY = "formatter.date.locale_country";
    public static final String LOCALE_LANGUAGE = "formatter.date.locale_language";

    /**
     * Method to get a DateTimeFormatter.
     * 
     * @return DateTimeFormatter based on parameters and locale.
     */
    protected DateTimeFormatter getDateTimeFormatter(final Request request) {
        final String localeLanguageStr = request.getParameter(String.class, LOCALE_LANGUAGE, DEFAULT_LOCALE_LANGUAGE);
        final String localeCountryStr = request.getParameter(String.class, LOCALE_COUNTRY, DEFAULT_LOCALE_COUNTRY);
        Locale locale = null;
        if (StringUtils.isNotEmpty(localeCountryStr)) {
            locale = new Locale(localeLanguageStr, localeCountryStr);
        } else {
            locale = new Locale(localeLanguageStr);
        }

        // FormatterString
        final String formatStr = request.getParameter(String.class, FORMAT, DEFAULT_FORMAT);

        return DateTimeFormat.forPattern(formatStr).withLocale(locale);
    }
}
