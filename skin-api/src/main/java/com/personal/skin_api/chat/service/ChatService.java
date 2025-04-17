package com.personal.skin_api.chat.service;

import com.personal.skin_api.chat.service.dto.request.*;
import com.personal.skin_api.chat.service.dto.response.ChatListResponse;
import com.personal.skin_api.chat.service.dto.response.ChatRoomListResponse;

public interface ChatService {
    Long createChatRoom(ChatRoomCreateServiceRequest request);
    ChatListResponse enterChatRoom(ChatRoomEnterServiceRequest request);
    ChatRoomListResponse findMyChatRoom(ChatRoomListServiceRequest request);
    void sendChat(ChatSendServiceRequest request);
    void exitChatRoom(ChatRoomExitServiceRequest request);
}