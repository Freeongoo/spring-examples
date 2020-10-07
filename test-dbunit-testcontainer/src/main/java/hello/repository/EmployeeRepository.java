package hello.repository;

import hello.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by name
     *
     * @param name name
     * @return Optional<Employee>
     */
    Optional<Employee> findByName(String name);
}
