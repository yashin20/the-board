package com.project.the_board.service;

import com.project.the_board.dto.MemberRequestDto;
import com.project.the_board.entity.Member;
import com.project.the_board.exception.DataAlreadyExistsException;
import com.project.the_board.exception.DataNotFoundException;
import com.project.the_board.exception.PasswordCheckFailedException;
import com.project.the_board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /** 회원 생성
    * 1. 중복 검사 (username)
    * 2. password 이중검사
    * 3. password encoding
    */
    @Transactional
    public Long createMember(MemberRequestDto dto) {
        /* 1. 중복 검사 (username) */
        duplicateValidation(dto);
        /* 2. password 이중검사 */
        passwordDoubleCheck(dto);
        /* 3. password encoding */
        passwordEncoding(dto);

        Member entity = dto.toEntity();

        Member savedMember = memberRepository.save(entity);
        return savedMember.getId();
    }

    /* 1. 중복 검사 (username) */
    public void duplicateValidation(MemberRequestDto requestDto) {

        //username 중복 확인
        if (memberRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new DataAlreadyExistsException("이미 존재하는 'username' 입니다.");
        }
    }

    /* 2. password 이중검사 */
    public void passwordDoubleCheck(MemberRequestDto requestDto) {

        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new PasswordCheckFailedException("비밀번호가 동일하지 않습니다.");
        }
    }

    /* 3. password encoding */
    public void passwordEncoding(MemberRequestDto dto) {
        String encoded = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encoded);
    }


    /**
     * 회원 조회 (Member)
     */
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DataNotFoundException("MemberService.getMemberById : 존재하지 않는 회원 입니다."));
    }

    /**
     * 회원 수정 - nickname, password, email, phone
     */
    @Transactional
    public Member updateMember(MemberRequestDto dto) {
        /*회원정보 업데이트 (Update.class) : id, nickname, email, phone */
        /*비밀번호 업데이트 (UpdatePw.class) : id, currentPassword, password, passwordCheck */

        /*수정대상 member*/
        Member member = getMemberById(dto.getId());
        dto.setUsername(member.getUsername()); // authenticate 위해 username 주입
        //dto 구성요소 : id, username, currentPassword, password, passwordCheck

        /**
         * 비밀번호 수정시 고려사항
         * 1. 현재 비밀번호가 올바른가?
         * 2. newPassword.equal(newPasswordAgain) == true 인가?
         */
        /*비밀번호 수정 과정*/
        if (dto.getPassword() != null && dto.getPasswordCheck() != null &&
                !dto.getPassword().isEmpty() && !dto.getPasswordCheck().isEmpty()) {

            /* 1. 현재 비밀번호가 올바른가? */
            if (!authenticate(dto.getUsername(), dto.getCurrentPassword())) {
                throw new PasswordCheckFailedException("현재 비밀번호가 올바르지 않습니다.");
            }

            /* 2. newPassword.equal(newPasswordAgain) == true 인가? */
            passwordDoubleCheck(dto);

            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            dto.setPassword(encodedPassword); //비밀번호 인코딩
        }

        member.update(dto);

        return member;
    }

    /* 1. 로그인 인증 로직 */
    public boolean authenticate(String username, String password) {
        Member findMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("MemberService.authenticate : 존재하지 않는 회원 입니다."));

        return passwordEncoder.matches(password, findMember.getPassword());
    }


    /**
     * 회원 삭제
     */
    @Transactional
    public Long deleteMember(Long memberId) {
        /*삭제대상 member*/
        Member member = getMemberById(memberId);

        memberRepository.delete(member);
        return memberId;
    }


    /* 현재 로그인 회원 Username*/
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    /* 현재 로그인 회원*/
    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new DataNotFoundException("MemberService.getCurrentMember : 존재하지 않는 회원 입니다."));
    }

}
