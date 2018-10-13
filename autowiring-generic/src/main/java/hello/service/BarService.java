package hello.service;

import hello.dao.GeneralDao;
import hello.model.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BarService")
public class BarService implements GeneralService<Bar> {
    @Autowired
    private GeneralDao<Bar> barDao;

    @Override
    public Bar get(Long id) {
        return barDao.get(id);
    }
}
