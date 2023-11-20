package com.shop.controller;

import com.shop.domain.*;
import com.shop.service.PayService;
import com.shop.service.ProductService;
import com.shop.service.ReviewService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PayService payService;

    @Autowired
    private ReviewService reviewService;

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

    //아이디 중복 검사
    @ResponseBody // ajax 값 변환 위해 필요
    @GetMapping("/idDupCheck")
    public int idDupCheck(User userId){
        int result = userService.idDupCheck(userId); // 중복 확인 값 int로 받음
        return result;
    }



    //내가 등록한 상품
    //@RequestMapping("/myProList")
    @RequestMapping(value = "/myProList", method = RequestMethod.GET)
    public String myProductList( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId  = authentication.getName();


        List<Product> myproList = productService.findByUserId(userId);
        System.out.println(myproList);
        model.addAttribute("myproList", myproList);
        //소윤의 구매내역
        List<Pay> myPayList = payService.myPayListByUserId(userId);
        model.addAttribute("myPayList",myPayList);

        // 내가 쓴 후기
        List<Review> proReview= reviewService.proReview(userId);
        System.out.println(proReview);
        model.addAttribute("proReview", proReview);

        //내가 받은 후기
        List<Review> proSellerReview= reviewService.proSellerReview(userId);
        System.out.println(proSellerReview);
        model.addAttribute("proSellerReview", proSellerReview);


        //좋아요
        //pno, userId
        List<Likes> proLikes = productService.getByIdLikeList(userId);
        List<Product> proList = new ArrayList<>();
        for (Likes pro: proLikes) {
            System.out.println(pro);
            proList.add(productService.getProduct(pro.getPno()));
        }

        System.out.println(proList);
        model.addAttribute("proLikes", proLikes);
        model.addAttribute("proList", proList);


        return "member/myProductList";
    }













}