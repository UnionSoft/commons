package nl.unionsoft.formatter;

import java.util.ArrayList;
import java.util.List;

public class FormatterConfig {

    private String id;
    private FormatterScope formatterScope;
    private Class<?> formatterClass;
    private List<Class<?>> handlers;

    public FormatterConfig() {
        handlers = new ArrayList<Class<?>>();
    }

    /**
     * @return the formatterClass
     */
    public Class<?> getFormatterClass() {
        return formatterClass;
    }

    /**
     * @param formatterClass
     *            the formatterClass to set
     */
    public void setFormatterClass(final Class<?> formatterClass) {
        this.formatterClass = formatterClass;
    }

    /**
     * @return the handlers
     */
    public List<Class<?>> getHandlers() {
        return handlers;
    }

    /**
     * @param handlers
     *            the handlers to set
     */
    public void setHandlers(final List<Class<?>> handlers) {
        this.handlers = handlers;
    }

    public void addHandler(final Class<?> handler) {
        handlers.add(handler);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the formatterScope
     */
    public FormatterScope getFormatterScope() {
        return formatterScope;
    }

    /**
     * @param formatterScope
     *            the formatterScope to set
     */
    public void setFormatterScope(FormatterScope formatterScope) {
        this.formatterScope = formatterScope;
    }

    public enum FormatterScope {
        SINGLETON, INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FormatterConfig [formatterClass=" + formatterClass + ", handlers=" + handlers + "]";
    }

}
