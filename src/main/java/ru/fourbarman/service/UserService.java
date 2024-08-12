package ru.fourbarman.service;

import org.springframework.stereotype.Service;
import ru.fourbarman.database.entity.Company;
import ru.fourbarman.database.repository.CompanyRepository;
import ru.fourbarman.database.repository.CrudRepository;
import ru.fourbarman.database.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;

    public UserService(UserRepository userRepository, CrudRepository<Integer, Company> companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
}
