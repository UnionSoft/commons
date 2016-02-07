package nl.unionsoft.formatter.requestrespons;

public class ObjectRequest extends Request {

    private Object sourceObject;

    /**
     * @return the sourceObject
     */
    public Object getSourceObject() {
        return sourceObject;
    }

    /**
     * @param sourceObject
     *            the sourceObject to set
     */
    public void setSourceObject(final Object sourceObject) {
        this.sourceObject = sourceObject;
    }

}
