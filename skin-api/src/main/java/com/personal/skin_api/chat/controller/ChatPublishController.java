package com.personal.skin_api.chat.controller;

import com.personal.skin_api.chat.controller.dto.request.ChatSendRequest;
import com.personal.skin_api.chat.service.ChatService;
import com.personal.skin_api.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ChatPublishController {

    private final ChatService chatService;

    @MessageMapping("/send") // pub
    public ResponseEntity<Object> sendChat(@RequestBody ChatSendRequest request,
                                           Principal principal) {
        chatService.sendChat(request.toService(principal.getName()));
        return ResponseEntity.ok().body(new CommonResponse(200, "채팅 전송 성공"));
    }
}
