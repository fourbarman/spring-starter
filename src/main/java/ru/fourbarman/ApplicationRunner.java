package ru.fourbarman;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.fourbarman.database.pool.ConnectionPool;
import ru.fourbarman.database.repository.CompanyRepository;

/**
 * Hello world!
 *
 */
public class ApplicationRunner {
    public static void main( String[] args ) {
        CompanyRepository companyRepository;
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {
            ConnectionPool pool = context.getBean("p1", ConnectionPool.class);
            System.out.println(pool);
            companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            System.out.println(companyRepository);
        }
    }
}
