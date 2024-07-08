package com.project.the_board.dto;

import com.project.the_board.entity.Comment;
import com.project.the_board.entity.Member;
import com.project.the_board.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestDto {

    /* content, member, post */
    public interface Create {}
    /* id, content */
    public interface Update {}

    @NotNull(groups = {CommentRequestDto.Update.class}, message = "Title 은 필수 입력 값 입니다.")
    private Long id;
    @NotBlank(groups = {CommentRequestDto.Create.class, CommentRequestDto.Update.class}, message = "Content 는 필수 입력 값 입니다.")
    private String content;

    private Member member; //작성자
    private Post post; //소속 게시글


    /* DTO -> Entity */
    public Comment toEntity() {
        return new Comment(
                this.id,
                this.content,
                this.member,
                this.post
        );
    }


    public CommentRequestDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
