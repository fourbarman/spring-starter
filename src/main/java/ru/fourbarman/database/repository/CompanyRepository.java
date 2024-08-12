package ru.fourbarman.database.repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.fourbarman.bpp.Auditing;
import ru.fourbarman.bpp.Transaction;
import ru.fourbarman.database.entity.Company;
import ru.fourbarman.database.pool.ConnectionPool;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

//@Repository
@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool pool1;
    private final List<ConnectionPool> pools;
    private final Integer poolSize;

    public CompanyRepository(ConnectionPool pool1,
                             List<ConnectionPool> pools,
                             @Value("${db.pool.size}") Integer poolSize) {
        this.pool1 = pool1;
        this.pools = pools;
        this.poolSize = poolSize;
    }

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
