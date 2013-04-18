package nl.unionsoft.formatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import nl.unionsoft.formatter.FormatterConfig.FormatterScope;
import nl.unionsoft.formatter.logic.ContextUtil;
import nl.unionsoft.formatter.logic.FormatterLogic;
import nl.unionsoft.formatter.logic.impl.ContextUtilImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FormatterFactory {
    private static final Logger LOGGER = Logger.getLogger(FormatterFactory.class);
    private ContextUtil contextUtil;

    private FormatterFactory() {
        // Private constructor
    }

    private void init(String applicationContextLocation, String classPathPropertyFileLocation) {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(applicationContextLocation);
        contextUtil = ContextUtilImpl.getBeanByClass(ContextUtil.class, applicationContext);
        contextUtil.setApplicationContext(applicationContext);
        final FormatterLogic formatterLogic = contextUtil.getBeanByClass(FormatterLogic.class);
        // Load systemFormatters
        final InputStream propertyStream = FormatterFactory.class.getResourceAsStream(classPathPropertyFileLocation);
        final Properties properties = new Properties();
        try {
            properties.load(propertyStream);
            final String formatterIdString = properties.getProperty("config.formatterIds");
            if (StringUtils.isNotEmpty(formatterIdString)) {
                final String[] formatterIds = StringUtils.split(formatterIdString, ',');
                for (final String formatterId : formatterIds) {
                    handleFormatterConfigById(StringUtils.trim(formatterId), properties, formatterLogic);
                }
            }
        } catch (final IOException e) {
            LOGGER.error("Unable to load properties", e);
        } finally {
            try {
                propertyStream.close();
            } catch (final IOException e) {
                LOGGER.error("Unable to close stream", e);
            }

        }
    }

    private void handleFormatterConfigById(final String formatterId, final Properties properties, final FormatterLogic formatterLogic) {
        final String formatterClassStrProp = "formatters." + formatterId + ".class";
        final String formatterClassStr = properties.getProperty(formatterClassStrProp);
        if (StringUtils.isEmpty(formatterClassStr)) {
            throw new IllegalStateException("Could not find property for formatterClass '" + formatterClassStrProp + "'.");
        }
        final FormatterConfig formatterConfig = new FormatterConfig();
        formatterConfig.setId(formatterId);
        Class<?> formatterClass;
        try {
            // FormatterClass
            formatterClass = Class.forName(formatterClassStr);
            formatterConfig.setFormatterClass(formatterClass);

            // Scope
            String scope = properties.getProperty("formatters." + formatterId + ".scope");
            if (StringUtils.isNotEmpty(scope)) {
                formatterConfig.setFormatterScope(FormatterScope.valueOf(StringUtils.upperCase(scope)));
            }
            // handlers
            int count = 1;
            String handler = null;
            while (StringUtils.isNotEmpty((handler = properties.getProperty("formatters." + formatterId + ".handler" + count)))) {
                final Class<?> formatterHandler = Class.forName(handler);
                formatterConfig.addHandler(formatterHandler);
                count++;
            }
            if (formatterConfig.getHandlers().isEmpty()) {
                throw new IllegalStateException("No handlers found for formatterconfig " + formatterClassStrProp);
            }
            formatterLogic.registerFormatterConfig(formatterConfig, false);
        } catch (final ClassNotFoundException e) {
            LOGGER.error("Unable to load formatterClass", e);
        }

    }

    public FormatterLogic getFormatterLogic() {
        return contextUtil.getBeanByClass(FormatterLogic.class);
    }

    public static FormatterFactory createInstance() {
        return createInstance("classpath:nl/unionsoft/formatter/spring/application-context.xml", "/nl/unionsoft/formatter/config.properties");
    }

    public static FormatterFactory createInstance(String applicationContextLocation, String classPathPropertyFileLocation) {
        final FormatterFactory formatterFactory = new FormatterFactory();
        formatterFactory.init(applicationContextLocation, classPathPropertyFileLocation);
        return formatterFactory;
    }

}
