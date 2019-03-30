# Example JPA Static MetaModel

https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Installation

Add deps:

```
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-jpamodelgen</artifactId>
    <version>5.3.2.Final</version>
</dependency>
```

Add build plugin:
```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.7.0</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <encoding>UTF-8</encoding>
                <compilerArguments>
                    <processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
                </compilerArguments>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Run package: `mvn package`

See dir: `/target/classes/hello/entity/oneToMany/Author_.class`

## Config Intellij

File--Settings--Build--Compiler--Annotation Processors
* Enable Annotation Processor
* Obtain processors for project classpath
* Production source directory: `target/generated-sources/annotations`
* Test source directory: `target/generated-test-sources/test-annotations`
* Annotation processor:
    add: `org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor`
* Apply

After then run maven from intellij

## Using

Old - without static metaModel:

```
    CriteriaQuery<Comment> criteriaQuery = getCriteriaQuery();
    Root<Comment> root = getRoot(criteriaQuery);
    criteriaQuery.select(root);
    
    Join<Comment, Post> joinPost = root.join("post");
    criteriaQuery.where(getCriteriaBuilder().equal(joinPost.get("id"), postId));
    
    return getSession()
            .createQuery(criteriaQuery)
            .getResultList();
```

New - with static metaModel:

```
    CriteriaQuery<Comment> criteriaQuery = getCriteriaQuery();
    Root<Comment> root = getRoot(criteriaQuery);
    criteriaQuery.select(root);

    Join<Comment, Post> joinPost = root.join(Comment_.post);
    criteriaQuery.where(getCriteriaBuilder().equal(joinPost.get(Post_.id), postId));

    return getSession()
            .createQuery(criteriaQuery)
            .getResultList();
```
