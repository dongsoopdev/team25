package com.shop.controller;

import com.shop.domain.Pay;
import com.shop.domain.Product;
import com.shop.domain.User;
import com.shop.service.PayService;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private PayService payService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    @GetMapping("/payInsert/{pno}")
    public String payInsert(@PathVariable("pno") long pno, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        User user = userService.findByUserId(userId);
        Product product = productService.getProduct(pno);
        model.addAttribute("product", product);
        model.addAttribute("user", user);

        return "pay/payInsert";
    }

    //결제하기~~
    @PostMapping("/payInsert")
    public String payInsertPro(Pay pay) throws Exception {
        int check = payService.payInsert(pay);
        if (check == 1) {
            log.info("결제 성공");
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("num", 2); // 또는 2
            paramMap.put("pno", pay.getPno()); // pno 값 설정
            productService.updateStatus(paramMap);
            return "redirect:/";
        }
        else {
            log.info("결제 실패");
            return "redirect:/";
        }
    }
    
    //나의 구매 내역
    @GetMapping("/myPayList")
    public String myPayList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId  = authentication.getName();

        List<Pay> myPayList = payService.myPayListByUserId(userId);

        model.addAttribute("myPayList",myPayList);
        return "pay/myPayList";
    }

    @GetMapping("/payComplete/{pno}/{payNo}")
    public String payComplete(@PathVariable("pno") long pno, @PathVariable("payNo") long payNo) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", 1); // 또는 2
        paramMap.put("pno", pno); // pno 값 설정
        productService.updateStatus(paramMap);
        payService.updateShip(4,payNo);

        return "redirect:/pay/myPayList";
    }

    //나의 판매 내역
    @GetMapping("/mySaleList")
    public String mySaleList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId  = authentication.getName();

        List<Product> mySaleList = productService.findByUserId(userId);

        model.addAttribute("mySaleList",mySaleList);
        return "pay/mySaleList";
    }

    //나의 판매 내역

}

