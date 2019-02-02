package hello.service.dto.impl;

import hello.entity.dto.Chair;
import hello.repository.dto.ChairRepository;
import hello.service.AbstractService;
import hello.service.dto.ChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ChairServiceImpl extends AbstractService<Chair, Long> implements ChairService {

    private ChairRepository repository;

    @Autowired
    public ChairServiceImpl(ChairRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<Chair, Long> getRepository() {
        return repository;
    }
}
