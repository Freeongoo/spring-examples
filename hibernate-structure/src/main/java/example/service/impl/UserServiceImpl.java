package example.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.dao.UserDao;
import example.entity.User;
import example.exceptions.DuplicateFieldException;
import example.exceptions.ERROR_CODES;
import example.exceptions.PropertyNotFoundException;
import example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Collection<User> getAllUsers() {
        return dao.getAll();
    }

    @Override
    public Collection<User> getAllValidUsers() {
        return dao.getAllValidUsers();
    }

    @Override
    public User getUserById(Long id) {
        return dao.getByKey(id);
    }

    @Override
    public User getUserByUsername(String userName) {
        return null;
    }

    @Override
    public User create(String json) throws Exception {
        User newUser = new ObjectMapper().readValue(json, User.class);

        if (dao.getByUserName(newUser.getUserName()) != null){
            throw new DuplicateFieldException("this username - '" + newUser.getUserName() + "' already using", ERROR_CODES.DUPLICATED_FIELDS);
        }

        newUser.setActivated(true);

        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()){
            // set password
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        } else {
            // default password
            newUser.setPassword(passwordEncoder.encode("123456"));
        }

        validate(newUser);

        dao.persist(newUser);

        return newUser;
    }

    @Override
    public User update(Long id, String json) throws Exception {
        return null;
    }

    @Override
    public User delete(Long id) throws Exception {
        return null;
    }

    @Override
    public User updateEntity(User user) throws Exception {
        return null;
    }

    private void validate (User user) throws PropertyNotFoundException {
        if (user.getFirstName() == null)
            throw new PropertyNotFoundException("firstName", ERROR_CODES.PROPERTY_MISSING);
        if (user.getLastName() == null)
            throw new PropertyNotFoundException ("lastName", ERROR_CODES.PROPERTY_MISSING);
        if (user.getEmail() == null)
            throw new PropertyNotFoundException ("email", ERROR_CODES.PROPERTY_MISSING);
        if (user.getUserName() == null)
            throw new PropertyNotFoundException ("username", ERROR_CODES.PROPERTY_MISSING);
        if (user.getPassword() == null)
            throw new PropertyNotFoundException ("password", ERROR_CODES.PROPERTY_MISSING);
    }
}
