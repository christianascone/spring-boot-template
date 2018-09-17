package com.template.spring.springtemplate.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AbstractEntity<T> {

    @Transient
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED", insertable = true, updatable = false)
    @JsonIgnore
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_MODIFIED", insertable = false, updatable = true)
    @JsonIgnore
    private Date dateModified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_DELETED", insertable = false, updatable = true)
    @JsonIgnore
    private Date dateDeleted;

    @JsonIgnore
    @Column(name = "DELETED")
    private boolean deleted = false;

    @Version
    @Column(name = "VERSION")
    private long version = 0;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        AbstractEntity object = (AbstractEntity) o;
        return Objects.equals(id, object.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass().hashCode(), id);
    }

    @PrePersist
    public void prePersist() {
        setDateCreated(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        setDateModified(new Date());
        if (isDeleted() && getDateDeleted() == null) {
            setDateDeleted(getDateModified());
        }
    }

    @Override
    public String toString() {
        return String
                .format("%s [ id()=%d, dateCreated()=%s, dateModified()=%s, dateDeleted()=%s, deleted()=%b, version()=%s [ @Transient [ ], @Inherit [ ] ] ]",
                        AbstractEntity.class.getSimpleName(), getId(),
                        getDateCreated(), getDateModified(), getDateDeleted(),
                        isDeleted(), getVersion()
                        /* @Transient */
                        /* @Inherit */);
    }
}
