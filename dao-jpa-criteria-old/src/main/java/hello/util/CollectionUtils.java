package hello.util;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class CollectionUtils {

    private CollectionUtils() { }

    /**
     * @param collection1 collection1
     * @param collection2 collection2
     * @param <E> E
     * @return merged list
     */
    public static <E> Collection<E> merge(Collection<E> collection1, Collection<E> collection2) {
        return Stream.concat(collection1.stream(), collection2.stream())
                .collect(toList());
    }

    /**
     * @param collection collection
     * @param <E> E
     * @return not-null collection
     */
    public static <E> Collection<E> convertIfNullToEmptyCollection(Collection<E> collection) {
        return Optional.ofNullable(collection).orElseGet(Collections::emptyList);
    }

    /**
     *
     * @param collection collection
     * @param <E> E
     * @return not-null list
     */
    public static <E> List<E> convertIfNullToEmptyList(Collection<E> collection) {
        return new ArrayList<>(convertIfNullToEmptyCollection(collection));
    }

    /**
     *
     * @param list list
     * @param index index
     * @return exist index or not
     */
    public static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

    /**
     * @param collection collection
     * @return boolean
     */
    public static boolean collectionIsNullOrEmpty (Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * @param collection collection
     * @param <E> type
     * @return boolean
     */
    public static <E> boolean isExistNull(Collection<E> collection) {
        return collection.stream()
                .anyMatch(Objects::isNull);
    }

    /**
     * @param collection collection
     * @param <E> type
     * @return collection without null
     */
    public static <E> Collection<E> getCollectionWithoutNull(Collection<E> collection) {
        return collection.stream()
                .filter(Objects::nonNull)
                .collect(toList());
    }
}
