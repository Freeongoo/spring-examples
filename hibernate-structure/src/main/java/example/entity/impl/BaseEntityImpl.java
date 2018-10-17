package example.entity.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import example.entity.BaseEntity;
import example.utils.JsonUtil;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntityImpl implements BaseEntity {
    private String creator;
    private String modified;
    private Date modificationTime;
    private Date creationTime;

    @Override
    public void prepare() {
        // do in children
    }

    @Override
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    @Override
    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    @Column(name = "modified")
    public String getModified() {
        return modified;
    }

    @Override
    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JsonUtil.DATE_FORMAT)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_time")
    public Date getModificationTime() {
        return modificationTime;
    }

    @Override
    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    @Override
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JsonUtil.DATE_FORMAT)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time")
    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
