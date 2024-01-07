package web.servlet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import web.servlet.controller.PostController;
import web.servlet.repository.PostRepository;
import web.servlet.service.PostService;

@Configuration
@ComponentScan(basePackages = "web.servlet")
public class AppConfig {
    @Bean
    PostRepository repository() {
        return new PostRepository();
    }

    @Bean
    PostService service() {
        return new PostService(repository());
    }

    @Bean
    PostController controller() {
        return new PostController(service());
    }
}
