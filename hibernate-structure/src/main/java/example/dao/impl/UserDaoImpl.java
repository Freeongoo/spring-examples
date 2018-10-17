package example.dao.impl;

import example.dao.AbstractBaseEntityDao;
import example.dao.UserDao;
import example.entity.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends AbstractBaseEntityDao<Long, User> implements UserDao {
    @Override
    protected Class<User> getPersistentClass() {
        return User.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllValidUsers() {
        return (List<User>) createEntityCriteria()
                .add(Restrictions.isNotNull("firstName"))
                .add(Restrictions.isNotNull("lastName")).list();
    }

    @Override
    public User getByUserName(String userName) {
        try {
            return (User) createEntityCriteria()
                    .add(Restrictions.eq("userName", userName)).list().get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        } catch (Exception e) {
            // log.error(PREFIX, e);
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        return (User) createEntityCriteria()
                .add(Restrictions.eq("email", email)).list().get(0);
    }

    @Override
    public User getByUsernameCaseInsensitive(String userName) {
        return (User) createEntityCriteria()
                .add(Restrictions.eq("userName", userName).ignoreCase()).list().get(0);
    }
}
