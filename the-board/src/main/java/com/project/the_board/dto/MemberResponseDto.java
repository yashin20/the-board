package com.project.the_board.dto;

import com.project.the_board.entity.Member;
import com.project.the_board.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto implements Serializable {

    /**
     * Member 응답 DTO
     * <p>
     * 등록 요청 - return : id
     * <p>
     * 조회 요청 - return : id, username, password, email, phone, createdAt, updatedAt
     * <p>
     * 수정 요청 - return : id, username, password, email, phone, createdAt, updatedAt
     * <p>
     * 삭제 요청 - return : id
     */

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;

    private MemberRole role;

    private String createdAt;
    private String updatedAt;


    //등록 / 삭제 요청 - return
    public MemberResponseDto(Long id) {
        this.id = id;
    }

    //Entity -> DTO
    //조회 / 수정 요청 - return
    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();

        this.nickname = entity.getNickname();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();

        this.role = entity.getMemberRole();

        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }


}
