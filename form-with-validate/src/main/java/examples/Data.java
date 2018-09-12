package examples;

import javax.validation.constraints.*;

public class Data {

    @Min(value = 1L, message = "The value must be more than zero")
    @NotNull(message="id cannot be null.")
    private long id;

    @NotNull(message="content cannot be empty.")
    @Size(min=1, max=20, message="content must be between 1 and 20 characters long.")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message="content must be alphanumeric with no spaces")
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
