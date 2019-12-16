package hello.service.impl;

import hello.entity.User;
import hello.service.UserCacheableService;
import hello.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCacheableServiceImpl implements UserCacheableService {

    private UserService userService;

    public UserCacheableServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Cacheable(value="user", key="#id")
    public User getById(Long id) {
        return userService.getById(id);
    }

    @Override
    @Cacheable(value="users")
    public List<User> getAll() {
        return userService.getAll();
    }
}
