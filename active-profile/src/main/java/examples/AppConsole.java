package examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootApplication
public class AppConsole implements CommandLineRunner {
    @Autowired
    ProfileManager profileManager;

    public static void main(String[] args) {
        SpringApplication.run(AppConsole.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        profileManager.getActiveProfiles();
    }
}