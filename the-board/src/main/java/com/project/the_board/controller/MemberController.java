package com.project.the_board.controller;

import com.project.the_board.dto.MemberRequestDto;
import com.project.the_board.dto.MemberResponseDto;
import com.project.the_board.entity.Member;
import com.project.the_board.exception.DataAlreadyExistsException;
import com.project.the_board.exception.PasswordCheckFailedException;
import com.project.the_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인(Login) - "/members/login"
     */
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        model.addAttribute("loginForm", new MemberRequestDto());
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        return "members/login";
    }

    /**
     * 회원가입(Join) - "/members/join"
     */
    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("joinForm", new MemberRequestDto());
        return "members/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("joinForm") @Validated(MemberRequestDto.Create.class) MemberRequestDto dto,
                       BindingResult bindingResult, Model model) {

        /* '유효성 검사' 에러처리 */
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);

            return "members/join";
        }

        try {
            //회원가입 로직
            memberService.createMember(dto);
        }
        /* '중복 검사' + 'PW 이중검사' 에러처리 */ catch (DataAlreadyExistsException | PasswordCheckFailedException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "members/join";
        }

        return "redirect:/";
    }

    /**
     * 회원 상세 정보 표시 - "/members/info"
     */
    @GetMapping("/info")
    public String memberInformation(Model model) {
        //현재 로그인된 Username
        model.addAttribute("signedMember", memberService.getCurrentUsername());

        Member currentMember = memberService.getCurrentMember();
        MemberResponseDto responseDto = new MemberResponseDto(currentMember);
        model.addAttribute("infoForm", responseDto);

        return "members/info";
    }


    /**
     * 회원 수정(Update)
     * 1. 회원 정보 수정 (nickname, email, phone) - "/members/info/update"
     * 2. 비밀번호 수정 (password) - "/members/pw-update"
     */
    /**
     * 1. 회원 정보 수정 (nickname, email, phone) - "/members/info/update"
     */
    @GetMapping("/info/update")
    public String updateForm(Model model) {
        //현재 로그인된 Username
        model.addAttribute("signedMember", memberService.getCurrentUsername());

        Member currentMember = memberService.getCurrentMember();
        MemberResponseDto responseDto = new MemberResponseDto(currentMember);
        //변경하지 않는 요소(username, createdAt, updatedAt)
        model.addAttribute("onlyRead", responseDto);

        //변경 가능한 요소(nickname, phone, email)
        model.addAttribute("updateForm", new MemberRequestDto(responseDto.getId(),
                responseDto.getNickname(),
                responseDto.getEmail(),
                responseDto.getPhone()));
        return "members/info-update";
    }

    @PostMapping("/info/update")
    public String update(@ModelAttribute("updateForm") @Validated(MemberRequestDto.Update.class) MemberRequestDto dto,
                         BindingResult bindingResult, Model model) {

        // 현재 로그인된 회원 객체
        Member currentMember = memberService.getCurrentMember();

        /* '유효성 검사' 에러처리 */
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "members/info-update";
        }


        try {
            //회원 정보 업데이트
            memberService.updateMember(dto);
        }
        // 중복 검사 오류 시, 에러 처리 로직
        catch (DataAlreadyExistsException | PasswordCheckFailedException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "members/info-update";
        }

        return "redirect:/members/info";
    }


    /**
     * 2. 비밀번호 수정 (password) - "/members/pw-update"
     */
    @GetMapping("/pw-update")
    public String passwordUpdateForm(Model model) {
        //현재 로그인된 Username
        model.addAttribute("signedMember", memberService.getCurrentUsername());

        Member currentMember = memberService.getCurrentMember();

        model.addAttribute("passwordUpdateForm", new MemberRequestDto(currentMember.getId(),
                "", "", "", true));
        return "members/password-update";
    }

    @PostMapping("/pw-update")
    public String passwordUpdate(@ModelAttribute("passwordUpdateForm") @Validated(MemberRequestDto.UpdatePw.class) MemberRequestDto dto,
                                 BindingResult bindingResult, Model model) {


        // 현재 로그인된 회원 객체
        Member currentMember = memberService.getCurrentMember();

        /* '유효성 검사' 에러처리 */
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "members/info-update";
        }


        try {
            //회원 정보 업데이트
            memberService.updateMember(dto);
        }
        // 중복 검사 오류 시, 에러 처리 로직
        catch (DataAlreadyExistsException | PasswordCheckFailedException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "members/info-update";
        }



        return "redirect:/members/info";
    }
}
