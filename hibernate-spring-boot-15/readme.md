# Config Hibernate for Spring Boot 1.5

## Set from Jpa to Hibernate in Spring Boot

Spring boot focuses on using JPA to persist data in relational db and it has ability to create repository implementations automatically, at runtime, from a repository interface. But here we are trying to use hibernate as a JPA provider

Config session in BeanConfig.java:

```
@Configuration
public class BeanConfig {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public SessionFactory getSessionFactory() {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    return entityManagerFactory.unwrap(SessionFactory.class);
	}

}
```

and then use SessionFactory in DAO. Example:

```
@Component
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<UserDetails> getUserDetails() {
		Criteria criteria = sessionFactory.openSession().createCriteria(UserDetails.class);
		return criteria.list();
	}
}
```

## Config

1. `cp application.properties.dist application.properties`
2. set db name, login and password and spring.jpa.hibernate.ddl-auto = update
3. create database 
4. insert to your db file "sql.sql"
5. run application
6. open in browser http://localhost:8080 