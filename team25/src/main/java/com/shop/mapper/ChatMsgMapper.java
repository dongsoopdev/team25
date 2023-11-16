package com.shop.mapper;

import com.shop.domain.ChatMsg;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMsgMapper {

    public List<ChatMsg> chatMsgList(Long roomId);

    public Long chatMsgUnread(Long roomId);

    public ChatMsg chatMsgGetLast();

    public Long chatMsgAdd(ChatMsg chatMsg);

    public Long chatMsgUpdate(Long chatId, String sender);

    public Long chatMsgUpdates(Long roomId, String sender);

    public Long chatMsgDelete(Long chatId);
}
