package ru.fourbarman.spring.service;

import org.springframework.stereotype.Service;
import ru.fourbarman.spring.database.entity.Company;
import ru.fourbarman.spring.database.repository.CompanyRepository;
import ru.fourbarman.spring.database.repository.CrudRepository;
import ru.fourbarman.spring.database.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;

    public UserService(UserRepository userRepository, CrudRepository<Integer, Company> companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
}
