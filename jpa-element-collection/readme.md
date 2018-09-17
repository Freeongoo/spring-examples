# Hibernate ElementCollection

We use @ElementCollection annotation to declare an element-collection mapping. All the records of the collection are stored in a separate table. The configuration for this table is specified using the @CollectionTable annotation.

The @CollectionTable annotation is used to specify the name of the table that stores all the records of the collection, and the JoinColumn that refers to the primary table.

Moreover, When you’re using an Embeddable type with Element Collection, you can use the @AttributeOverrides and @AttributeOverride annotations to override/customize the fields of the embeddable type.