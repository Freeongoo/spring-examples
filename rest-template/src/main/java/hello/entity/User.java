package hello.entity;

import java.util.Objects;

/**
 * @author dorofeev
 */
public class User {
    private Long id;
    private Long userId;
    private String title;
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id) &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(title, user.title) &&
                Objects.equals(body, user.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, body);
    }
}
