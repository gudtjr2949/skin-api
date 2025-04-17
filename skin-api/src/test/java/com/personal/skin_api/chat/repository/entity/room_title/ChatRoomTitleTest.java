package com.personal.skin_api.chat.repository.entity.room_title;

import org.junit.jupiter.api.Test;

import static com.personal.skin_api.chat.repository.entity.room_title.ChatRoomTitle.*;
import static org.assertj.core.api.Assertions.*;

class ChatRoomTitleTest {


    @Test
    void 채팅방_제목을_생성한다() {
        // given
        String productName = "형석이의 엄청난 스킨";

        // when
        ChatRoomTitle chatRoomTitle = new ChatRoomTitle(productName);

        // then
        assertThat(chatRoomTitle.getChatRoomTitle().contains(productName)).isTrue();
    }

    @Test
    void 채팅방_제목이_설정된_포맷_형식으로_생성된다() {
        // given
        String productName = "형석이의 엄청난 스킨";
        String formattedChatRoomTitle = String.format("%s%s%s%s", LEFT_BRACKET, productName, RIGHT_BRACKET, TITLE_SUFFIX);

        // when
        ChatRoomTitle chatRoomTitle = new ChatRoomTitle(productName);

        // then
        assertThat(chatRoomTitle.getChatRoomTitle()).isEqualTo(formattedChatRoomTitle);
    }

}