package hello.entity.composite;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
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
