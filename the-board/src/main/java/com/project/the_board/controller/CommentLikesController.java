package com.project.the_board.controller;

import com.project.the_board.exception.DataNotFoundException;
import com.project.the_board.service.CommentLikesService;
import com.project.the_board.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comment-likes")
@RequiredArgsConstructor
public class CommentLikesController {

    private final CommentLikesService commentLikesService;

    // 좋아요 토글 API
    /* "/comment-likes/{commentId}/toggle/{postId}" */
    @PostMapping("/{commentId}/toggle/{postId}")
    public String toggleLike(@RequestParam Long memberId, @PathVariable Long commentId, RedirectAttributes redirectAttributes,
                             @PathVariable Long postId) {
        try {
            commentLikesService.toggleLike(memberId, commentId);
            redirectAttributes.addFlashAttribute("message", "좋아요 상태가 변경되었습니다.");
        } catch (DataNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/posts/" + postId;
    }

}
