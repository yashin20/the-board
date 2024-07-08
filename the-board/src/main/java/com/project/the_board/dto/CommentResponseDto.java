package com.project.the_board.dto;

import com.project.the_board.entity.Comment;
import lombok.Data;

@Data
public class CommentResponseDto {

    private Long id;
    private String content;
    private String memberNickname;
    private Integer likes;
    private String createdAt;
    private String updatedAt;

    private Long memberId;
    private Long postId;

    private Boolean liked; //현재 로그인된 회원이 좋아요를 눌렀는가?

    /*Entity -> DTO*/
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.memberNickname = comment.getMember().getNickname();
        this.likes = comment.getLikes();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.memberId = comment.getMember().getId();
        this.postId = comment.getPost().getId();
    }
}
