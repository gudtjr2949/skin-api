package com.personal.skin_api.chat.service;

import com.personal.skin_api.chat.service.dto.request.ChatRoomCreateServiceRequest;
import com.personal.skin_api.chat.service.dto.request.ChatRoomEnterServiceRequest;
import com.personal.skin_api.chat.service.dto.request.ChatRoomExitServiceRequest;
import com.personal.skin_api.chat.service.dto.request.ChatSendServiceRequest;
import com.personal.skin_api.chat.service.dto.response.ChatListResponse;

public interface ChatService {
    void createChatRoom(ChatRoomCreateServiceRequest request);
    ChatListResponse enterChatRoom(ChatRoomEnterServiceRequest request);
    void sendChat(ChatSendServiceRequest request);
    void exitChatRoom(ChatRoomExitServiceRequest request);
}