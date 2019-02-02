package hello.service.dto.impl;

import hello.entity.dto.TableStaff;
import hello.repository.dto.TableStaffRepository;
import hello.service.AbstractService;
import hello.service.dto.TableStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class TableStaffServiceImpl extends AbstractService<TableStaff, Long> implements TableStaffService {

    private TableStaffRepository repository;

    @Autowired
    public TableStaffServiceImpl(TableStaffRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<TableStaff, Long> getRepository() {
        return repository;
    }
}
