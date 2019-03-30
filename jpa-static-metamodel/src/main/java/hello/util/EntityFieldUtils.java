package hello.util;

import org.springframework.util.StringUtils;

public final class EntityFieldUtils {

    public final static String FIELD_DELIMITER = ".";

    /**
     * Concat by field delimiter
     *
     * @param arr arr
     * @return string
     */
    public static String concat(String... arr) {
        return String.join(FIELD_DELIMITER, arr);
    }

    /**
     * Relation field it's field like: "post.id"
     *
     * @param fieldName fieldName
     * @return true or false
     */
    public static boolean isRelationField(String fieldName) {
        return getSplitField(fieldName) != null;
    }

    /**
     * @param fieldName fieldName
     * @return string
     */
    public static String getRelationFieldAlias(String fieldName) {
        validateField(fieldName);
        return getSplitField(fieldName)[0];
    }

    /**
     * @param fieldName fieldName
     * @return string
     */
    public static String getRelationFieldName(String fieldName) {
        validateField(fieldName);
        return getSplitField(fieldName)[1];
    }

    private static void validateField(String fieldName) {
        if (!isRelationField(fieldName)) {
            throw new RuntimeException(String.format("Passed not relation field name: '%s'", fieldName));
        }
    }

    private static String[] getSplitField(String fieldName) {
        return StringUtils.split(fieldName, FIELD_DELIMITER);
    }
}
