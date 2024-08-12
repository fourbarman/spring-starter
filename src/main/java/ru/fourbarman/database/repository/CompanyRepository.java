package ru.fourbarman.database.repository;

import ru.fourbarman.bpp.InjectBean;
import ru.fourbarman.database.pool.ConnectionPool;

public class CompanyRepository {
    @InjectBean
    private ConnectionPool connectionPool;
}
