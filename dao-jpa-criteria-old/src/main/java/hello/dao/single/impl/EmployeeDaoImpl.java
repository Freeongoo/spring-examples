package hello.dao.single.impl;

import hello.dao.impl.AbstractDaoImpl;
import hello.dao.single.EmployeeDao;
import hello.entity.single.Employee;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl extends AbstractDaoImpl<Employee, Long> implements EmployeeDao {

    @Override
    protected Class<Employee> getPersistentClass() {
        return Employee.class;
    }

    @Override
    public List<Employee> findByName(String name) {
        return createEntityCriteria()
                .add(Restrictions.eq("name", name))
                .list();
    }
}
