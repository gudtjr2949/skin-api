package com.personal.skin_api.chat.repository.entity.chat_content;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ChatContentTest {

    @Test
    void 채팅_내용을_생성한다() {
        // given
        String message = "채팅입니다.";

        // when
        ChatContent chatContent = new ChatContent(message);

        // then
        assertThat(chatContent.getChatContent()).isEqualTo(message);
    }

    @Test
    void 채팅_내용이_NULL일_경우_예외가_발생한다() {
        // given
        String message = null;

        // when & then
        assertThatThrownBy(() -> new ChatContent(message)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 채팅_내용이_BLANK_일_경우_예외가_발생한다() {
        // given
        String emptyMessage = "";
        String blankMessage = " ";

        List<String> messages = List.of(emptyMessage, blankMessage);

        // when & then
        messages.stream().forEach(message -> {
            assertThatThrownBy(() -> new ChatContent(message)).isInstanceOf(RestApiException.class);
        });
    }

}