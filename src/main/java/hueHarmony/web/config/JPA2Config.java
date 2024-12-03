package hueHarmony.web.config;

import hueHarmony.web.repository.repository_impl.ExtendedRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "hueHarmony.web.repository", repositoryBaseClass = ExtendedRepositoryImpl.class)
public class JPA2Config {

}
