package com.project.the_board.dto;

import com.project.the_board.entity.Member;
import com.project.the_board.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRequestDto {

    public interface Create {}
    public interface Update {}

    /**
     * Post 요청 DTO
     * <p>
     * 등록 요청 : title, content, member
     * <p>
     * 조회 요청 : id
     * <p>
     * 수정 요청 : id, title, content
     * <p>
     * 삭제 요청 : id
     */

    @NotNull(groups = {Update.class}, message = "Title 은 필수 입력 값 입니다.")
    private Long id;

    @NotBlank(groups = {Create.class, Update.class}, message = "Title 은 필수 입력 값 입니다.")
    private String title;
    @NotBlank(groups = {Create.class, Update.class}, message = "Content 는 필수 입력 값 입니다.")
    private String content;

    private Member member;


    //DTO -> Entity
    public Post toEntity() {
        return new Post(
                this.id,
                this.title,
                this.content,
                this.member
        );
    }


    public PostRequestDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
