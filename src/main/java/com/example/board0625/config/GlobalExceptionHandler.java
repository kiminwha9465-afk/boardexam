package com.example.board0625.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSize(HttpServletRequest request, RedirectAttributes rttr) {
        rttr.addFlashAttribute("error", "파일 크기가 너무 큽니다. 파일당 최대 50MB까지 업로드 가능합니다.");
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/board/list");
    }
}
