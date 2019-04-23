# Learn Hibernate Cache Second Level

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

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

