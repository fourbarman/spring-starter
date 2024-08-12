package ru.fourbarman;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.fourbarman.config.ApplicationConfiguration;
import ru.fourbarman.database.pool.ConnectionPool;
import ru.fourbarman.database.repository.CrudRepository;

/**
 * Hello world!
 *
 */
public class ApplicationRunner {
    public static void main( String[] args ) {
        try (var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
            ConnectionPool pool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(pool);
            var companyRepository = context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
        }
    }
}
