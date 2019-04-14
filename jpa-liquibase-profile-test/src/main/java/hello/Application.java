package hello;

import hello.persistence.entities.Book;
import hello.persistence.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootApplication
public class Application implements ApplicationRunner {

    @Autowired
    private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Iterable<Book> books = bookRepository.findAll();
        System.out.println(books);
    }
}
