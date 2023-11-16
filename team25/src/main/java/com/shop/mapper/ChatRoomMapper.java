package com.shop.mapper;

import com.shop.domain.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {

    public List<ChatRoom> chatRoomList();

    public List<ChatRoom> chatRoomProductList(Long pno);

    public ChatRoom chatRoomGet(Long roomId);

    public ChatRoom chatRoomGetId(Long pno, String userId);

    public Long chatRoomGetUnique(String userId, Long pno);

    public void chatRoomAdd(String userId, Long pno);

    public Long chatRoomUpdate(Long roomId);

    public Long chatRoomDelete(Long roomId);
}