package com.shop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.domain.ChatMsg;
import com.shop.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ServerEndpoint(value = "/socket")
public class ChatMsgController {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ChatService service;

    private static final List<Session> sessionList = new ArrayList<Session>();

    @OnOpen  // socket 연결 시
    public void onOpen(Session session) {
        sessionList.add(session);
    }

    @OnMessage
    public void onMessage (String message, Session session) throws IOException {
        // 다른 사람에게 메세지 보내기
        Map<String, List<String>> requestParameter = session.getRequestParameterMap();
        Long roomId = Long.parseLong(requestParameter.get("roomId").get(0));

        ChatMsg chat = mapper.readValue(message, ChatMsg.class);
        System.out.println(chat);
        sendRoomMessage(message, roomId, chat);

    }

    @OnError
    public void onError(Throwable e, Session session) {
        System.out.println(e.getMessage() + "by session : " + session.getId());
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sessionList.remove(session);
    }

    private void sendAllSessionToMessage(String msg){ // 연결된 모든 사용자에게 메세지 전달
        try {
            for(Session s : ChatMsgController.sessionList){
                s.getBasicRemote().sendText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRoomMessage(String msg, Long roomId, ChatMsg chat){
        try {
            for(Session s : ChatMsgController.sessionList){
                Map<String, List<String>> requetParameter = s.getRequestParameterMap();
                Long sroomId = Long.parseLong(requetParameter.get("roomId").get(0));
                if(sroomId == roomId){
                    s.getBasicRemote().sendText(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}