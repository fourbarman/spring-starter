package ru.fourbarman.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import ru.fourbarman.bpp.Auditing;
import ru.fourbarman.bpp.InjectBean;
import ru.fourbarman.bpp.Transaction;
import ru.fourbarman.database.entity.Company;
import ru.fourbarman.database.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {
    //@Resource(name = "pool1")
    //@Autowired
//    @Qualifier("pool1")
    private ConnectionPool pool1;
    @Autowired
    private List<ConnectionPool> pools;
    @Value("${db.pool.size}")
    private Integer poolSize;

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

    @Autowired
    public void setPool1(ConnectionPool pool1) {
        this.pool1 = pool1;
    }
}
