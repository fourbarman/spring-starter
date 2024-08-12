package ru.fourbarman.spring.config;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import ru.fourbarman.spring.database.repository.CrudRepository;
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

}
