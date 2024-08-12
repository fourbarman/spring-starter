package ru.fourbarman.spring.database.repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import ru.fourbarman.spring.bpp.Auditing;
import ru.fourbarman.spring.bpp.Transaction;
import ru.fourbarman.spring.database.entity.Company;
import ru.fourbarman.spring.database.pool.ConnectionPool;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

//@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
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
