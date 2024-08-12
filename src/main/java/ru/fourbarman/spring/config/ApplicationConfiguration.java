package ru.fourbarman.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import ru.fourbarman.spring.database.pool.ConnectionPool;
import ru.fourbarman.spring.database.repository.CrudRepository;
import ru.fourbarman.spring.database.repository.UserRepository;
import ru.fourbarman.web.config.WebConfiguration;

//@ImportResource("classpath:application.xml")
@Import(WebConfiguration.class)
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(
        basePackages = "ru.fourbarman.spring",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\..+Repository"),
        })
public class ApplicationConfiguration {

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ConnectionPool pool2(@Value("${db.username}") String username) {
        return new ConnectionPool(username, 20);
    }

    @Bean
    public UserRepository userRepository2(ConnectionPool pool2) {
        return new UserRepository(pool2);
    }

    @Bean
    public ConnectionPool pool3() {
        return new ConnectionPool("test-username", 25);
    }

    @Bean
    public UserRepository userRepository3() {
        return new UserRepository(pool3());
    }
}
