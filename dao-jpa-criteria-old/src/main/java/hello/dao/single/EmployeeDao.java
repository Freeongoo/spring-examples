package hello.dao.single;

import hello.dao.AbstractDao;
import hello.entity.single.Employee;

import java.util.List;

public interface EmployeeDao extends AbstractDao<Employee, Long> {

    public List<Employee> findByName(String name);
}
