package com.personal.skin_api.chat.repository.entity.chat_content;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.chat.ChatErrorCode;

public class ChatContentBlankStrategy implements ChatContentValidationStrategy {

    @Override
    public void validate(String chatContent) {
        if (chatContent.isBlank())
            throw new RestApiException(ChatErrorCode.CHAT_CONTENT_CAN_NOT_BE_BLANK);
    }
}
