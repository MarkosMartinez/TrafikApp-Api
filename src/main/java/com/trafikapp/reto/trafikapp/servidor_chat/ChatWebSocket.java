package com.trafikapp.reto.trafikapp.servidor_chat;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatWebSocket extends TextWebSocketHandler {
    private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        broadcastMessage("Un nuevo usuario se ha conectado.", session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        broadcastMessage(message.getPayload(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        broadcastMessage("Un usuario se ha desconectado.", session);
    }

    private void broadcastMessage(String message, WebSocketSession sender) {
        TextMessage textMessage = new TextMessage(message);
        sessions.stream()
               .filter(session -> !session.equals(sender))
               .forEach(session -> {
                   try {
                       session.sendMessage(textMessage);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               });
    }
}