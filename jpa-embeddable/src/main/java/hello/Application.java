package hello;

import hello.model.Address;
import hello.model.Name;
import hello.model.User;
import hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAllInBatch();

        userRepository.deleteAllInBatch();

        Name name = new Name("Rajeev", "Kumar", "Singh");
        Address address = new Address("747", "Golf View Road", "Bangalore", "Karnataka", "India", "560008");
        User user = new User(name, "rajeev@callicoder.com", address);

        userRepository.save(user);
    }
}