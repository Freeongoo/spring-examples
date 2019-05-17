package hello.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.String.format;

public class ReflectionUtils {

    private static final String LOG_PREFIX = "[ReflectionUtils] ";

    private static final String GETTER_PREFIX = "get";
    private static final String SETTER_PREFIX = "set";

    /**
     * @param fieldName fieldName
     * @return String
     */
    public static String getterByFieldName(String fieldName) {
        if (isStringNullOrEmpty(fieldName))
            return null;

        return convertFieldByAddingPrefix(fieldName, GETTER_PREFIX);
    }

    /**
     * @param fieldName fieldName
     * @return String
     */
    public static String setterByFieldName(String fieldName) {
        if (isStringNullOrEmpty(fieldName))
            return null;

        return convertFieldByAddingPrefix(fieldName, SETTER_PREFIX);
    }

    private static String convertFieldByAddingPrefix(String fieldName, String prefix) {
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * @param obj obj
     * @param fieldName fieldName
     * @return Object
     */
    public static Object getFieldContent(Object obj, String fieldName) {
        if (!isValidParams(obj, fieldName))
            return null;

        try {
            Field declaredField = getFieldAccessible(obj, fieldName);
            return declaredField.get(obj);
        } catch (Exception e) {
            String exceptionMsg = format("Cannot find or get field: '%s' from object: '%s'", fieldName, obj);
            throw new RuntimeException(exceptionMsg, e);
        }
    }

    public static boolean isExistFieldName(Class<?> clazz, String fieldName) {
        Optional<Field> field = getField(clazz, fieldName);
        return field.isPresent();
    }

    /**
     * @param obj obj
     * @param fieldName fieldName
     * @param value value
     */
    public static void setFieldContent(Object obj, String fieldName, Object value) {
        if (!isValidParams(obj, fieldName))
            return;

        try {
            Field declaredField = getFieldAccessible(obj, fieldName);
            declaredField.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Field getFieldAccessible(Object obj, String fieldName) {
        Optional<Field> optionalField = getField(obj, fieldName);
        Field declaredField = optionalField.get(); // not need check isPresent - throw exception
        declaredField.setAccessible(true);
        return declaredField;
    }

    /**
     * @param obj obj
     * @param methodName methodName
     * @return Object
     */
    public static Object callMethod(Object obj, String methodName) {
        if (!isValidParams(obj, methodName))
            return null;

        try {
            Method method = obj.getClass().getMethod(methodName);
            method.setAccessible(true);
            return method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param clazz clazz
     * @return Field[]
     */
    public static Field[] getAllFields(Class<?> clazz) {
        if (clazz == null) return null;

        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            // danger! Recursion
            fields.addAll(Arrays.asList(getAllFields(clazz.getSuperclass())));
        }
        return fields.toArray(new Field[] {});
    }

    /**
     * @param obj obj
     * @param fieldName fieldName
     * @return Optional<Field>
     */
    public static Optional<Field> getField(Object obj, String fieldName) {
        if (!isValidParams(obj, fieldName))
            return Optional.empty();

        Class<?> clazz = obj.getClass();
        return getField(clazz, fieldName);
    }

    /**
     * @param clazz clazz
     * @param fieldName fieldName
     * @return Optional<Field>
     */
    public static Optional<Field> getField(Class<?> clazz, String fieldName) {
        if (!isValidParams(clazz, fieldName))
            return Optional.empty();

        Field[] fields = getAllFields(clazz);
        return Stream.of(fields)
                .filter(x -> x.getName().equals(fieldName))
                .findFirst();
    }

    private static boolean convertStringToBoolean(String s) {
        String trim = s.trim();
        return !trim.equals("") && !trim.equals("0") && !trim.toLowerCase().equals("false");
    }

    /**
     * @param clazz clazz
     * @param fieldName fieldName
     * @return Class
     */
    public static Class<?> getFieldType(Class<?> clazz, String fieldName) {
        return getFieldWithCheck(clazz, fieldName).getType();
    }

    /**
     * @param clazz clazz
     * @param fieldName fieldName
     * @return Field
     */
    public static Field getFieldWithCheck(Class<?> clazz, String fieldName) {
        return ReflectionUtils.getField(clazz, fieldName)
                .orElseThrow(() -> {
                    String msg = String.format("Cannot find field name: '%s' from class: '%s'", fieldName, clazz);
                    return new IllegalArgumentException(msg);
                });
    }

    private static boolean isValidParams(Object obj, String param) {
        return (obj != null && !isStringNullOrEmpty(param));
    }

    private static boolean isStringNullOrEmpty(String fieldName) {
        return fieldName == null || fieldName.trim().length() == 0;
    }
}
