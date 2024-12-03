package io.urdego.content_service.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@Configuration
public class ContentServiceConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    // JSON 직렬화/역직렬화 시 사용
    @Bean
    public ObjectMapper objectMapper() {

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        ObjectMapper mapper = new ObjectMapper();

        /*// 객체의 속성 이름을 snake-case로 설정
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);*/

        // Java 8 날짜/시간 모듈 등록
        mapper.registerModule(javaTimeModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return mapper;
    }

    @Bean
    public void setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
