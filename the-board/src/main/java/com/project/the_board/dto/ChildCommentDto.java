package com.project.the_board.dto;

import com.project.the_board.entity.Comment;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

@Data
public class ChildCommentDto {

    private Long id;
    private String content;
    private String memberNickname;
    private Integer likes;
    private String createdAt;
    private String updatedAt;

    //부모 댓글 ID
    private Long parentId;

    private Long memberId;
    private Long postId;

    private Boolean liked; //현재 로그인된 회원이 좋아요를 눌렀는가?

    /*Entity -> DTO*/
    public ChildCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.memberNickname = comment.getMember().getNickname();
        this.likes = comment.getLikes();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        }
        this.memberId = comment.getMember().getId();
        this.postId = comment.getPost().getId();
    }
}
