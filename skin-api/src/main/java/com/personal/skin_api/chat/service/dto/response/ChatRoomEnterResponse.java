package com.personal.skin_api.chat.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomEnterResponse {
    private String chatRoomTitle;
    private String sellerNickname;
    private String myNickname;

    @Builder
    private ChatRoomEnterResponse(final String chatRoomTitle,
                                  final String sellerNickname,
                                  final String myNickname) {
        this.chatRoomTitle = chatRoomTitle;
        this.sellerNickname = sellerNickname;
        this.myNickname = myNickname;
    }
}
