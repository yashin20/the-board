package com.project.the_board.controller;

import com.project.the_board.dto.CommentRequestDto;
import com.project.the_board.dto.PostRequestDto;
import com.project.the_board.entity.Comment;
import com.project.the_board.service.CommentService;
import com.project.the_board.service.MemberService;
import com.project.the_board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    /*post 소속 comment List
     * "/comments/{postId}" */
    @GetMapping("/{postId}")
    public Page<Comment> getCommentByPostId(@PathVariable Long postId,
                                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.findCommentList(postId, pageable);
    }

    /*comment 저장 Post 요청
     * "/comments/{postId}/new" */
    @PostMapping("/{postId}/new")
    public String saveComment(@PathVariable Long postId,
                              @ModelAttribute("createCommentForm") @Validated(CommentRequestDto.Create.class) CommentRequestDto dto) {

        dto.setMember(memberService.getCurrentMember());
        dto.setPost(postService.findPostById(postId));
        commentService.createComment(dto);

        return "redirect:/posts/" + postId;
    }

    /*comment 수정 Post 요청
     * "/comments/{commentId}/update" */
    @PostMapping("/{commentId}/update")
    public String updateComment(@PathVariable("commentId") Long commentId,
                                @RequestParam("content") String content) {

        Comment updatedComment = commentService.findCommentById(commentId);
        CommentRequestDto commentRequestDto = new CommentRequestDto(commentId, content);
        commentService.updateComment(commentRequestDto);

        return "redirect:/posts/" + updatedComment.getPost().getId();
    }

    /*comment 삭제 Post 요청
     * "/comments/{commentId}/delete" */
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId) {

        Comment deletedComment = commentService.findCommentById(commentId);
        commentService.deleteComment(commentId);

        return "redirect:/posts/" + deletedComment.getPost().getId();
    }
}
