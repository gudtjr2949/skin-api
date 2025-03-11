package com.personal.skin_api.common.redis.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Getter
@Setter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class RedisSentinelInfo {
    @Value("${spring.data.redis.sentinel.nodes}")
    private Set<String> nodes;

    @Value("${spring.data.redis.sentinel.master}")
    private String master;

    private String password;

    private int database = 0;

    private String readFrom;

    private String clientName = "redisgate";

    private RedisSentinelInfo sentinel;
}