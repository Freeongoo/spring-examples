package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    @Autowired
    CheckTransaction testJdbcTemplate;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        CheckTransaction checkTransaction = context.getBean(CheckTransaction.class);

        // run in transaction - if exception - all rollback
        try {
            checkTransaction.insertSameEmails();
        } catch (Exception e) {
            System.out.println(e);
        }

        checkTransaction.correctCreateNewEmployee();

        ((ConfigurableApplicationContext) context).close();
    }
}