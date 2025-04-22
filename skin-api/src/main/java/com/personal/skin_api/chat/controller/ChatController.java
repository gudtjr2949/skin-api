package com.personal.skin_api.chat.controller;

import com.personal.skin_api.chat.controller.dto.request.ChatListRequest;
import com.personal.skin_api.chat.controller.dto.request.ChatSendRequest;
import com.personal.skin_api.chat.service.ChatService;
import com.personal.skin_api.chat.service.dto.request.ChatRoomEnterServiceRequest;
import com.personal.skin_api.chat.service.dto.request.ChatRoomExitServiceRequest;
import com.personal.skin_api.chat.service.dto.request.ChatRoomListServiceRequest;
import com.personal.skin_api.chat.service.dto.response.ChatListResponse;
import com.personal.skin_api.chat.service.dto.response.ChatRoomListResponse;
import com.personal.skin_api.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/enter")
    public ResponseEntity<ChatListResponse> enterChatRoom(@RequestParam Long chatRoomId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        ChatListResponse chatListResponse = chatService.enterChatRoom(ChatRoomEnterServiceRequest.builder()
                .chatRoomId(chatRoomId)
                        .email(userDetails.getUsername())
                .build());

        return ResponseEntity.ok().body(chatListResponse);
    }

    @PostMapping("/chat-list")
    public ResponseEntity<ChatListResponse> findChatList(@RequestBody ChatListRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        ChatListResponse response = chatService.findChatList(request.toService(userDetails.getUsername()));
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/chat-room-list")
    public ResponseEntity<ChatRoomListResponse> findChatRoomList(@AuthenticationPrincipal UserDetails userDetails) {
        ChatRoomListResponse response = chatService.findMyChatRoom(ChatRoomListServiceRequest.builder()
                .email(userDetails.getUsername())
                .build());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/exit")
    public ResponseEntity<Object> exitChatRoom(@RequestParam Long chatRoomId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        chatService.exitChatRoom(ChatRoomExitServiceRequest.builder()
                .chatRoomId(chatRoomId)
                .email(userDetails.getUsername())
                .build());

        return ResponseEntity.ok().body(new CommonResponse(200, "채팅방 퇴장 성공"));
    }
}
