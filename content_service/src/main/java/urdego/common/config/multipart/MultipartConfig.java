package urdego.common.config.multipart;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(50)); // 단일 파일 최대 크기
        factory.setMaxRequestSize(DataSize.ofMegabytes(50)); // 전체 요청 최대 크기
        return factory.createMultipartConfig();
    }
}
