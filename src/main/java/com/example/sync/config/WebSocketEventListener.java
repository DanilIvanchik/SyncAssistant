package com.example.sync.config;

import com.example.sync.controller.ChatMessage;
import com.example.sync.controller.MassageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketDisconnectEvent(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null){
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MassageType.LEAVE)
                    .sender(username)
                    .build();
            sendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}
