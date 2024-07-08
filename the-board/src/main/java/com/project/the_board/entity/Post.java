package com.project.the_board.entity;

import com.project.the_board.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    private Integer views = 0; //조회수
    private Integer likes = 0; //좋아요

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //작성자


    public Post(Long id, String title, String content, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    //post update
    public void update(PostRequestDto dto) {
        if (dto.getTitle() != null) {
            this.title = dto.getTitle();
        }
        if (dto.getContent() != null) {
            this.content = dto.getContent();
        }
    }

    //views increment
    public void incrementView() {
        this.views += 1;
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
