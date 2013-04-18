package nl.unionsoft.formatter.impl.complex;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import nl.unionsoft.formatter.Formatter;
import nl.unionsoft.formatter.requestrespons.ObjectRequest;
import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;
import nl.unionsoft.formatter.requestrespons.StringResponse;

import org.apache.commons.lang.StringUtils;

public class BigDecimalFormatter implements Formatter {

    private static final int DEFAULT_MAX_FRACTION_DIGITS = 2;
    private static final int DEFAULT_MIN_FRACTION_DIGITS = 2;


    public static final String MAX_FRACTION_DIGITS = "formatter.bigdecimal.max_fraction_digits";
    public static final String MIN_FRACTION_DIGITS = "formatter.bigdecimal.min_fraction_digits";

    private static final String DEFAULT_LOCALE_COUNTRY = null;
    private static final String DEFAULT_LOCALE_LANGUAGE = "en";
    public static final String LOCALE_COUNTRY = "formatter.bigdecimal.locale_country";
    public static final String LOCALE_LANGUAGE = "formatter.bigdecimal.locale_language";

    public StringResponse objectToString(final ObjectRequest objectRequest) {
        StringResponse stringResponse = null;
        if (objectRequest != null) {
            stringResponse = new StringResponse();
            final Object sourceObject = objectRequest.getSourceObject();
            if (sourceObject instanceof BigDecimal) {

                final String localeLanguageStr = objectRequest.getParameter(String.class, LOCALE_LANGUAGE, DEFAULT_LOCALE_LANGUAGE);
                final String localeCountryStr = objectRequest.getParameter(String.class, LOCALE_COUNTRY, DEFAULT_LOCALE_COUNTRY);
                Locale locale = null;
                if (StringUtils.isNotEmpty(localeCountryStr)) {
                    locale = new Locale(localeLanguageStr, localeCountryStr);
                } else {
                    locale = new Locale(localeLanguageStr);
                }

                final Integer maxFractionDigitsParam = objectRequest.getParameter(Integer.class, MAX_FRACTION_DIGITS, DEFAULT_MAX_FRACTION_DIGITS);
                final Integer minFractionDigitsParam = objectRequest.getParameter(Integer.class, MIN_FRACTION_DIGITS, DEFAULT_MIN_FRACTION_DIGITS);

                final NumberFormat format = NumberFormat.getInstance(locale);
                format.setMinimumFractionDigits(maxFractionDigitsParam);
                format.setMaximumFractionDigits(minFractionDigitsParam);
                stringResponse.setResponse(format.format(sourceObject));
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
                objectResponse.setResponse(new BigDecimal(sourceString));
            }
        }
        return objectResponse;
    }

}
