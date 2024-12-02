package io.urdego.gateway_service.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userServiceApi() {
        return GroupedOpenApi.builder()
                .group("user-service")
                .pathsToMatch("/api/user-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi contentServiceApi() {
        return GroupedOpenApi.builder()
                .group("content-service")
                .pathsToMatch("/api/content-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi groupServiceApi() {
        return GroupedOpenApi.builder()
                .group("group-service")
                .pathsToMatch("/api/group-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi gameServiceApi() {
        return GroupedOpenApi.builder()
                .group("game-service")
                .pathsToMatch("/api/game-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi notificationServiceApi() {
        return GroupedOpenApi.builder()
                .group("notification-service")
                .pathsToMatch("/api/notification-service/**")
                .build();
    }
}
