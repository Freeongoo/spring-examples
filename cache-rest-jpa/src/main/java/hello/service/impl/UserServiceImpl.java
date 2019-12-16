package hello.service.impl;

import hello.entity.User;
import hello.repository.UserRepository;
import hello.service.AbstractService;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User, Long> implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<User, Long> getRepository() {
        return repository;
    }
}
