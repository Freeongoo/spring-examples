package hello.dao.impl;

import hello.dao.EmployeeDao;
import hello.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.toIntExact;

@Repository
public class EmployeeDaoImpl extends JdbcDaoSupport implements EmployeeDao {
    private final String SQL_SELECT_ALL   = "SELECT * FROM employee";
    private final String SQL_DELETE_BY_ID   = "DELETE FROM employee WHERE id = ?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM employee WHERE id = ?";
    private final String SQL_SELECT_BY_EMAIL = "SELECT * FROM employee WHERE email = ?";
    private final String SQL_INSERT_NEW   = "INSERT INTO employee (name, email) VALUES (?, ?)";

    private final DataSource dataSource;

    @Autowired
    public EmployeeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public void insert(Employee employee) {
        getJdbcTemplate().update(SQL_INSERT_NEW, employee.getName(), employee.getEmail());
    }

    @Override
    public int insertWithReturnInsertedId(Employee employee) {
        KeyHolder key = new GeneratedKeyHolder();
        getJdbcTemplate().update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(SQL_INSERT_NEW, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            return ps;
        }, key);

        return key.getKey().intValue();
    }

    @Override
    public void insertList(final List<Employee> employees) {
        getJdbcTemplate().batchUpdate(SQL_INSERT_NEW, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Employee employee = employees.get(i);
                ps.setString(1, employee.getName());
                ps.setString(2, employee.getEmail());
            }

            public int getBatchSize() {
                return employees.size();
            }
        });

    }
    @Override
    public List<Employee> getAll(){
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(SQL_SELECT_ALL);

        List<Employee> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Employee emp = new Employee(
                    toIntExact((Long)row.get("id")),
                    (String)row.get("name"),
                    (String)row.get("email")
            );
            result.add(emp);
        }

        return result;
    }

    @Override
    public Employee getById(int employeeId) {
        try {
            return getEmployeeQueryForObject(SQL_SELECT_BY_ID, new Object[]{employeeId});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Employee getByEmail(String email) {
        try {
            return getEmployeeQueryForObject(SQL_SELECT_BY_EMAIL, new Object[]{email});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Employee employee) {
        getJdbcTemplate().update(SQL_DELETE_BY_ID, employee.getId());
    }

    private Employee getEmployeeQueryForObject(String sql, @Nullable Object[] args) {
        return getJdbcTemplate().queryForObject(
                sql,
                args,
                (rs, rwNumber) -> new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                )
        );
    }
}