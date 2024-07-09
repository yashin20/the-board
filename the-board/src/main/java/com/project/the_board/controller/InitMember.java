package com.project.the_board.controller;

import com.project.the_board.dto.CommentRequestDto;
import com.project.the_board.dto.MemberRequestDto;
import com.project.the_board.dto.PostRequestDto;
import com.project.the_board.entity.Comment;
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

            for (int i = 1; i <= 60; i++) {

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

            /** 대댓글 테스트 데이터 **/
            /*61 번째 게시글*/
            PostRequestDto dto = new PostRequestDto();
            dto.setTitle("comment children test");
            dto.setContent("content");
            dto.setMember(memberService.getMemberById(memberId2));
            Long postId = postService.createPost(dto); /*61 번째 게시글*/

            /*부모 댓글*/
            CommentRequestDto dto1 = new CommentRequestDto();
            dto1.setContent("parent comment");
            dto1.setMember(memberService.getMemberById(memberId2));
            dto1.setPost(postService.findPostById(postId));
            Long parentCommentId = commentService.createComment(dto1);

            /*자식 댓글 1*/
            CommentRequestDto dto2 = new CommentRequestDto();
            dto2.setContent("child1 comment");
            dto2.setMember(memberService.getMemberById(memberId2));
            dto2.setPost(postService.findPostById(postId));
            Comment parentComment = commentService.findCommentById(parentCommentId);
            dto2.setParent(parentComment);
            commentService.createComment(dto2);

            /*자식 댓글 2*/
            CommentRequestDto dto3 = new CommentRequestDto();
            dto3.setContent("child2 comment");
            dto3.setMember(memberService.getMemberById(memberId2));
            dto3.setPost(postService.findPostById(postId));
            dto3.setParent(parentComment);
            commentService.createComment(dto3);
        }
    }
}
