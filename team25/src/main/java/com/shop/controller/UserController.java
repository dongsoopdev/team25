package com.shop.controller;

import com.shop.domain.Product;
import com.shop.domain.User;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import com.shop.util.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    //회원 가입
    @GetMapping("/join")
    public String joinForm(Model model, User user){
        model.addAttribute("user", user);
        return "member/joinForm";
    }

    @PostMapping("joinPro")
    public String joinPro(User user, Integer roleId, Model model){
        userService.save(user, 5); // 추후 회원가입 시 고르는 것으로 고려 ( 관리자가 권한 부여 하는 방법으로 하는 것도 나쁘지 않을 듯)
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model, LoginForm loginForm){
        model.addAttribute("loginForm", loginForm);
        return "member/login";
    }


    @PostMapping("/auth")
    public String loginPro(User user, HttpSession session, Model model){
        User result = userService.loginPro(user);
        if(result!=null){
            session.setAttribute("userSid", result);
            model.addAttribute("user", user);
        } else {
            session.invalidate();
        }
        return "redirect:/";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(Model model, HttpSession session){
        session.invalidate();
        model.addAttribute("msg", "로그아웃 되었습니다.");
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
    @GetMapping("/myProductList")
    public String myProductList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId  = authentication.getName();


        List<Product> myproList = productService.findByUserId(userId);
        System.out.println(myproList);
        model.addAttribute("myproList", myproList);
        return "member/myProductList2";
    }









}