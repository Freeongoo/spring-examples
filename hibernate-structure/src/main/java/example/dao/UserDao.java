package example.dao;

import example.entity.User;

import java.util.List;

public interface UserDao extends Dao<Long, User>{
    List<User> getAllValidUsers ();
    User getByUserName (String userName);
    User getByEmail (String email);
    User getByUsernameCaseInsensitive(String userName);
}
