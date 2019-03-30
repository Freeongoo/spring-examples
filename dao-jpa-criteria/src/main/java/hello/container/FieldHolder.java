package hello.container;

/**
 * Container for holder entity's fields
 */
public class FieldHolder {

    private String name;

    private Object value;

    /**
     * If relationFieldName null - field without relation
     * if relationFieldName not null - name of relation object.
     */
    private String relationFieldName;

    public FieldHolder() {}

    public FieldHolder(String name, Object value, String relationFieldName) {
        this(name, value);
        this.relationFieldName = relationFieldName;
    }

    public FieldHolder(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getRelationFieldName() {
        return relationFieldName;
    }

    public void setRelationFieldName(String relationFieldName) {
        this.relationFieldName = relationFieldName;
    }
}
