package ru.fourbarman.database.repository;

import org.springframework.stereotype.Repository;
import ru.fourbarman.database.pool.ConnectionPool;

@Repository
public class UserRepository {
    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
}
