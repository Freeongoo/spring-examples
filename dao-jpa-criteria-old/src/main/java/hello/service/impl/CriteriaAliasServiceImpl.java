package hello.service.impl;

import hello.container.FieldHolder;
import hello.service.CriteriaAliasService;
import hello.util.EntityFieldUtils;
import hello.util.StringDelimiterUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class CriteriaAliasServiceImpl implements CriteriaAliasService {

    @Override
    public Set<String> getCriteriaAlias(Collection<FieldHolder> fieldHolders) {
        requireNonNull(fieldHolders, "Cannot passed null collection of fieldHolders");

        return fieldHolders.stream()
                .map(this::getCriteriaAlias)
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    @Override
    public Set<String> getCriteriaAlias(Map<String, List<?>> props) {
        requireNonNull(props, "Cannot passed null map of props");

        return props.entrySet().stream()
                .map(m -> getCriteriaAliasByRelationFieldName(m.getKey()))
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    private Set<String> getCriteriaAlias(FieldHolder fieldHolder) {
        String relationFieldName = fieldHolder.getRelationFieldName();
        if (relationFieldName == null) {
            return emptySet();
        }

        String fullFieldName = EntityFieldUtils.concat(relationFieldName, fieldHolder.getName());
        return getCriteriaAliasByRelationFieldName(fullFieldName);
    }

    @Override
    public Set<String> getCriteriaAliasByRelationFieldName(String fieldName) {
        requireNonNull(fieldName, "Cannot passed null fieldName");
        Set<String> aliasStore = new HashSet<>();

        int i = 0;
        while (true) {
            Optional<String> substring = StringDelimiterUtils.getSubString(fieldName, EntityFieldUtils.FIELD_DELIMITER, i);
            if (!substring.isPresent()) break;
            aliasStore.add(substring.get());
            i++;
        }
        return aliasStore;
    }
}
