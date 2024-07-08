package com.project.the_board.controller;

import com.project.the_board.dto.CommentRequestDto;
import com.project.the_board.dto.MemberRequestDto;
import com.project.the_board.dto.PostRequestDto;
import com.project.the_board.repository.MemberRepository;
import com.project.the_board.service.CommentService;
import com.project.the_board.service.MemberService;
import com.project.the_board.service.PostService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// Test Data 생성
@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitUserService initUserService;


    @PostConstruct
    public void init() {
        initUserService.init();
    }


    @Component
    static class InitUserService {
        @PersistenceContext
        private EntityManager em;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private MemberService memberService;
        @Autowired
        private MemberRepository memberRepository;
        @Autowired
        private PostService postService;
        @Autowired
        private CommentService commentService;

        @Transactional
        public void init() {

            MemberRequestDto requestDto1 = new MemberRequestDto();
            requestDto1.setUsername("member1");
            requestDto1.setPassword("1q2w3e4r~!");
            requestDto1.setPasswordCheck("1q2w3e4r~!");
            requestDto1.setNickname("nickname1");
            requestDto1.setEmail("member1@example.com");
            requestDto1.setPhone("010-1111-1111");
            Long memberId1 = memberService.createMember(requestDto1);

            MemberRequestDto requestDto2 = new MemberRequestDto();
            requestDto2.setUsername("member2");
            requestDto2.setPassword("1q2w3e4r~!");
            requestDto2.setPasswordCheck("1q2w3e4r~!");
            requestDto2.setNickname("nickname2");
            requestDto2.setEmail("member2@example.com");
            requestDto2.setPhone("010-2222-2222");
            Long memberId2 = memberService.createMember(requestDto2);

            for (int i = 1; i <= 85; i++) {

                if (i % 3 == 0) {
                    PostRequestDto dto = new PostRequestDto();
                    dto.setTitle("title" + i);
                    dto.setContent("content" + i);
                    dto.setMember(memberService.getMemberById(memberId2));
                    Long postId = postService.createPost(dto);

                    for (int j = 0; j < 3; j++) {
                        CommentRequestDto commentRequestDto = new CommentRequestDto();
                        commentRequestDto.setContent("comment + post" + i);
                        commentRequestDto.setMember(memberService.getMemberById(memberId1));
                        commentRequestDto.setPost(postService.findPostById(postId));
                        commentService.createComment(commentRequestDto);
                    }

                } else {
                    PostRequestDto dto = new PostRequestDto();
                    dto.setTitle("title" + i);
                    dto.setContent("content" + i);
                    dto.setMember(memberService.getMemberById(memberId1));
                    Long postId = postService.createPost(dto);

                    for (int j = 0; j < 3; j++) {
                        CommentRequestDto commentRequestDto = new CommentRequestDto();
                        commentRequestDto.setContent("comment + post" + i);
                        commentRequestDto.setMember(memberService.getMemberById(memberId2));
                        commentRequestDto.setPost(postService.findPostById(postId));
                        commentService.createComment(commentRequestDto);
                    }

                }
            }


        }
    }
}
