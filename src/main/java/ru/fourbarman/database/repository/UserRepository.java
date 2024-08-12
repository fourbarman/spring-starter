package ru.fourbarman.database.repository;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import ru.fourbarman.database.pool.ConnectionPool;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Repository
public class UserRepository {
    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
}
