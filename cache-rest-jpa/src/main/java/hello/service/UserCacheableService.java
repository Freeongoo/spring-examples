package hello.service;

import hello.entity.User;

import java.util.List;

public interface UserCacheableService {

    User getById(Long id);

    List<User> getAll();
}
