package com.shop.controller;

import com.shop.domain.Product;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productList")
    public String getproductAll(Model model){
       // List<Product> productList = productService.productList();
        return "product/productList";
    }

    @GetMapping("/get/{productID}")
    public String getproduct(@PathVariable long pno,Model model){
        Product product = productService.getproduct(pno);
        model.addAttribute("product",product);
        return "product/productDetail";
    }


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
    public String addproduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes){
        int cnt = productService.save(product);
        Product addedproduct = productService.getLatestproduct();
        //redirectAttributes.addAttribute("productId", addedproduct.getproductName());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/product/productList";
    }

}
