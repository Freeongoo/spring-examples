package example.entity;

import java.util.Date;

public interface BaseEntity {
    void prepare ();

    String getCreator();
    void setCreator(String creator);

    String getModified();
    void setModified(String modified);

    Date getModificationTime();
    void setModificationTime(Date modificationTime);

    Date getCreationTime();
    void setCreationTime(Date creationTime);
}