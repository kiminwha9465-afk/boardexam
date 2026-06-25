package com.example.board0625.controller;

import com.example.board0625.dto.CommentDTO;
import com.example.board0625.dto.MemberDTO;
import com.example.board0625.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public String write(CommentDTO comment,
                        @RequestParam(defaultValue = "1") int commentPage,
                        HttpSession session) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        comment.setMemberId(loginMember.getId());
        commentService.write(comment);
        // go to last page after writing so new comment is visible
        return "redirect:/board/detail/" + comment.getBoardId() + "?commentPage=9999";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, CommentDTO comment,
                         @RequestParam(defaultValue = "1") int commentPage) {
        comment.setId(id);
        commentService.update(comment);
        return "redirect:/board/detail/" + comment.getBoardId() + "?commentPage=" + commentPage;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Integer boardId,
                         @RequestParam(defaultValue = "1") int commentPage) {
        commentService.delete(id);
        return "redirect:/board/detail/" + boardId + "?commentPage=" + commentPage;
    }
}
