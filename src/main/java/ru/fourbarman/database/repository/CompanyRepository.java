package ru.fourbarman.database.repository;

import ru.fourbarman.bpp.Auditing;
import ru.fourbarman.bpp.InjectBean;
import ru.fourbarman.bpp.Transaction;
import ru.fourbarman.database.entity.Company;
import ru.fourbarman.database.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {
    @InjectBean
    private ConnectionPool connectionPool;

    @PostConstruct
    private void init() {
        System.out.println("Init CompanyRepository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("FindById method called");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("Delete method called");
    }


}
