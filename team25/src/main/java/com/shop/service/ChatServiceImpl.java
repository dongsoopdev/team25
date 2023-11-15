package com.shop.service;

import com.shop.domain.ChatMsg;
import com.shop.domain.ChatRoom;
import com.shop.mapper.ChatMsgMapper;
import com.shop.mapper.ChatRoomMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatRoomMapper chatRoomMapper;

    @Autowired
    ChatMsgMapper chatMsgMapper;

    @Override
    public List<ChatRoom> chatRoomListProduct(Long pno) {
        return chatRoomMapper.chatRoomProductList(pno);
    }

    @Override
    public ChatRoom chatRoomGet(Long roomId) {
        return chatRoomMapper.chatRoomGet(roomId);
    }

    @Override
    public ChatRoom chatRoomAdd(String userId, Long pno) {
        if(chatRoomMapper.chatRoomGetUnique(userId, pno)>0){
            return null;
        }

        chatRoomMapper.chatRoomAdd(userId, pno);
        return chatRoomMapper.chatRoomGetId(pno, userId);
    }

    @Override
    public Long chatRoomUpdate(Long roomId) {
        return chatRoomMapper.chatRoomUpdate(roomId);
    }

    @Override
    public List<ChatMsg> chatMsgList(Long roomId, String sender) {
        chatMsgMapper.chatMsgUpdates(roomId, sender);
        return chatMsgMapper.chatMsgList(roomId);
    }

    @Override
    public ChatMsg chatMsgAdd(ChatMsg chatMsg) {
        Long roomId = chatMsg.getRoomId();
        ChatRoom room = chatRoomMapper.chatRoomGet(roomId);
        if(room.getStatus().equals("BLOCK")){
            return null; // 차단된 경우에는 메시지 전송하지 않음.
        }
        chatMsgMapper.chatMsgAdd(chatMsg);
        return chatMsgMapper.chatMsgGetLast();
    }

    @Override
    public Long chatMsgUpdate(Long chatId, String sender) {
        return chatMsgMapper.chatMsgUpdate(chatId, sender);
    }

    @Override
    public Long chatMsgUpdates(Long roomId, String sender) {
        return chatMsgMapper.chatMsgUpdates(roomId, sender);
    }

    @Override
    public Long chatMsgDelete(Long chatId) {
        return chatMsgMapper.chatMsgDelete(chatId);
    }
}
