package com.example.messagingstompwebsocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Collections.emptyMap;
import static java.util.Objects.isNull;

public final class JacksonUtil {

    public static final String EMPTY_OBJECT_JSON = "{}";

    private JacksonUtil() {
    }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * @param json  json
     * @param key   key
     * @param value value
     * @return json
     */
    public static String addToJsonKeyAndValue(String json, String key, Object value) {
        if (isNull(json) || isNull(key)) {
            return json;
        }

        Map<String, Object> map = fromStringToMap(json);

        map.put(key, value);
        return toString(map);
    }

    /**
     * @param json json
     * @param mapAdd mapAdd
     * @return json
     */
    public static String addToJsonMap(String json, Map<String, Object> mapAdd) {
        if (isNull(json)) {
            return json;
        }

        Map<String, Object> map = fromStringToMap(json);

        map.putAll(mapAdd);
        return toString(map);
    }

    /**
     * @param json json
     * @return map
     */
    public static Map<String, Object> getMap(String json) {
        if (isNull(json)) {
            return emptyMap();
        }

        try {
            TypeFactory typeFactory = OBJECT_MAPPER.getTypeFactory();
            MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, Object.class);
            return OBJECT_MAPPER.readValue(json, mapType);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: '"
                    + json + "' cannot be transformed to map", e);
        }
    }

    /**
     * Convert object to map
     *
     * @param entity entity
     * @return map
     */
    public static Map<String, Object> convertObjectToMap(Object entity) {
        return getMap(toString(entity));
    }

    /**
     * @param entity entity
     * @param jsonView jsonView
     * @return map
     */
    public static Map<String, Object> convertObjectToMapWithJsonView(Object entity, Class<?> jsonView) {
        return getMap(toStringWithJsonView(entity, jsonView));
    }

    public static Map<String, Object> convertObjectToMapWithoutNullFieldAndEmptyArray(Object entity) {
        return getMap(toStringWithoutNullFieldsAndEmptyArray(entity));
    }

    /**
     * @param json json
     * @param key  key
     * @return value or null
     */
    public static Object getValueByKey(String json, String key) {
        if (isNull(json)) {
            return null;
        }
        Map<String, Object> map = getMap(json);
        return map.computeIfAbsent(key, k -> null);
    }

    /**
     * Detect is passed json string is array?
     * Ex.: '[{"name": 1}]' is true
     *
     * @param json array string
     * @return true or false
     */
    public static boolean isJsonArray(String json) {
        return json.trim().charAt(0) == '[';
    }

    /**
     * @param json array string
     * @throws IllegalArgumentException is passed string json is not array
     */
    public static void validateJsonIsArray(String json) {
        if (isJsonArray(json)) {
            return;
        }

        throw new IllegalArgumentException("The transmitted json must be an array");
    }

    /**
     * Convert json string to object by clazz
     *
     * @param string string
     * @param clazz  clazz
     * @param <T>    T
     * @return object
     */
    public static <T> T fromString(String string, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(string, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + string + " cannot be transformed to Json object by class: "
                    + clazz.getName(), e);
        }
    }

    public static <T> T fromFile(File file, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given file value: "
                    + file + " cannot be transformed to Json object by class: "
                    + clazz.getName(), e);
        }
    }

    public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given bytes value cannot be transformed to Json object by class: "
                    + clazz.getName(), e);
        }
    }

    /**
     * @param string string object or array
     * @param clazz  clazz
     * @return object or list of object
     */
    public static Object fromStringObjectOrArray(String string, Class<?> clazz) {
        if (!isJsonArray(string)) {
            return fromString(string, clazz);
        }

        return fromStringArray(string, clazz);
    }

    /**
     * @param string string object
     * @return map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> fromStringToMap(String string) {
        return fromString(string, HashMap.class);
    }


    /**
     * Convert json string contains array to list of object by clazz
     *
     * @param stringArray stringArray
     * @param clazz       clazz
     * @param <T>         T
     * @return list of object
     */
    public static <T> List<T> fromStringArray(String stringArray, Class<T> clazz) {
        validateJsonIsArray(stringArray);

        try {
            CollectionType javaType = OBJECT_MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
            return OBJECT_MAPPER.readValue(stringArray, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given stringArray value: "
                    + stringArray + " cannot be transformed to Json object by class: "
                    + clazz.getName(), e);
        }
    }

    /**
     * Danger! Passed param 'objectForUpdate' will be change
     *
     * @param json            json
     * @param objectForUpdate objectForUpdate
     */
    public static void updateFromString(String json, Object objectForUpdate) {
        try {
            OBJECT_MAPPER.readerForUpdating(objectForUpdate).readValue(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given json value: "
                    + json + " cannot be transformed for object update: "
                    + objectForUpdate, e);
        }
    }

    /**
     * @param json json
     * @param key key
     * @return json
     */
    public static String removeByKeyDeep(String json, String key) {
        if (isNull(json)) {
            return null;
        }
        String jsonWithoutKey = json.replaceAll("\"" + key + "\"[ ]*:[^,}\\]]*[,]?", "");
        return jsonWithoutKey.replaceAll(",\\s*}", "}");
    }

    /**
     * @param json json
     * @param keys keys
     * @return json
     */
    public static String removeByKeysDeep(String json, String ...keys) {
        if (isNull(json)) {
            return null;
        }
        if (isNull(keys)) {
            return json;
        }

        for (String key : keys) {
            json = removeByKeyDeep(json, key);
        }

        return json;
    }

    /**
     * Remove key only in first level of nesting
     * For all level using {@link #removeByKeyDeep}
     *
     * @param json json or jsonArray
     * @param key key
     *
     * @return json without passed key
     */
    public static String removeByKey(String json, String key) {
        if (isJsonArray(json)) {
            List<Map> maps = fromStringArray(json, Map.class);

            for (Map<String, Object> map : maps) {
                if (map.containsKey(key)) {
                    map.remove(key);
                }
            }
            return toString(maps);
        } else {
            try {
                Map<Object, Object> map = OBJECT_MAPPER.readValue(json, Map.class);
                if (map.containsKey(key)) {
                    map.remove(key);
                }
                return OBJECT_MAPPER.writeValueAsString(map);
            } catch (IOException e) {
                e.printStackTrace();
                return json;
            }
        }
    }

    /**
     * Return updated object from json
     *
     * @param json   json
     * @param object object
     * @return updated object
     */
    public static <T> T updateFromStringByClone(String json, final T object) {
        T cloneEntity = clone(object);
        try {
            OBJECT_MAPPER.readerForUpdating(cloneEntity).readValue(json);
            return cloneEntity;
        } catch (IOException e) {
            throw new IllegalArgumentException("The given json value: "
                    + json + " cannot be transformed for object update: "
                    + cloneEntity, e);
        }
    }

    /**
     * @param value object
     * @return json string
     */
    public static String toString(Object value) {
        if (isNull(value)) {
            return EMPTY_OBJECT_JSON;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                    + value + " cannot be transformed to a String", e);
        }
    }

    /**
     * Convert to json by passed jsonView class
     *
     * @param value    object
     * @param jsonView jsonView
     * @return json
     */
    public static String toStringWithJsonView(Object value, Class<?> jsonView) {
        if (isNull(value)) {
            return EMPTY_OBJECT_JSON;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper
                    .writerWithView(jsonView)
                    .writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                    + value + " cannot be transformed to a String", e);
        }
    }

    /**
     * Important! if same keys exist replaces from jsonSecond
     *
     * @param jsonFirst  jsonFirst
     * @param jsonSecond jsonSecond
     * @return merged json
     */
    public static String merge(String jsonFirst, String jsonSecond) {
        Map<String, Object> mapFirst = fromStringToMap(jsonFirst);
        Map<String, Object> mapSecond = fromStringToMap(jsonSecond);

        for (Map.Entry<String, Object> entry : mapSecond.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            mapFirst.put(key, value);
        }

        return toString(mapFirst);
    }

    /**
     * Returns a json with an updated full set of fields of internal objects
     * Ex.: {"tubularAssemblyElementType": {"id": 1}} => {"tubularAssemblyElementType": {"id": 1, "name": "Type"}}
     *
     * @param fullEntity fullEntity
     * @param updateEntity updateEntity or String
     * @return json
     */
    public static String refreshInnerFields(Object fullEntity, Object updateEntity) {
        return JacksonUtil.toString(getActualMap(fullEntity, updateEntity));
    }

    public static Map<String, Object> getActualMap(Object fullEntity, Object updateEntity) {
        Map<String, Object> fullState = JacksonUtil.convertObjectToMap(fullEntity);
        Map<String, Object> partState;
        if (updateEntity instanceof String)
            partState = getMap((String) updateEntity);
        else
            partState = JacksonUtil.convertObjectToMap(updateEntity);
        Map<String, Object> result = new HashMap<>(partState);

        for (String key : partState.keySet()) {
            if (fullState.containsKey(key) && partState.get(key) != null) {
                result.put(key, fullState.get(key));
            }
        }
        return result;
    }

    public static Map<String, Object> getActualMapWithJsonView(Object fullEntity, Object updateEntity, Class<?> jsonView) {
        Map<String, Object> fullState = JacksonUtil.convertObjectToMapWithJsonView(fullEntity, jsonView);
        Map<String, Object> partState;
        if (updateEntity instanceof String)
            partState = getMap((String) updateEntity);
        else
            partState = JacksonUtil.convertObjectToMapWithJsonView(updateEntity, jsonView);
        Map<String, Object> result = new HashMap<>(partState);

        for (String key : partState.keySet()) {
            if (fullState.containsKey(key) && partState.get(key) != null) {
                result.put(key, fullState.get(key));
            }
        }
        return result;
    }

    /**
     * @param value object
     * @return json string without null fields
     */
    public static String toStringWithoutNullFields(Object value) {
        return toStringWithParams(value, (m) -> {
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        });
    }

    /**
     * @param value value
     * @param jsonView jsonView
     * @return json string without null fields with jsonView
     */
    public static String toStringWithoutNullFieldsWithJsonView(Object value, Class<?> jsonView) {
        return toStringWithParams(value, (m) -> {
            m.writerWithView(jsonView);
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        });
    }

    /**
     * @param value object
     * @return json string without null fields
     */
    public static String toStringWithoutNullFieldsAndEmptyArray(Object value) {
        return toStringWithParams(value, (m) -> {
            m.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        });
    }

    /**
     * @param value value
     * @param jsonView jsonView
     * @return json string without null fields with jsonView
     */
    public static String toStringWithoutNullFieldsAndEmptyArrayWithJsonView(Object value, Class<?> jsonView) {
        return toStringWithParams(value, (m) -> {
            m.writerWithView(jsonView);
            m.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        });
    }

    /**
     * @param value object
     * @return json string without null fields
     */
    private static String toStringWithParams(Object value, Consumer<ObjectMapper> consumer) {
        if (isNull(value)) {
            return EMPTY_OBJECT_JSON;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            consumer.accept(mapper);
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                    + value + " cannot be transformed to a String", e);
        }
    }

    public static JsonNode toJsonNode(String value) {
        try {
            return OBJECT_MAPPER.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Deep clone
     *
     * @param value value
     * @param <T>   T
     * @return cloned object
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T value) {
        return fromString(toString(value), (Class<T>) value.getClass());
    }

    /**
     * @param jsonStr jsonStr
     * @return beauty json
     */
    public static String prettyJson(String jsonStr) {
        if (isNull(jsonStr)) {
            return EMPTY_OBJECT_JSON;
        }

        try {
            Object json = OBJECT_MAPPER.readValue(jsonStr, Object.class);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot beatify passed json string: " + jsonStr, e);
        }
    }
}