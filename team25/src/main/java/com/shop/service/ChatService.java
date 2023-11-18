package com.shop.service;


import com.shop.domain.ChatMessage;
import com.shop.domain.ChatRoom;

import java.util.List;

public interface ChatService {
    public List<ChatRoom> chatRoomProductList(Long pno);
    public ChatRoom chatRoomGetNo(Long roomNo);
    public ChatRoom chatRoomInsert(String buyer, Long pno);
    public int chatRoomBlockUpdate(Long roomNo);

    public List<ChatMessage> chatMessageList(Long roomNo);
    public ChatMessage chatMessageInsert(ChatMessage chatMessage);
    public int chatMessageReadUpdate(Long chatNo, String sender);
    public int chatMessageReadUpdates(Long roomNo, String sender);
    public int chatMessageRemoveUpdate(Long chatNo);

    public int chatMessageUnreadAll(String receiver);
    public List<ChatRoom> chatRoomMy(String id);
}
