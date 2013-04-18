package nl.unionsoft.common.converter;

/**
 * Convert the given object (E) to target object (T) and back
 */
public interface BidirectionalConverter<D, O> extends Converter<D, O> {
    /**
     * Convert the given object (D) back to target object (E)
     * 
     * @param d
     *            source object d
     * @return target object O
     */
    public O convertBack(final D d);
}
