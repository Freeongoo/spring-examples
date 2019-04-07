package hello.entity.composite;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RightDictionaryItemId implements Serializable {

    private static final long serialVersionUID = -8829162248911070836L;

    @Column(name = "id")
    private String id;

    @Column(name = "specific_protected_object_id")
    private Long specificProtectedObjectId;

    public RightDictionaryItemId() {
    }

    public RightDictionaryItemId(String id, Long specificProtectedObjectId) {
        this.id = id;
        this.specificProtectedObjectId = specificProtectedObjectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSpecificProtectedObjectId() {
        return specificProtectedObjectId;
    }

    public void setSpecificProtectedObjectId(Long specificProtectedObjectId) {
        this.specificProtectedObjectId = specificProtectedObjectId;
    }
}
