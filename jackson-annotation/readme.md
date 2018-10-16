# Jackson Annotation Examples

* `@JsonAnyGetter` -  allows the flexibility of using a Map field as standard properties.
* `@JsonAnySetter` -  allows you the flexibility of using a Map as standard properties. On de-serialization, the properties from JSON will simply be added to the map.
* `@JsonGetter` -  is an alternative to the @JsonProperty annotation to mark the specified method as a getter method.
* `@JsonPropertyOrder` -  is used to specify the order of properties on serialization.
* `@JsonRawValue` -  is used to instruct the Jackson to serialize a property exactly as is.
* `@JsonRootName` -  is used – if wrapping is enabled – to specify the name of the root wrapper to be used.
* `@JsonCreator` - is used to tune the constructor/factory used in deserialization.
* `@JsonIgnoreProperties` - one of the most common annotations in Jackson - is used to mark a property or a list of properties to be ignored at the class level.
* `@JsonIgnore` - is used to mark a property to be ignored at the field level.
* `@JsonInclude` - is used to exclude properties with empty/null/default values.
