package hello.service;

import hello.container.FieldHolder;
import hello.entity.BaseEntity;

import java.util.Collection;

public interface FieldHolderService {

    /**
     * It is important to understand that the list of fields may contain the names of the fields of relational objects
     * Ex.: "well.id" or "event.name"
     *
     * @param entity entity
     * @param fields fields
     * @return Collection of FieldHolder
     */
    public Collection<FieldHolder> getListFieldHolder(final BaseEntity entity, final Collection<String> fields);
}
