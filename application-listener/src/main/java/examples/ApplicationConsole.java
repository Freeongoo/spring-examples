package examples;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationConsole implements CommandLineRunner {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationConsole.class);

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsole.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}