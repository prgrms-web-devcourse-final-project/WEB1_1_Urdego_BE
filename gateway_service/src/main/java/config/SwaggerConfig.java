package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI urdegoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Gateway")
                        .description("전체 마이크로서비스별 API 모음")
                        .version("v1.0"));
    }
}
