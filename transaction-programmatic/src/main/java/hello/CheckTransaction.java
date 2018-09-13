package hello;

import hello.model.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class CheckTransaction {
    private final EmployeeService employeeService;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public CheckTransaction(EmployeeService employeeService, TransactionTemplate transactionTemplate) {
        this.employeeService = employeeService;
        this.transactionTemplate = transactionTemplate;
    }

    // with return value
    public int correctCreateNewEmployee() {
        return (int) transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus status) {
                return employeeService.insertWithReturnInsertedId(new Employee("Free22", "test22@test.com"));
            }
        });
    }

    // with not return value
    public void insertSameEmails() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                employeeService.insert(new Employee("Free", "test@test.com"));
                employeeService.insert(new Employee("Free2", "test@test.com"));
            }
        });
    }
}
