package com.personal.skin_api.chat.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatListResponse {
    private String sellerNickname;
    private String chatRoomTitle;
    private String nickname;
    private List<ChatResponse> chatResponses;

    @Builder
    private ChatListResponse(final String sellerNickname,
                             final String chatRoomTitle,
                             final String nickname,
                             final List<ChatResponse> chatResponses) {
        this.sellerNickname = sellerNickname;
        this.chatRoomTitle = chatRoomTitle;
        this.nickname = nickname;
        this.chatResponses = chatResponses;
    }
}
