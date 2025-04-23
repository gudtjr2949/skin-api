package com.personal.skin_api.chat.service;

import com.personal.skin_api.chat.service.dto.request.*;
import com.personal.skin_api.chat.service.dto.response.ChatListResponse;
import com.personal.skin_api.chat.service.dto.response.ChatRoomEnterResponse;
import com.personal.skin_api.chat.service.dto.response.ChatRoomListResponse;
import com.personal.skin_api.product.repository.entity.Product;

public interface ChatService {
    Long createChatRoom(ChatRoomCreateServiceRequest request);
    ChatRoomEnterResponse enterChatRoom(ChatRoomEnterServiceRequest request);
    ChatListResponse findChatList(ChatListServiceRequest request);
    ChatRoomListResponse findMyChatRoom(ChatRoomListServiceRequest request);
    void sendChat(ChatSendServiceRequest request);
    void exitChatRoom(ChatRoomExitServiceRequest request);
    void deleteChatRoom(Product product);
}