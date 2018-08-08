package ch.corner.example.springoot.wlp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
//@PropertySource("classpath:secret.properties")
public class SpringbootwlpApplication {

    @Bean
    public static SecretSupportPropertySourcesPlaceholderConfigurer secretSupportPropertySourcesPlaceholderConfigurer(Environment env){
        return new SecretSupportPropertySourcesPlaceholderConfigurer(env);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootwlpApplication.class, args);
    }
}
