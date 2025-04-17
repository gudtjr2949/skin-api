package com.personal.skin_api.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String kafkaUrl;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl); // Kafka 브로커 서버 설정
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId()); // Kafka Consumer Group 의 고유한 식별자를 설정
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    // 메시지를 수신하는 KafkaListenerContainerFactory 빈 정의
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();

        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        // Kafka 메시지를 병렬로 처리하기 위한 설정을 추가할 수 있음(스레드 풀 크기, 에러 핸들링..)
        return kafkaListenerContainerFactory;
    }
}