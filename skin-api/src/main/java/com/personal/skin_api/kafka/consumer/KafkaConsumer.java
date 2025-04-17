package com.personal.skin_api.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.skin_api.kafka.dto.KafkaChat;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final SimpMessagingTemplate template;

    @KafkaListener(topics = "chat-exchange")
    public void consume(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // message를 ChatMessage 객체로 변환
            KafkaChat kafkaChat = objectMapper.readValue(message, KafkaChat.class);

            // WebSocket을 통해 해당 채팅방으로 메시지 전송
            String destination = "/sub/chat?chatRoomId=" + kafkaChat.getChatRoomId();
            template.convertAndSend(destination, kafkaChat);
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
        }
    }
}