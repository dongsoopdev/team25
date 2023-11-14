package com.shop.controller;

import com.shop.domain.Product;
import com.shop.domain.ProductFile;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productList")
    public String getProductAll(Model model){
        List<Product> productList = productService.findAll();
        model.addAttribute("product",productList );
        return "product/productList";
    }

    @GetMapping("/getProduct/{productID}")
    public String getProduct(@PathVariable long pno,Model model){
        Product product = productService.getProduct(pno);
        model.addAttribute("product",product);
        return "product/productDetail";
    }




    // 상품 상세 페이지 - 판매자, 구매자 가능
//    @GetMapping("/item/view/{itemId}")
//    public String ItemView(Model model, @PathVariable("itemId") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
//            // 판매자
//            User user = principalDetails.getUser();
//
//            model.addAttribute("item", itemService.itemView(id));
//            model.addAttribute("user", user);
//
//            return "itemView";
//        } else {
//            // 구매자
//            User user = principalDetails.getUser();
//
//            // 페이지에 접속한 유저를 찾아야 함
//            User loginUser = userPageService.findUser(user.getId());
//
//            int cartCount = 0;
//            Cart userCart = cartService.findUserCart(loginUser.getId());
//            List<CartItem> cartItems = cartService.allUserCartView(userCart);
//
//            for(CartItem cartItem : cartItems) {
//                cartCount += cartItem.getCount();
//            }
//
//            model.addAttribute("cartCount", cartCount);
//            model.addAttribute("item", itemService.itemView(id));
//            model.addAttribute("user", user);
//
//            return "itemView";
//        }
//    }





    //상세보기
    @GetMapping("/productDetail")
    public String productDetail(Model model){

        return "product/productDetail";
    }



    @GetMapping("/addProduct")
    public String addForm(Model model){
        model.addAttribute("product", new Product());
        return "product/addProduct";
    }


    @PostMapping("/addProduct")
    public String addproduct(Product product,  MultipartFile[] imgFile) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        product.setSeller(userId);

        productService.saveProduct(product, imgFile);

  /*      if(principalDetails.getRole().getRoleName().equals("TEACHER")) {
            // 판매자
            product.setSeller(principalDetails.getUser());
            productService.saveProduct(product, imgFile);
            return "redirect:product/productLis";
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/";
        }*/

        return  "redirect:productList";
    }





}