package hello.container;

/**
 * Container for holder entity's fields
 */
public class FieldHolder {

    private String fieldName;

    /**
     * field value, may contain id of related object
     */
    private Object value;

    /**
     * "true" - means that the field "value" contains id related object
     * "false" - elementary type like: String, Wrapper, Primitive
     */
    private boolean isRelationId;

    public FieldHolder() {
    }

    private FieldHolder(String fieldName, Object value, boolean isRelationId) {
        this.fieldName = fieldName;
        this.value = value;
        this.isRelationId = isRelationId;
    }

    public static FieldHolder of(String fieldName, Object value, boolean isRelationId) {
        return new FieldHolder(fieldName, value, isRelationId);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean getIsRelationId() {
        return isRelationId;
    }

    public void setIsRelationId(boolean isRelationId) {
        this.isRelationId = isRelationId;
    }
}
