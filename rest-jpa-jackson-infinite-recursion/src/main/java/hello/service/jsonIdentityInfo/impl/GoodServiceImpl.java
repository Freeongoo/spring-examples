package hello.service.jsonIdentityInfo.impl;

import hello.entity.jsonIdentityInfo.Good;
import hello.repository.jsonIdentityInfo.GoodRepository;
import hello.service.AbstractService;
import hello.service.jsonIdentityInfo.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class GoodServiceImpl extends AbstractService<Good, Long> implements GoodService {

    private GoodRepository repository;

    @Autowired
    public GoodServiceImpl(GoodRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<Good, Long> getRepository() {
        return repository;
    }
}
