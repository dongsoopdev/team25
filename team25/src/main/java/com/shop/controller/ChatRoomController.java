package com.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.domain.ChatMsg;
import com.shop.domain.ChatRoom;
import com.shop.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/chat/")
public class ChatRoomController {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ChatService service;

    @GetMapping("chat")
    public String loadHome(Model model, HttpServletRequest request){
        Long pno = Long.parseLong(request.getParameter("pno"));
        List<ChatRoom> chatRooms = service.chatRoomListProduct(pno);

        model.addAttribute("chatRooms", chatRooms);
        return "/chat/chat";
    }

    @GetMapping("chat2")
    public String loadHome2(Model model, HttpServletRequest request){
        Long pno = Long.parseLong(request.getParameter("pno"));
        List<ChatRoom> chatRooms = service.chatRoomListProduct(pno);

        model.addAttribute("chatRooms", chatRooms);
        return "/chat/chat2";
    }

    @PostMapping("createRoom")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String userId, @RequestParam Long pno){

        return service.chatRoomAdd(userId, pno);
    }

    @PostMapping("getRoom")
    @ResponseBody
    public List<ChatMsg> getRoom(HttpServletRequest request){
        Long roomId = Long.parseLong(request.getParameter("roomId"));
        String sender = request.getParameter("userId");
        service.chatMsgUpdates(roomId, sender);

        return service.chatMsgList(roomId, sender);
    }

    @PostMapping("blockRoom")
    @ResponseBody
    public String blockRoom(HttpServletRequest request){
        Long roomId = Long.parseLong(request.getParameter("roomId"));
        Long returnNo = service.chatRoomUpdate(roomId);
        if(returnNo>0){
            return "Block Successfully";
        }

        return "Something went wrong";
    }

    @PostMapping("readRoom")
    @ResponseBody
    public String readRoom(HttpServletRequest request){
        Long roomId = Long.parseLong(request.getParameter("roomId"));
        String sender = request.getParameter("userId");

        Long returnNo = service.chatMsgUpdates(roomId, sender);
        if(returnNo>0){
            return "Success";
        }

        return "Something went wrong";
    }

    @PostMapping("insertChat")
    @ResponseBody
    public ChatMsg insertChat(@RequestParam String message) throws JsonProcessingException {
        ChatMsg chat = mapper.readValue(message, ChatMsg.class);

        return service.chatMsgAdd(chat);
    }

    @PostMapping("readChat")
    @ResponseBody
    public String readChat(@RequestParam String message, @RequestParam String user) throws JsonProcessingException {
        ChatMsg chat = mapper.readValue(message, ChatMsg.class);
        System.out.println(chat);
        service.chatMsgUpdate(chat.getChatId(), user);

        return "readChat Completed";
    }
}