package com.project.the_board.entity;

import com.project.the_board.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
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


    /*부모 댓글 존재 여부
     * 존재 - comment 객체
     * 존재 X - null */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    /*자식 댓글
     * "orphanRemoval = true" : 부모 댓글이 삭제될때 자식 댓글도 삭제됨.*/
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    /*삭제 여부*/
    private Boolean isDeleted = false;


    public Comment(Long id, String content, Member member, Post post) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.post = post;
    }

    //comment content update
    public void update(CommentRequestDto dto) {
        if (dto.getContent() != null) {
            this.content = dto.getContent();
        }
    }

    //parent update
    public void parentUpdate(Comment parent) {
        this.parent = parent;
    }

    public void changeIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void changeContent(String content) {
        this.content = content;
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
