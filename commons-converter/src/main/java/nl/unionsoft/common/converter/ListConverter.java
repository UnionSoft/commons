package nl.unionsoft.common.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Converts list a to list b
 * 
 * @author ckramer
 * 
 */

public final class ListConverter {

    /**
     * Private Constructor. Is a utility.
     */
    private ListConverter() {
        super();
    }

    /**
     * Converter used to converts lists from type A to type B.
     * 
     * @param <DESTINATION>
     *            the request class for the new list
     * @param <SOURCE>
     *            the given source class for the old list
     * @param destinationClass
     *            the request class for the new list
     * @param objectConverter
     *            The converter to use for each entity
     * @param sourceList
     *            the list containing the source entities
     * @return a new list containing all the converted objects
     */
    public static <DESTINATION, SOURCE> List<DESTINATION> convert(final Converter<DESTINATION, SOURCE> objectConverter, final Collection<SOURCE> sourceList) {

        if (objectConverter == null) {
            throw new IllegalArgumentException("objectConverter is required!");
        }

        List<DESTINATION> result = null;
        if (sourceList != null) {
            result = new ArrayList<DESTINATION>();
            for (final SOURCE source : sourceList) {
                result.add(objectConverter.convert(source));
            }
        }
        return result;
    }

    /**
     * Converter used to converts lists from type A to type B.
     * 
     * @param <DESTINATION>
     *            the request class for the new list
     * @param <SOURCE>
     *            the given source class for the old list
     * @param destinationClass
     *            the request class for the new list
     * @param objectConverter
     *            The converter to use for each entity
     * @param sourceList
     *            the list containing the source entities
     * @return a new list containing all the converted objects
     */
    public static <DESTINATION, SOURCE, CONFIGOBJECT> List<DESTINATION> convert(final ConverterWithConfig<DESTINATION, SOURCE, CONFIGOBJECT> objectConverter, final Collection<SOURCE> sourceList, CONFIGOBJECT configObject) {

        if (objectConverter == null) {
            throw new IllegalArgumentException("objectConverter is required!");
        }

        List<DESTINATION> result = null;
        if (sourceList != null) {
            result = new ArrayList<DESTINATION>();
            for (final SOURCE source : sourceList) {
                result.add(objectConverter.convert(source, configObject));
            }
        }
        return result;
    }

    /**
     * Converter used to converts lists from type A to type B.
     * 
     * s * @param <DESTINATION> the request class for the new list
     * 
     * @param <SOURCE>
     *            the given source class for the old list
     * @param destinationClass
     *            the request class for the new list
     * @param objectConverter
     *            The converter to use for each entity
     * @param sourceList
     *            the list containing the source entities
     * @return a new list containing all the converted objects
     */
    public static <DESTINATION, SOURCE> List<SOURCE> convertBack(final BidirectionalConverter<DESTINATION, SOURCE> objectConverter, final Collection<DESTINATION> sourceList) {
        if (objectConverter == null) {
            throw new IllegalArgumentException("objectConverter is required!");
        }

        List<SOURCE> result = null;
        if (sourceList != null) {
            result = new ArrayList<SOURCE>();
            for (final DESTINATION source : sourceList) {
                result.add(objectConverter.convertBack(source));
            }
        }
        return result;
    }

}
