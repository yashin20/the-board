package com.project.the_board.service;

import com.project.the_board.dto.CommentRequestDto;
import com.project.the_board.entity.Comment;
import com.project.the_board.entity.Member;
import com.project.the_board.entity.Post;
import com.project.the_board.exception.DataNotFoundException;
import com.project.the_board.exception.UnauthorizedAccessException;
import com.project.the_board.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /** Create Comment */
    @Transactional
    public Long createComment(CommentRequestDto dto) {
        /*1. create entity*/
        Comment comment = dto.toEntity();

        /*if (부모 댓글이 있다면) - comment.parent 업데이트
        * else - comment.parent = null 유지 */
        if (dto.getParent() != null) {
            comment.parentUpdate(dto.getParent());
        }

        /*2. save entity*/
        Comment savedComment = commentRepository.save(comment);

        return savedComment.getId();
    }


    /** Read Comment */

    /* id 기반 단건 조회 */
    public Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 댓글입니다."));
    }

    /* 특정 Post's id 기반 리스트 조회 */
    public Page<Comment> findCommentList(Long postId, Pageable pageable) {
        /* 1. 특정 Post 찾기 */
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 게시글입니다."));

        /* 2. comment list 조회 */
        return commentRepository.findByPost(post, pageable);
    }

    /* 부모 Comment List 조회
     * Post ID + Parent Comment == null */
    public Page<Comment> findParentList(Long postId, Pageable pageable) {
        /* 1. 특정 Post 찾기 */
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 게시글입니다."));

        /* 2. comment list 조회 */
        return commentRepository.findByPostAndParentIsNull(post, pageable);
    }

    /* 자식 Comment List 조회
     * Post ID + Parent Comment ID */
    public Page<Comment> findCommentListByParent(Long postId, Long parentId, Pageable pageable) {
        /* 1. 특정 Post 찾기 */
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 게시글입니다."));

        /* 2. 부모 Comment 찾기 */
        Comment parentComment = commentRepository.findById(parentId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 댓글입니다."));

        /* 3. comment list 조회 */
        return commentRepository.findByPostAndParent(post, parentComment, pageable);
    }


    /** Update Comment */
    @Transactional
    public Comment updateComment(CommentRequestDto dto) {
        /*1. update 대상 comment 찾기*/
        Comment comment = commentRepository.findById(dto.getId())
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 댓글입니다."));

        // '작성자 == 로그인 회원' 확인 절차
        if (!isAuthorValidation(comment.getId())) {
            throw new UnauthorizedAccessException("잘못된 접근 - 현재 로그인 회원이 작성자와 일치하지 않습니다.");
        }

        /*2. comment update*/
        comment.update(dto);

        /*3. comment return*/
        return comment;
    }


    /** Delete Comment */
    @Transactional
    public Long deleteComment(Long id) {
        /*1. delete 대상 comment 찾기*/
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 댓글입니다."));

        /*'작성자 == 로그인 회원' 확인 절차*/
        if (!isAuthorValidation(comment.getId())) {
            throw new UnauthorizedAccessException("잘못된 접근 - 현재 로그인 회원이 작성자와 일치하지 않습니다.");
        }

        /*삭제 주체
        * 1) 자식이 있는 부모 댓글 - isDeleted = true 로 변경 -> content -> "삭제된 댓글 입니다."
        * 2) (Leaf Node) 자식 댓글, 자식이 없는 부모댓글 - 즉시 삭제*/
        if (!comment.getChildren().isEmpty()) {
            comment.changeIsDeleted(true); //isDeleted = true 로 변경
            comment.changeContent("삭제된 댓글 입니다.");
        } else {
            /*2. comment delete*/
            commentRepository.delete(comment);
        }

        /*3. comment id return*/
        return id;
    }




    /* '작성자' == '로그인 회원' 검증 함수 */
    public Boolean isAuthorValidation(Long commentId) {
        /*1. 대상 comment 찾기*/
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 댓글입니다."));

        //2. 현재 로그인 회원 - currentMember
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Member currentMember = memberRepository.findByUsername(name)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));

        //3. 'id' 으로 '작성자' , '로그인 회원' 비교
        return comment.getMember().getId().equals(currentMember.getId());
    }

    /* 댓글 좋아요 */
    @Transactional
    public void incrementLikes(Long commentId) {
        /*1. 대상 comment 찾기*/
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 댓글입니다."));
        comment.incrementLikes();
    }

}
