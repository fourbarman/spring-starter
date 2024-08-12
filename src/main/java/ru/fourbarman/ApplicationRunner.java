package ru.fourbarman;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.fourbarman.database.pool.ConnectionPool;
import ru.fourbarman.database.repository.CompanyRepository;
import ru.fourbarman.database.repository.CrudRepository;

/**
 * Hello world!
 *
 */
public class ApplicationRunner {
    public static void main( String[] args ) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {
            ConnectionPool pool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(pool);
            var companyRepository = context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
        }
    }
}
