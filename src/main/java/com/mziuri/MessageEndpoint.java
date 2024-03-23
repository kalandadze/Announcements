package com.mziuri;


import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/websocket")
public class MessageEndpoint {
    private static final Map<String, Session> sessions= new HashMap<>();

    private void sendMessage(String message) {
        sessions.forEach((key, value) -> {
            try {
                value.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session);
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("WebSocket closed for " + session.getId()
                + " with reason " + closeReason.getCloseCode());
        sessions.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        sendMessage("[" + session.getId() + "]: " + message);
    }
}
