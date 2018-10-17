package example.service;

import example.entity.User;

import java.util.Collection;

public interface UserService {
	Collection<User> getAllUsers();
	Collection<User> getAllValidUsers();
	User getUserById(Long id);
	User getUserByUsername(String userName);
	User create(String json) throws Exception;
	User update(Long id, String json) throws Exception;
	User delete(Long id) throws Exception;
	User updateEntity(User user) throws Exception;
}