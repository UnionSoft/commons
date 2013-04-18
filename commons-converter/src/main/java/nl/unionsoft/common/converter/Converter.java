package nl.unionsoft.common.converter;

public interface Converter<TARGET, SOURCE> {

    /**
     * Converts the entity to a data transfer object.
     * 
     * @param e
     *            The entity to convert.
     * 
     * @return The converted DTO.
     */
    public TARGET convert(final SOURCE e);

}
