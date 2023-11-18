package com.shop.controller;

import com.shop.domain.ChatRoom;
import com.shop.domain.Product;
import com.shop.service.ChatService;
import com.shop.service.ProductService;

import groovy.util.logging.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/productList")
    public String getProductAll(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("product", productList);
        return "product/productList";
    }

    @GetMapping("/getProduct/{pno}")
    public String getProduct(@PathVariable("pno") long pno, Model model) {
        Product product = productService.getProduct(pno);
        System.out.println(product);
        model.addAttribute("product", product);

        //채팅방 연결
        List<ChatRoom> roomList = chatService.chatRoomProductList(pno);
        model.addAttribute("roomList", roomList);

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


    // add form
    @GetMapping("/addProduct")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/addProduct";
    }


    // 상품 등록 처리
    @PostMapping("/addProduct")
    public String addproduct(Product product, MultipartFile[] imgFile) throws Exception {
        //로그인 후 사용자 정보 가져와서 모델에 추가
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String loginId  = authentication.getName();


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

        return "redirect:/product/productList";
    }


    @GetMapping("/updateProduct/{pno}")
    public String updateProductForm(@PathVariable("pno") Long pno, Model model) {
        // 사용자의 권한 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
                //아이디 확인
                String userId = authentication.getName(); // 로그인한 사용자의 아이디를 얻는 방법으로 변경
                String getId = productService.getProduct(pno).getSeller();

                // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 수정 가능
                if (getId.equals(userId)) {
                    model.addAttribute("product", productService.getProduct(pno));
                    return "product/updateProduct";
                } else {
                    return "redirect:/";
                }
            } else {
                // 일반 회원이면 거절 -> main
                return "redirect:/";
            }
        } else {
            // 로그인한 사용자가 없는 경우
            return "redirect:/";
        }
    }




    // 상품 수정 (POST) - 판매자만 가능
    @PostMapping("/updateProduct/{pno}")
    public String updateProduct(Product product, @PathVariable("pno") Long pno, Model model, MultipartFile[] imgFile) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
                //아이디 확인
                String userId = authentication.getName(); // 로그인한 사용자의 아이디를 얻는 방법으로 변경
                String getId = productService.getProduct(pno).getSeller();

                // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 수정 가능
                if (getId.equals(userId)) {
                    List<Product> myproList = productService.findByUserId(getId);
                    productService.updateProduct(product, pno, imgFile);
                    model.addAttribute("myproList", myproList);
                    return "myProductList";
                } else {
                    return "redirect:/";
                }
            } else {
                // 일반 회원이면 거절 -> main
                return "redirect:/";
            }
        } else {
            // 로그인한 사용자가 없는 경우
            return "redirect:/";
        }
    }

    // 상품 삭제 - 판매자만 가능
    @GetMapping("/deleteProduct/{pno}")
    public String productDelete(@PathVariable("pno") Long pno, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
                //아이디 확인
                String userId = authentication.getName(); // 로그인한 사용자의 아이디를 얻는 방법으로 변경
                String getId = productService.getProduct(pno).getSeller();

                // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 수정 가능
                if (getId.equals(userId)) {

                    productService.deleteProduct(pno);
                    List<Product> myproList = productService.findByUserId(getId);
                    model.addAttribute("myproList", myproList);

                    return "myProductList";
                } else {
                    return "redirect:/";
                }
            } else {
                // 일반 회원이면 거절 -> main
                return "redirect:/";
            }
        } else {
            // 로그인한 사용자가 없는 경우
            return "redirect:/";
        }



    }


}