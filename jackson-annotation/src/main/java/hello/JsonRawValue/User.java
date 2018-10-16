package hello.JsonRawValue;

import com.fasterxml.jackson.annotation.JsonRawValue;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 9180949792819321186L;

    private String name;

    @JsonRawValue
    private String json;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(json, user.json);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, json);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", json='").append(json).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
