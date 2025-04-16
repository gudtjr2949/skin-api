package com.personal.skin_api.chat.repository.entity.chat_content;

import java.util.List;

public class ChatContentStrategyContext {
    private static final List<ChatContentValidationStrategy> strategies = List.of(
            new ChatContentNullStrategy(),
            new ChatContentBlankStrategy()
    );

    static void runStrategy(final String chatContent) {
        strategies.stream().forEach(strategy -> strategy.validate(chatContent));
    }
}
