package hello.dao.single.impl;

import hello.dao.impl.AbstractBaseDao;
import hello.dao.single.EmployeeDao;
import hello.entity.single.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EmployeeDaoImpl extends AbstractBaseDao<Employee, Long> implements EmployeeDao {

    @Override
    protected Class<Employee> getPersistentClass() {
        return Employee.class;
    }

    @Override
    public List<Employee> findByName(String name) {
        CriteriaQuery<Employee> criteriaQuery = getCriteriaQuery();
        Root<Employee> root = getRoot(criteriaQuery);
        Predicate predicate = getCriteriaBuilder().equal(root.get("name"), name);
        criteriaQuery
                .select(root)
                .where(predicate);

        return getSession()
                .createQuery(criteriaQuery)
                .getResultList();
    }
}
