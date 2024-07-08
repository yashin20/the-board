package com.project.the_board.service;

import com.project.the_board.dto.PostRequestDto;
import com.project.the_board.dto.PostResponseDto;
import com.project.the_board.entity.Member;
import com.project.the_board.entity.Post;
import com.project.the_board.exception.DataNotFoundException;
import com.project.the_board.exception.UnauthorizedAccessException;
import com.project.the_board.repository.MemberRepository;
import com.project.the_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /*게시글(Post) 생성*/
    @Transactional
    public Long createPost(PostRequestDto dto) {
        //1. dto -> post entity
        Post entity = dto.toEntity();

        //2. save entity
        Post savedPost = postRepository.save(entity);

        return savedPost.getId();
    }


    /*게시글(Post) 전체 조회*/
    public Page<Post> findPostAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /*게시글(Post) ID 기반 조회 로직*/
    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("PostService.findPostById : 존재하지 않는 게시글 입니다."));
    }

    /*게시글 상세 정보 조회 (+ 조회수 누적) */
    @Transactional
    public PostResponseDto getPostInfo(Long postId) {
        //1. 조회 대상 post
        Post post = findPostById(postId);
        //2. 조회수 증가
        post.incrementView();

        return new PostResponseDto(post);
    }

    /*1. keyword O : 작성자(Member) 가 작성한 게시글(Post) 전체 조회*/
    public Page<Post> findPostByMemberAndKeyword(Member member, String searchKeyword, Pageable pageable) {
        return postRepository.findByMemberAndTitleContaining(member, searchKeyword, pageable);
    }

    /*2. keyword X : 작성자(Member) 가 작성한 게시글(Post) 전체 조회*/
    public Page<Post> findPostByMember(Member member, Pageable pageable) {
        return postRepository.findByMember(member, pageable);
    }

    /*searchKeyword 로 검색 조회*/
    public Page<Post> searchPost(String searchKeyword, Pageable pageable) {
        return postRepository.findByTitleContaining(searchKeyword, pageable);
    }


    /*게시글(Post) 수정
     * PostRequestDto.Update.class : id, title, content
     * "게시글 수정시 작성자 == 현재 로그인된 회원" 인 경우에만 가능*/
    @Transactional
    public Post updatePost(PostRequestDto dto) {
        //1. 수정대상 post
        Post post = findPostById(dto.getId());

        // '작성자 == 로그인 회원' 확인 절차
        if (!isAuthorValidation(post.getId())) {
            throw new UnauthorizedAccessException("잘못된 접근 - 현재 로그인 회원이 작성자와 일치하지 않습니다.");
        }

        //2. post 수정
        post.update(dto);

        //3. return value
        return post;
    }

    // '작성자' == '로그인 회원' 검증 함수
    public Boolean isAuthorValidation(Long postId) {
        //1. 수정대상 post
        Post post = findPostById(postId);

        //2. 현재 로그인 회원 - currentMember
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Member currentMember = memberRepository.findByUsername(name)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));

        //3. 'id' 으로 '작성자' , '로그인 회원' 비교
        return post.getMember().getId().equals(currentMember.getId());
    }


    /*게시글(Post) 삭제*/
    @Transactional
    public Long deletePost(Long postId) {
        //1. 삭제대상 post
        Post post = findPostById(postId);

        // '작성자 == 로그인 회원' 확인 절차
        if (!isAuthorValidation(post.getId())) {
            throw new UnauthorizedAccessException("잘못된 접근 - 현재 로그인 회원이 작성자와 일치하지 않습니다.");
        }

        //2. post 삭제
        postRepository.delete(post);

        return postId;
    }


    /*게시글 좋아요*/
    @Transactional
    public void incrementLikes(Long postId) {
        Post post = findPostById(postId);
        post.incrementLikes();
    }
}
