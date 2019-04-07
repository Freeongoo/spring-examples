package hello.entity.composite.simpleFields;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "right_dictionary_item")
public class RightDictionaryItem {

    @EmbeddedId
    private RightDictionaryItemId rightDictionaryItemId;

    private String name;

    public RightDictionaryItemId getRightDictionaryItemId() {
        return rightDictionaryItemId;
    }

    public void setRightDictionaryItemId(RightDictionaryItemId rightDictionaryItemId) {
        this.rightDictionaryItemId = rightDictionaryItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RightDictionaryItem that = (RightDictionaryItem) o;
        return Objects.equals(rightDictionaryItemId, that.rightDictionaryItemId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rightDictionaryItemId);
    }
}
