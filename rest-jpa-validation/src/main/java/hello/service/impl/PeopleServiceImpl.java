package hello.service.impl;

import hello.repository.springValidation.PeopleRepository;
import hello.service.PeopleService;
import hello.springValidation.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImpl extends AbstractService<People> implements PeopleService {

    @Autowired
    private PeopleRepository repository;

    @Override
    protected CrudRepository<People, Long> getRepository() {
        return repository;
    }
}
