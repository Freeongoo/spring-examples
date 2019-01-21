package hello.service.jsonIdentityInfo.impl;

import hello.entity.jsonIdentityInfo.Good;
import hello.exception.ERROR_CODES;
import hello.exception.NotFoundException;
import hello.repository.jsonIdentityInfo.GoodRepository;
import hello.service.jsonIdentityInfo.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodServiceImpl implements GoodService {

    private GoodRepository repository;

    @Autowired
    public GoodServiceImpl(GoodRepository repository) {
        this.repository = repository;
    }

    @Override
    public Good get(Long id) {
        Optional<Good> good = repository.findById(id);
        return good
                .orElseThrow(() -> new NotFoundException("Cannot find good by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND));
    }

    @Override
    public List<Good> getAll() {
        return repository.findAll();
    }
}
