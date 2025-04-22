package com.personal.skin_api.kafka.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaChat {
    private Long chatRoomId;
    private String nickname;
    private String chatContent;
    private Long createdAt;
}
