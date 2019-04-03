package hello.service;

import hello.container.FieldHolder;

import java.util.*;

public interface CriteriaAliasService {

    /**
     * @param fieldHolders fieldHolders
     * @return set of alias
     */
    public Set<String> getCriteriaAlias(Collection<FieldHolder> fieldHolders);

    /**
     * @param props props
     * @return set of alias
     */
    public Set<String> getCriteriaAlias(Map<String, List<?>> props);

    /**
     * @param fieldName fullNameField
     * @return set of alias
     */
    public Set<String> getCriteriaAliasByRelationFieldName(String fieldName);
}
