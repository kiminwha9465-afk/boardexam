package com.example.board0625.controller;

import com.example.board0625.dto.MemberDTO;
import com.example.board0625.service.BoardService;
import com.example.board0625.service.CommentService;
import com.example.board0625.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService  memberService;
    private final BoardService   boardService;
    private final CommentService commentService;

    @GetMapping("/join")
    public String joinForm() {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(MemberDTO member, RedirectAttributes rttr) {
        try {
            memberService.join(member);
            rttr.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인해 주세요.");
            return "redirect:/member/login";
        } catch (IllegalArgumentException e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/join";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(String memberId, String password,
                        HttpSession session, RedirectAttributes rttr) {
        try {
            MemberDTO member = memberService.login(memberId, password);
            session.setAttribute("loginMember", member);
            return "redirect:/board/list";
        } catch (IllegalArgumentException e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/board/list";
    }

    private static final int MY_PAGE_SIZE = 10;

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model,
                         @RequestParam(defaultValue = "profile") String tab,
                         @RequestParam(defaultValue = "1") int page) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        model.addAttribute("member", memberService.getById(loginMember.getId()));

        int totalBoards = boardService.countByMemberId(loginMember.getId());
        int totalPages  = Math.max(1, (int) Math.ceil((double) totalBoards / MY_PAGE_SIZE));
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        model.addAttribute("boards", boardService.getByMemberIdPaged(loginMember.getId(), page, MY_PAGE_SIZE));
        model.addAttribute("totalBoards",     totalBoards);
        model.addAttribute("boardPage",       page);
        model.addAttribute("boardTotalPages", totalPages);

        int totalComments    = commentService.countByMemberId(loginMember.getId());
        int totalCommentPages = Math.max(1, (int) Math.ceil((double) totalComments / MY_PAGE_SIZE));
        int commentPage = page;
        if (commentPage < 1) commentPage = 1;
        if (commentPage > totalCommentPages) commentPage = totalCommentPages;

        model.addAttribute("myComments",          commentService.getByMemberIdPaged(loginMember.getId(), commentPage, MY_PAGE_SIZE));
        model.addAttribute("totalComments",        totalComments);
        model.addAttribute("commentPage",          commentPage);
        model.addAttribute("commentTotalPages",    totalCommentPages);
        model.addAttribute("tab", tab);
        return "member/mypage";
    }

    @PostMapping("/update")
    public String update(MemberDTO member, HttpSession session, RedirectAttributes rttr) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        member.setId(loginMember.getId());
        memberService.update(member);

        // 세션 닉네임 갱신
        MemberDTO updated = memberService.getById(loginMember.getId());
        session.setAttribute("loginMember", updated);

        rttr.addFlashAttribute("message", "회원정보가 수정되었습니다.");
        return "redirect:/member/mypage?tab=profile";
    }
}
