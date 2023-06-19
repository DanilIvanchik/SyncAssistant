package com.example.sync.controller;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private MassageType type;
}
