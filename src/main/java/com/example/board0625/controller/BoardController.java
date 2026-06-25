package com.example.board0625.controller;

import com.example.board0625.dto.BoardDTO;
import com.example.board0625.dto.BoardImageDTO;
import com.example.board0625.dto.MemberDTO;
import com.example.board0625.dto.PageDTO;
import com.example.board0625.service.BoardService;
import com.example.board0625.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService   boardService;
    private final CommentService commentService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @GetMapping({"/", "/board/list"})
    public String list(PageDTO page, Model model) {
        int totalCount = boardService.getTotalCount(page);
        page.setTotalCount(totalCount);
        page.calculate();
        model.addAttribute("boards", boardService.getAll(page));
        model.addAttribute("page", page);
        return "board/list";
    }

    private static final int COMMENT_PAGE_SIZE = 10;

    @GetMapping("/board/detail/{id}")
    public String detail(@PathVariable Integer id,
                         @RequestParam(defaultValue = "1") int commentPage,
                         Model model) {
        model.addAttribute("board", boardService.getById(id));
        model.addAttribute("images", boardService.getImages(id));

        int totalComments = commentService.countByBoardId(id);
        int totalCommentPages = (int) Math.ceil((double) totalComments / COMMENT_PAGE_SIZE);
        if (totalCommentPages < 1) totalCommentPages = 1;
        if (commentPage < 1) commentPage = 1;
        if (commentPage > totalCommentPages) commentPage = totalCommentPages;

        model.addAttribute("comments", commentService.getByBoardIdPaged(id, commentPage, COMMENT_PAGE_SIZE));
        model.addAttribute("totalComments", totalComments);
        model.addAttribute("commentPage", commentPage);
        model.addAttribute("commentTotalPages", totalCommentPages);
        return "board/detail";
    }

    @GetMapping("/board/write")
    public String writeForm() {
        return "board/write";
    }

    @PostMapping("/board/write")
    public String write(BoardDTO board,
                        @RequestParam(value = "imageFilenames", required = false, defaultValue = "") String imageFilenames,
                        HttpSession session, RedirectAttributes rttr) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        board.setMemberId(loginMember.getId());
        boardService.write(board);

        saveImageFilenames(board.getId(), imageFilenames);

        rttr.addFlashAttribute("message", "게시글이 등록되었습니다.");
        return "redirect:/board/list";
    }

    @GetMapping("/board/edit/{id}")
    public String editForm(@PathVariable Integer id, HttpSession session, Model model) {
        BoardDTO board = boardService.getByIdNoCount(id);
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        if (!loginMember.getId().equals(board.getMemberId())) {
            return "redirect:/board/detail/" + id;
        }
        model.addAttribute("board", board);
        model.addAttribute("images", boardService.getImages(id));
        return "board/edit";
    }

    @PostMapping("/board/edit/{id}")
    public String edit(@PathVariable Integer id, BoardDTO board,
                       @RequestParam(value = "imageFilenames", required = false, defaultValue = "") String imageFilenames,
                       RedirectAttributes rttr) {
        board.setId(id);
        boardService.update(board);
        saveImageFilenames(id, imageFilenames);
        rttr.addFlashAttribute("message", "게시글이 수정되었습니다.");
        return "redirect:/board/detail/" + id;
    }

    @PostMapping("/board/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes rttr) {
        boardService.delete(id);
        rttr.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        return "redirect:/board/list";
    }

    /* ===== 이미지 업로드 API ===== */
    @PostMapping("/board/image/upload")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam MultipartFile file) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error", "파일이 없습니다."));
            String original = Objects.requireNonNull(file.getOriginalFilename());
            String ext      = original.substring(original.lastIndexOf('.'));
            String filename = UUID.randomUUID() + ext;
            file.transferTo(new File(uploadDir + "/" + filename));
            return ResponseEntity.ok(Map.of("filename", filename, "url", "/uploads/" + filename));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    /* ===== 이미지 삭제 API ===== */
    @PostMapping("/board/image/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteImage(@PathVariable Integer id) {
        BoardImageDTO img = boardService.getImageById(id);
        if (img != null) {
            new File(uploadDir + "/" + img.getImagePath()).delete();
            boardService.deleteImageById(id);
        }
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    /* ===== 헬퍼 ===== */
    private void saveImageFilenames(Integer boardId, String imageFilenames) {
        if (imageFilenames == null || imageFilenames.isBlank()) return;
        List<String> paths = Arrays.asList(imageFilenames.split(","));
        boardService.saveImages(boardId, paths);
    }
}
