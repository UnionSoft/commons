package nl.unionsoft.formatter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.unionsoft.formatter.FormatterConfig.FormatterScope;
import nl.unionsoft.formatter.impl.complex.BigDecimalFormatter;
import nl.unionsoft.formatter.logic.FormatterLogic;
import nl.unionsoft.formatter.logic.impl.FormatterLogicImpl;
import nl.unionsoft.formatter.requestrespons.ObjectRequest;
import nl.unionsoft.formatter.requestrespons.ObjectResponse;
import nl.unionsoft.formatter.requestrespons.StringRequest;
import nl.unionsoft.formatter.requestrespons.StringResponse;

import org.joda.time.DateTime;
import org.junit.Test;

public class FormatterFactoryTest {

    @Test
    public void testFormatterFactory() {
        final FormatterFactory formatterFactory = FormatterFactory.createInstance();
        final FormatterLogic formatterLogic = formatterFactory.getFormatterLogic();
        // Add some formatters..
        try {
            // Missing formatterConfig
            formatterLogic.registerFormatterConfig(null, null);
            Assert.fail("no exception occured!");
        } catch (final IllegalArgumentException e) {
            Assert.assertEquals("formatterConfig is required!", e.getMessage());
        }

        try {
            // Add while already exists
            final FormatterConfig formatterConfig = new FormatterConfig();
            formatterConfig.setId("bigDecimalFormatter");
            formatterConfig.setFormatterClass(BigDecimalFormatter.class);
            formatterConfig.addHandler(BigDecimal.class);
            formatterLogic.registerFormatterConfig(formatterConfig, false);
            Assert.fail("no exception occured!");
        } catch (final IllegalStateException e) {
            Assert.assertEquals("formatterConfig already found for id bigDecimalFormatter", e.getMessage());
        }

        {
            // Add while already exists, but overwrite.
            final FormatterConfig formatterConfig = new FormatterConfig();
            formatterConfig.setId("bigDecimalFormatter");
            formatterConfig.setFormatterClass(BigDecimalFormatter.class);
            formatterConfig.addHandler(BigDecimal.class);
            formatterLogic.registerFormatterConfig(formatterConfig, true);
        }

        try {
            // Add while already exists, but with another id.
            final FormatterConfig formatterConfig = new FormatterConfig();
            formatterConfig.setId("bigDecimalFormatter2");
            formatterConfig.setFormatterClass(BigDecimalFormatter.class);
            formatterConfig.addHandler(BigDecimal.class);
            formatterLogic.registerFormatterConfig(formatterConfig, false);
        } catch (final IllegalStateException e) {
            Assert
                    .assertEquals(
                            "already found a formatterConfig for handlerType class java.math.BigDecimal for config FormatterConfig [formatterClass=class nl.unionsoft.formatter.impl.complex.BigDecimalFormatter, handlers=[class java.math.BigDecimal]]",
                            e.getMessage());
        }

        {
            // Add while already exists, but with another id, but overwrite
            final FormatterConfig formatterConfig = new FormatterConfig();
            formatterConfig.setId("bigDecimalFormatter2");
            formatterConfig.setFormatterClass(BigDecimalFormatter.class);
            formatterConfig.addHandler(BigDecimal.class);
            formatterLogic.registerFormatterConfig(formatterConfig, true);
        }

        try {
            // Missing formatterClass
            final FormatterConfig formatterConfig = new FormatterConfig();
            formatterConfig.setId("bigDecimalFormatter2");
            formatterConfig.addHandler(BigDecimal.class);
            formatterLogic.registerFormatterConfig(formatterConfig, false);
        } catch (final IllegalStateException e) {
            Assert.assertEquals("formatterConfig has no formatterClass!", e.getMessage());
        }
        try {
            // Missing id
            final FormatterConfig formatterConfig = new FormatterConfig();
            formatterConfig.addHandler(BigDecimal.class);
            formatterLogic.registerFormatterConfig(formatterConfig, false);
        } catch (final IllegalStateException e) {
            Assert.assertEquals("formatterConfig has no id!", e.getMessage());
        }

        try {
            // Missing handler(s)
            final FormatterConfig formatterConfig = new FormatterConfig();
            formatterConfig.setId("bigDecimalFormatter2");
            formatterConfig.setFormatterClass(BigDecimalFormatter.class);
            formatterLogic.registerFormatterConfig(formatterConfig, false);
        } catch (final IllegalStateException e) {
            Assert.assertEquals("formatterConfig 'FormatterConfig [formatterClass=class nl.unionsoft.formatter.impl.complex.BigDecimalFormatter, handlers=[]]' has no handlerTypes!", e.getMessage());
        }

    }

    @Test
    public void testFormatterCalls() {

        final FormatterFactory formatterFactory = FormatterFactory.createInstance();
        final FormatterLogic formatterLogic = formatterFactory.getFormatterLogic();
        // Call formatters
        {
            Assert.assertEquals("10.00", formatterLogic.simpleObjectToString(BigDecimal.TEN));
            Assert.assertEquals(BigDecimal.TEN, formatterLogic.simpleStringToObject(BigDecimal.class, "10"));
        }
        {
            final Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(BigDecimalFormatter.MAX_FRACTION_DIGITS, 10);
            parameters.put(BigDecimalFormatter.MIN_FRACTION_DIGITS, 10);
            Assert.assertEquals("10.0000000000", formatterLogic.simpleObjectToString(BigDecimal.TEN, parameters));
            Assert.assertEquals(BigDecimal.TEN, formatterLogic.simpleStringToObject(BigDecimal.class, "10"));
        }

        {
            Assert.assertEquals(null, formatterLogic.simpleObjectToString(null));
            Assert.assertEquals(null, formatterLogic.simpleStringToObject(null, null));
        }
        {
            Assert.assertEquals(null, formatterLogic.simpleObjectToString(null));
            Assert.assertEquals(null, formatterLogic.simpleStringToObject(String.class, null));
        }
        {
            Assert.assertEquals(null, formatterLogic.simpleObjectToString(null));
            Assert.assertEquals("", formatterLogic.simpleStringToObject(String.class, ""));
        }

        {
            Assert.assertEquals("10.1", formatterLogic.simpleObjectToString(Double.valueOf(10.1)));
            Assert.assertEquals(10.1, formatterLogic.simpleStringToObject(Double.class, "10.1"));
        }
        {
            Assert.assertEquals("10.1", formatterLogic.simpleObjectToString(Float.valueOf(10.1F)));
            Assert.assertEquals(10.1F, formatterLogic.simpleStringToObject(Float.class, "10.1"));
        }
        {
            Assert.assertEquals("10", formatterLogic.simpleObjectToString(Integer.valueOf(10)));
            Assert.assertEquals(Integer.valueOf(10), formatterLogic.simpleStringToObject(Integer.class, "10"));
        }

        {
            Assert.assertEquals("10", formatterLogic.simpleObjectToString(Long.valueOf(10)));
            Assert.assertEquals(Long.valueOf(10), formatterLogic.simpleStringToObject(Long.class, "10"));
        }
        {
            Assert.assertEquals(FormatterLogicImpl.class.getCanonicalName(), formatterLogic.simpleObjectToString(FormatterLogicImpl.class));
            Assert.assertEquals(FormatterLogicImpl.class, formatterLogic.simpleStringToObject(Class.class, FormatterLogicImpl.class.getCanonicalName()));
        }
        {
            final DateTime dateTime = new DateTime(2010, 1, 1, 1, 1, 1, 0);
            Assert.assertEquals("01/01/2010 01:01:01 0", formatterLogic.simpleObjectToString(dateTime));
            Assert.assertEquals(dateTime, formatterLogic.simpleStringToObject(DateTime.class, "01/01/2010 01:01:01 0"));
        }
        {
            final DateTime dateTime = new DateTime(2010, 1, 1, 1, 1, 1, 0);
            Assert.assertEquals("01/01/2010 01:01:01 0", formatterLogic.simpleObjectToString(dateTime.toDate()));
            Assert.assertEquals(dateTime.toDate(), formatterLogic.simpleStringToObject(Date.class, "01/01/2010 01:01:01 0"));
        }

        {

            Assert.assertEquals("INSTANCE", formatterLogic.simpleObjectToString(FormatterScope.INSTANCE));
            Assert.assertEquals(FormatterScope.INSTANCE, formatterLogic.simpleStringToObject(FormatterScope.class, "INSTANCE"));
        }

        // DIY
        {
            final ObjectRequest objectRequest = new ObjectRequest();
            objectRequest.setSourceObject(BigDecimal.TEN);
            objectRequest.setParameter(BigDecimalFormatter.MAX_FRACTION_DIGITS, 10);
            objectRequest.setParameter(BigDecimalFormatter.MIN_FRACTION_DIGITS, 10);
            final StringResponse stringResponse = formatterLogic.objectToString("bigDecimalFormatter", objectRequest);
            Assert.assertEquals("10.0000000000", stringResponse.getResponse());
        }
        {
            final StringRequest stringRequest = new StringRequest();
            stringRequest.setSourceString("10");
            stringRequest.setParameter(BigDecimalFormatter.MAX_FRACTION_DIGITS, 10);
            stringRequest.setParameter(BigDecimalFormatter.MIN_FRACTION_DIGITS, 10);
            final ObjectResponse objectResponse = formatterLogic.stringToObject("bigDecimalFormatter", stringRequest);
            Assert.assertEquals(BigDecimal.TEN, objectResponse.getResponse());
        }
    }
}
