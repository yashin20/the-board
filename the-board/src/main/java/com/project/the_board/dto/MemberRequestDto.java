package com.project.the_board.dto;

import com.project.the_board.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRequestDto {

    /*회원 생성 : username, password, passwordCheck, nickname, email, phone */
    public interface Create {}

    /*회원정보 업데이트 : id, nickname, email, phone */
    public interface Update {}

    /*비밀번호 업데이트 : id, currentPassword, password, passwordCheck */
    public interface UpdatePw {}

    /*회원 조회, 회원 삭제 : id */
    public interface IdOnly {}


    @NotNull(groups = {Update.class, UpdatePw.class, IdOnly.class}, message = "ID는 필수 입력 값입니다.")
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", groups = Create.class,
            message = "아이디는 영문 대소문자, 숫자로 이루어진 4~20자리여야 합니다.")
    @NotBlank(groups = Create.class, message = "아이디는 필수 입력 값입니다.")
    private String username;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", groups = {Create.class, UpdatePw.class},
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @NotBlank(groups = {Create.class, UpdatePw.class}, message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", groups = {Create.class, UpdatePw.class},
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @NotBlank(groups = {Create.class, UpdatePw.class}, message = "비밀번호는 필수 입력 값입니다.")
    private String passwordCheck;

    @NotBlank(groups = {Create.class, Update.class}, message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", groups = {Create.class, Update.class},
            message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(groups = {Create.class, Update.class}, message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", groups = {Create.class, Update.class},
            message = "전화번호 형식이 올바르지 않습니다.")
    @NotBlank(groups = {Create.class, Update.class}, message = "전화번호는 필수 입력 값입니다.")
    private String phone;



    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", groups = UpdatePw.class,
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @NotBlank(groups = UpdatePw.class, message = "비밀번호는 필수 입력 값입니다.")
    private String currentPassword;


    public Member toEntity() {
        return new Member(
                this.id,
                this.username,
                this.password,
                this.nickname,
                this.email,
                this.phone);
    }


    public MemberRequestDto(Long id, String nickname, String email, String phone) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
    }


    public MemberRequestDto(Long id, String currentPassword, String password, String passwordCheck, Boolean isPasswordUpdate) {
        this.id = id;
        this.currentPassword = currentPassword;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
