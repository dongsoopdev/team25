package com.shop.controller;

import com.shop.domain.*;
import com.shop.service.ChatService;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/member")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ChatService chatService;


    @GetMapping("/")
    public String home(Model model){ // 인증된 사용자 정보 보여줌
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //토큰에 저장된 사용자의 고유번호 id값
        User user = userService.findById(id);
        user.setPassword(null);
        model.addAttribute("user",user);
        return "index";
    }

    //User 테이블의 전체 정보 보여줌
    @GetMapping("/userList")
    public String getUserList(Model model){
        List<User> userList = userService.getUserList();
        model.addAttribute("list", userList);
        return "member/userList";
    }

    //로그인 폼 출력
    @GetMapping("/login")
    public String loginForm(){
        return"member/login";
    }


    @GetMapping("/join")
    public String joinForm(Model model, User user){
        model.addAttribute("user", user);
        return "member/joinForm";
    }

    @PostMapping("/join")
    public String userInsert(User user, Model model){
        userService.userInsert(user, 5);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    //회원 정보 수정폼
    @GetMapping("/update")
    public String updateForm(Model model){
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "member/updateForm";
    }

    //회원 정보 수정
    @PostMapping("/update")
    public String edit(User user){
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setId(id);
        userService.edit(user);
        return "redirect:/";
    }

    //회원 탈퇴
    @PostMapping("/delete")
    public String withdraw(User user, HttpSession session, Model model){
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setActive(user.getActive());
        user.setId(user.getId());
        userService.withdraw(user);
        //토큰은 삭제 하지 않고 기록만 남길 수 있게??
        return "redirect:/";
    }


   /* @GetMapping("/mypage")
    public String getMyInfo(Model model){
        //로그인 후 사용자 정보 가져와서 모델에 추가
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        model.addAttribute("username",username);
        return "test";
    }*/

    @GetMapping("/loginInfo")
    public String memberInfo(Principal principal, ModelMap modelMap){
        String loginId = principal.getName();
        User user = userService.findByUserId(loginId);
        modelMap.addAttribute("user", user);
        return "member/myinfo";
    }


    //내가 등록한 상품
    @RequestMapping("/myProList")
    public String myProductList( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId  = authentication.getName();


        List<Product> myproList = productService.findByUserId(userId);
        System.out.println(myproList);
        model.addAttribute("myproList", myproList);
        return "member/myProductList";
    }



    //내가 등록한 상품
    @GetMapping("/myProductList")
    public String myProductList(@RequestParam("seller") String seller, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId  = authentication.getName();


        List<Product> myproList = productService.findByUserId(seller);
        System.out.println(myproList);
        model.addAttribute("myproList", myproList);
        return "member/myProductList";
    }


    //나의 채팅
//    @GetMapping("myChatList")
//    public String myChatList(HttpServletRequest request, ModelMap modelMap, Principal principal, Model model){
//        //사용자의 아이디 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId  = authentication.getName();
//
//        String seller = request.getParameter("seller"); //판매자
//
//        List<ChatRoom> roomList = chatService.chatRoomMy(seller);
//        System.out.println(roomList);
//        model.addAttribute("roomList", roomList);
//
//        return "member/myChatList";
//    }

//    @GetMapping("myChatList")
//    public String roomList(HttpServletRequest request, Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId  = authentication.getName();
//
//        Long pno = Long.valueOf(request.getParameter("pno"));
//        model.addAttribute("pno", pno);
//
//        List<ChatRoom> chatRooms = chatService.chatRoomProductList(pno);
//        model.addAttribute("rooms", chatRooms);
//
//        return "member/myChatList";
//    }

    //나의 채팅방 목록
    @GetMapping("myChatList")
    public String myChat(HttpServletRequest request, Model model, Principal principal){
        if(principal != null) {
            String userId  = principal.getName();
            model.addAttribute("userId", userId);

            List<ChatRoomVO> rooms = chatService.chatRoomMy(userId);
            for(ChatRoomVO chatRoom : rooms){
                User buyer = userService.findByUserId(chatRoom.getBuyer());
                chatRoom.setBuyerNm(buyer.getUserName());
            }
            model.addAttribute("rooms", rooms);
        }
        return "member/myChatList";
    }

    // 채팅하기
    @GetMapping("myChat")
    public String myChat(HttpServletRequest request, ModelMap modelMap, Principal principal, Model model) {
        //사용자의 아이디 가져오기
        String userId = principal.getName();
        model.addAttribute("userId", userId);

        //또는 SecurityContextHolder.getContext().getAuthentication().getName();
        String buyer = request.getParameter("buyer");//구매희망자
        Long pno = Long.valueOf(request.getParameter("pno")); // 상품 고유번호

        // 채팅방이 없으면 새로 추가, 있으면 가져오기
        ChatRoomVO room = chatService.chatRoomInsert(buyer, pno);
        model.addAttribute("room", room);


        // 기존의 채팅 내역 가져오기
        Long roomNo = room.getRoomNo();

        List<ChatMessage> chats = chatService.chatMessageList(roomNo);
        model.addAttribute("chats", chats);

        // 채팅방에 들어가면 기존에 안 읽은 메시지 읽음 처리
        //chatService.chatMessageReadUpdates(roomNo, userId);

        // 채팅방 상대 이름 띄우기
        // 채팅방은 구매자 기준으로 저장되므로, 구매자인 경우 product 에서 seller 가져오기
        Product product = productService.getProduct(pno);

        if (userId.equals(room.getBuyer())) {
            // 로그인한 사람이 구매자인 경우 판매자의 이름
            model.addAttribute("roomName", product.getSeller());
        } else {
            // 로그인한 사람이 판매자인 경우 구매자의 이름
            model.addAttribute("roomName", room.getBuyer());
        }

        return "member/myChat";
    }





}