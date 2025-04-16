package com.personal.skin_api.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.skin_api.kafka.dto.KafkaChat;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMassage(String topic, KafkaChat kafkaChat) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = objectMapper.writeValueAsString(kafkaChat);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(topic, jsonInString);
    }

}