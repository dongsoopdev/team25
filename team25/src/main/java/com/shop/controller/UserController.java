package com.shop.controller;

import com.shop.domain.User;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
public class UserController {
    @Autowired
    private UserService userService;

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

}