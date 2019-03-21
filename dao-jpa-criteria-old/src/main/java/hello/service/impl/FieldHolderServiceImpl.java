package hello.service.impl;

import hello.container.FieldHolder;
import hello.entity.BaseEntity;
import hello.service.FieldHolderService;
import hello.util.EntityFieldUtils;
import hello.util.HibernateUtils;
import hello.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
public class FieldHolderServiceImpl implements FieldHolderService {

    @Override
    public Collection<FieldHolder> getListFieldHolder(BaseEntity entity, Collection<String> fields) {
        return fields.stream()
                .map(fieldName -> getFieldHolder(entity, fieldName))
                .collect(toList());
    }

    private FieldHolder getFieldHolder(BaseEntity entity, String fieldName) {
        try {
            return getFieldHolderDependsOfFieldType(entity, fieldName);
        } catch (Exception e) {
            String msg = format("Try get content form field: '%s' entity: '%s'", fieldName, entity);
            throw new RuntimeException(msg, e);
        }
    }

    private FieldHolder getFieldHolderDependsOfFieldType(BaseEntity entity, String fieldName) {
        FieldHolder fieldHolder;
        Object fieldContent;

        if (EntityFieldUtils.isRelationField(fieldName)) {
            String relationAlias = EntityFieldUtils.getRelationFieldAlias(fieldName);
            String fieldNameRelation = EntityFieldUtils.getRelationFieldName(fieldName);
            fieldContent = getFieldRelationValue(entity, relationAlias, fieldNameRelation);
            fieldHolder = new FieldHolder(fieldNameRelation, fieldContent, relationAlias);
        } else {
            fieldContent = ReflectionUtils.getFieldContent(entity, fieldName);
            fieldHolder = new FieldHolder(fieldName, fieldContent);
        }

        return fieldHolder;
    }

    private Object getFieldRelationValue(BaseEntity entity, String relationAlias, String relationFieldName) {
        Object relationObject = ReflectionUtils.getFieldContent(entity, relationAlias);
        Object unproxyRelationObject = HibernateUtils.initializeAndUnproxy(relationObject);

        return ReflectionUtils.getFieldContent(unproxyRelationObject, relationFieldName);
    }
}
