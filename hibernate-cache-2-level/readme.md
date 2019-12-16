# Learn Hibernate Cache Second Level

So when does cache work?
* When you session.get() or session.load() the object that was previously selected and resides in cache. Cache is a storage where ID is the key and the properties are the values. So only when there is a possibility to search by ID you could eliminate hitting the DB.
* When your associations are lazy-loaded (or eager-loaded with selects instead of joins)

Hibernate Cache Second Level doesn't work when:
* If you don't select by ID. Again - 2nd level cache stores a map of entities' IDs to other properties (it doesn't actually store objects, but the data itself), so if your lookup looks like this: from Authors where name = :name, then you don't hit cache.
* When you use HQL (even if you use where id = ?).
* If in your mapping you set fetch="join", this means that to load associations joins will be used everywhere instead of separate select statements. Process level cache works on children objects only if fetch="select" is used.
* Even if you have fetch="select" but then in HQL you use joins to select associations - those joins will be issued right away and they will overwrite whatever you specified in hbm.xml or annotations.

## Configuration

For main and test dirs:
* `cp application.properties.dist application.properties`
* `cp application-test.properties.dist application-test.properties`

## Cache Config

#### 1. Add deps

Important! Add same version like Hibernate.

```
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-ehcache</artifactId>
    <version>${hibernate.version}</version>
</dependency>
```

#### 2. Add application.properties

```
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.use_query_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory
spring.jpa.properties.javax.persistence.sharedCache.mode=ENABLE_SELECTIVE
```

for logging cache:

```
logging.level.org.hibernate.cache = TRACE
logging.level.net.sf.ehcache=TRACE
```

`ENABLE_SELECTIVE` - Caching is enabled for all entities for `Cacheable(true)`
`ALL` - All entities and entity-related state and data are cached.

#### 3. @EnableCaching in Application

```
@EnableCaching
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
    }       
}            
```

#### 4. Annotated Entity Cacheable

```
@Entity
@Cacheable
@org.hibernate.annotations.Cache(region = "referenceCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class GoodReadOnly extends AbstractBaseEntity<Long> {
}
```

## Testing

### CacheConcurrencyStrategy.READ_ONLY

/src/test/java/hello/entity/cache/GoodReadOnlyTest.java

Notes! in method `findByName_CacheLevelTwoNotWork_ShouldBeMoreThanOneQuery` hibernate cache level 2 not work.
For such methods you need to use a different cache - for example, the spring cache (`findByName_UsingCacheFromSpring_ShouldBeOneQuery`)