package com.project.the_board.service;

import com.project.the_board.entity.*;
import com.project.the_board.exception.DataNotFoundException;
import com.project.the_board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    /* member -> comment 좋아요(likes) 로직
     * 이미 좋아요가 눌러진 상태라면, 좋아요 취소*/
    @Transactional
    public Boolean toggleLike(Long memberId, Long commentId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("존재하지 않는 댓글입니다."));

        //이미 좋아요를 누른 경우
        if (commentLikesRepository.existsByMemberAndComment(member, comment)) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            CommentLikes commentLikes = commentLikesRepository.findByMemberAndComment(member, comment)
                    .orElseThrow(() -> new DataNotFoundException("좋아요 정보를 찾을 수 없습니다."));
            commentLikesRepository.delete(commentLikes); //likes 정보 삭제
            comment.decrementLikes(); //댓글 좋아요 수 -1
            return false;
        } else {
            CommentLikes commentLikes = new CommentLikes();
            commentLikes.setMember(member);
            commentLikes.setComment(comment);
            commentLikesRepository.save(commentLikes);
            comment.incrementLikes();
            return true;
        }
    }

    /*member -> comment 이미 좋아요를 눌렀는지 확인하는 로직*/
    public boolean isLikedByMember(Long memberId, Long commentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 댓글입니다."));

        /*이미 좋아요 누름 : true
         * 좋아요 누른적 없음 : false*/
        return commentLikesRepository.existsByMemberAndComment(member, comment);
    }

}
