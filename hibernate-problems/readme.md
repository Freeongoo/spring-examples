# Find and fix some hibernate problems

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## N+1 issue

see problem: `/src/test/java/hello/entity/nPlusOneProblem/NPlusOneProblemPostTest.java`

### Solutions

#### 1. Using batch fetching (simplest way)

##### Local in Entity

Using annotation: `@BatchSize(size=100)`

see solutions: `/src/test/java/hello/entity/nPlusOneProblem/solution/batchSize/ClientTest.java`

##### Global for all entities

add in application.properties:

```
spring.jpa.properties.hibernate.batch_fetch_style = PADDED
spring.jpa.properties.hibernate.default_batch_fetch_size = 25
```

#### 2. Using join fetch

##### HQL fetch

```
List<Author> authors = getSession().createQuery(
        "select a from hello.entity.nPlusOneProblem.solution.joinFetch.Author a join fetch a.books")
        .list();

authors.forEach(a -> a.getBooks().size());

AssertSqlCount.assertSelectCount(1);
``` 

##### Criteria fetch

```
CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
Root<Author> root = query.from(Author.class);

root.fetch("books", JoinType.INNER);

query.select(root)
        .distinct(true);

List<Author> authors = getSession()
        .createQuery(query)
        .getResultList();

authors.forEach(a -> a.getBooks().size());

AssertSqlCount.assertSelectCount(1);
```