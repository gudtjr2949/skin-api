package com.personal.skin_api.chat.repository.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ROOM_TITLE")
    private String roomTitle;


    @Builder
    private ChatRoom(final String roomTile) {
        this.roomTitle = roomTile;
    }
}
