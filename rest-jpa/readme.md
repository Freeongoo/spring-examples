# Example jpa rest

## Testing

When try test exception handle don't forget add advice in MockMvc:

```
@Before
public void setup() {
    this.mockMvc = standaloneSetup(this.employeeController)
            .setControllerAdvice(new EmployeeNotFoundAdvice())
            .build(); // Standalone context
}
```

```
@Test
public void one_WhenNotExist() throws Exception {
    int idNotExist = -1;

    this.mockMvc.perform(get("/employees/" + idNotExist))
            .andExpect(status().isNotFound());
}
```