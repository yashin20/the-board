package com.project.the_board.controller;

import com.project.the_board.exception.DataNotFoundException;
import com.project.the_board.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // 좋아요 토글 API
    @PostMapping("/{postId}/toggle")
    public String toggleLike(@RequestParam Long memberId, @PathVariable Long postId, RedirectAttributes redirectAttributes) {
        try {
            likesService.toggleLike(memberId, postId);
            redirectAttributes.addFlashAttribute("message", "좋아요 상태가 변경되었습니다.");
        } catch (DataNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/posts/" + postId;
    }

}
