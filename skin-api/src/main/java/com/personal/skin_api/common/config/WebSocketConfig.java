package com.personal.skin_api.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private String originUrl = "http://localhost:3000";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub")
                .setTaskScheduler(taskScheduler())
                .setHeartbeatValue(new long[] {3000L, 3000L}); // 메시지를 구독(수신)하는 요청 엔드포인트 -> sub
        config.setApplicationDestinationPrefixes("/pub");  // 메시지를 발행(송신)하는 엔드포인트 -> pub
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins(originUrl);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        // 시간과 보내는 버퍼 사이즈 제한
        registration.setSendTimeLimit(15 * 1000).setSendBufferSizeLimit(512 * 1024);
        // 전송받고 보낼 메시지 사이즈 제한
        registration.setMessageSizeLimit(128 * 1024);
    }

    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(2500);  // 스레드 풀 크기 설정 (필요에 따라 확장 가능)
        taskScheduler.initialize();
        return taskScheduler;
    }
}