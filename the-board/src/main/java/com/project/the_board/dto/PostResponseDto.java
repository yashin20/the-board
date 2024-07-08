package com.project.the_board.dto;

import com.project.the_board.entity.Post;
import lombok.Data;

@Data
public class PostResponseDto {

    /*post list - id, title, member - nickname, createdAt, views*/
    /*post information - id, title, content, member - nickname, createdAt, updatedAt, views, likes*/

    private Long id;
    private String title;
    private String content;

    private Long memberId;
    private String memberNickname;

    private String createdAt;
    private String updatedAt;

    private Integer views;
    private Integer likes;


    //Entity -> Dto
    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();

        this.memberId = entity.getMember().getId();
        this.memberNickname = entity.getMember().getNickname();

        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();

        this.views = entity.getViews();
        this.likes = entity.getLikes();
    }
}
