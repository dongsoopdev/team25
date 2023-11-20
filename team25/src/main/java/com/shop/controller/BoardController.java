package com.shop.controller;

import com.shop.domain.Board;
import com.shop.domain.User;
import com.shop.service.BoardService;
import com.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board/")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;
    @GetMapping("/insertBoard")
    public String insertBoard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        User user = userService.findByUserId(userId);
        model.addAttribute("user",user);
        return "board/insertBoard";
    }

    @PostMapping("/insertBoard")
    public String insertBoardPro(Board board) {
        int check = boardService.insertBoard(board);
        if (check == 1) {
            log.info("글 작성 성공");
            return "redirect:/";
        }
        else {
            log.info("글 작성 실패");
            return "redirect:/";
        }
    }
}
