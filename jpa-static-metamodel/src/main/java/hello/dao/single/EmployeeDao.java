package hello.dao.single;

import hello.dao.BaseDao;
import hello.entity.single.Employee;

import java.util.List;

public interface EmployeeDao extends BaseDao<Employee, Long> {

    public List<Employee> findByName(String name);
}
