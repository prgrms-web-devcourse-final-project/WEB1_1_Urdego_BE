package io.urdego.game_service.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트 구독 주제 설정
        config.enableSimpleBroker("/game-service/subscribe");
        // 클라이언트가 메시지를 보낼 때의 엔드포인트
        config.setApplicationDestinationPrefixes("/game-service/publish");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 엔드포인트 설정
        registry.addEndpoint("/game-service/connect")
                .setAllowedOrigins("*");
//                .withSockJS(); // SockJS를 통한 fallback 지원
    }

}
