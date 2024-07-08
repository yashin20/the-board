package com.project.the_board.entity;


import com.project.the_board.dto.MemberRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole = MemberRole.USER;

    private String nickname;
    private String email;
    private String phone;

//    private Image pfp; // 프로필 사진

    //작성 게시글 목록
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();



    //생성자
    public Member(Long id, String username, String password, String nickname, String email, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
    }


    //Update
    public void update(MemberRequestDto dto) {
        if (dto.getPassword() != null) {
            this.password = dto.getPassword();
        }
        if (dto.getNickname() != null) {
            this.nickname = dto.getNickname();
        }
        if (dto.getEmail() != null) {
            this.email = dto.getEmail();
        }
        if (dto.getPhone() != null) {
            this.phone = dto.getPhone();
        }
    }
}
