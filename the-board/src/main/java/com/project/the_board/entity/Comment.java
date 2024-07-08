package com.project.the_board.entity;

import com.project.the_board.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private Integer likes = 0; //좋아요

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; //소속 post


    public Comment(Long id, String content, Member member, Post post) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.post = post;
    }

    //comment update
    public void update(CommentRequestDto dto) {
        if (dto.getContent() != null) {
            this.content = dto.getContent();
        }
    }

    //likes increment
    public void incrementLikes() {
        this.likes += 1;
    }

    //likes decrement
    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes -= 1;
        }
    }
}
