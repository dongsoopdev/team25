package com.shop.controller;


import com.shop.domain.Review;
import com.shop.service.ReviewService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/addReviewForm/{pno}")
    public String addReviewForm(@PathVariable("pno") Long pno, Model model) {
        return "product/addReview";
    }


    @GetMapping("/addReview")
    public String addReview(Model model, Review review, HttpServletRequest req, HttpServletResponse res) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        review.setId(userId);
        reviewService.insertReview(review);

        //return "redirect:/lecture/getLecture?no=" + review.getPar();
        return "product/addReview";
    }


}
