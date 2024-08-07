package ru.fourbarman;

import ru.fourbarman.database.ioc.Container;
import ru.fourbarman.database.pool.ConnectionPool;
import ru.fourbarman.database.repository.CompanyRepository;
import ru.fourbarman.database.repository.UserRepository;
import ru.fourbarman.service.UserService;

/**
 * Hello world!
 *
 */
public class ApplicationRunner {
    public static void main( String[] args ) {
        Container container = new Container();
//        ConnectionPool connectionPool = new ConnectionPool();
//        UserRepository userRepository = new UserRepository(connectionPool);
//        CompanyRepository companyRepository = new CompanyRepository(connectionPool);
//        UserService userService = new UserService(userRepository, companyRepository);

        // Inversion Of Control
//        ConnectionPool connectionPool = container.get(ConnectionPool.class);
//        UserRepository userRepository = container.get(UserRepository.class);
//        CompanyRepository companyRepository = container.get(CompanyRepository.class);
        UserService userService = container.get(UserService.class);
        //TODO UserService
    }
}
