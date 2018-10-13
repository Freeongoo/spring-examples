package hello.service;

import hello.dao.GeneralDao;
import hello.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooService implements GeneralService<Foo> {
    @Autowired
    private GeneralDao<Foo> fooDao;

    @Override
    public Foo get(Long id) {
        return fooDao.get(id);
    }
}
