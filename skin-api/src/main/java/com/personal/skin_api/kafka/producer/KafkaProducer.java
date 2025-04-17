package com.personal.skin_api.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.KafkaErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.kafka.dto.KafkaChat;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.KafkaException;
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
            kafkaTemplate.send(topic, jsonInString);
        } catch (JsonProcessingException e) {
            throw new RestApiException(CommonErrorCode.JSON_TO_STRING_ERROR);
        } catch (KafkaException kafkaException) {
            throw new RestApiException(KafkaErrorCode.KAFKA_SEND_ERROR);
        }
    }

}