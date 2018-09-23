# Config Hibernate for Spring Boot 2

## Set from Jpa to Hibernate in Spring Boot

Spring boot focuses on using JPA to persist data in relational db and it has ability to create repository implementations automatically, at runtime, from a repository interface. But here we are trying to use hibernate as a JPA provider

Use EntityManagerFactory for get session in DAO. Example:

```
@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public List getUserDetails() {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery criteria = builder.createQuery(UserDetails.class);
		Root contactRoot = criteria.from(UserDetails.class);
		criteria.select(contactRoot);
		return session.createQuery(criteria).getResultList();
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