package com.personal.skin_api.chat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChatManageContext {

    private final Map<String, ChatManageStrategy> strategyMap;

    public ChatManageStrategy getStrategy(String strategyKey) {
        return strategyMap.get(strategyKey);
    }
}
