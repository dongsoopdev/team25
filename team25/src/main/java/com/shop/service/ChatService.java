package com.shop.service;

import com.shop.domain.ChatMsg;
import com.shop.domain.ChatRoom;

import java.util.List;

public interface ChatService {
    public List<ChatRoom> chatRoomListProduct(Long pno);
    public ChatRoom chatRoomGet(Long roomId);
    public ChatRoom chatRoomAdd(String userId, Long pno);
    public Long chatRoomUpdate(Long roomId);

    public List<ChatMsg> chatMsgList(Long roomId, String sender);
    public ChatMsg chatMsgAdd(ChatMsg chatMsg);
    public Long chatMsgUpdate(Long chatId, String sender);
    public Long chatMsgUpdates(Long roomId, String sender);
    public Long chatMsgDelete(Long chatId);
}
