# Example create DAO with Criteria Hibernate (@deprecated (since 5.2) for Session)

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Universal methods for create dynamical query - soo cool

See at `AbstractBaseDao`:
```
   /**
     * When passed empty map or all not exist fields - return all
     * When passed existing fields by not exist values - return empty list
     *
     * For create query with relation object - pass field in format: "<fieldName>.id" (Ex.: "post.id")
     * and for relation field you can passed only one value
     *
     * @param props props
     * @return list of entities
     */
    public List<T> getByProps(Map<String, List<?>> props);

    /**
     * When passed empty fieldHolders - return empty list
     * When passed all not exist fields name - return empty list
     *
     * @param fieldHolders fieldHolders
     * @return list of entities
     */
    public List<T> getByFields(Collection<FieldHolder> fieldHolders);
```
