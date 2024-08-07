package ru.fourbarman;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.fourbarman.database.pool.ConnectionPool;

/**
 * Hello world!
 *
 */
public class ApplicationRunner {
    public static void main( String[] args ) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        ConnectionPool pool = context.getBean("pool1", ConnectionPool.class);
        System.out.println(pool);
    }
}
