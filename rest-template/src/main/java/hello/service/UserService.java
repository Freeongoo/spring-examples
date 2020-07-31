package hello.service;

import hello.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Long id);
}
