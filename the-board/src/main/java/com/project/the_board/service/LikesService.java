package com.project.the_board.service;

import com.project.the_board.entity.Likes;
import com.project.the_board.entity.Member;
import com.project.the_board.entity.Post;
import com.project.the_board.exception.DataNotFoundException;
import com.project.the_board.repository.LikesRepository;
import com.project.the_board.repository.MemberRepository;
import com.project.the_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /* member -> post 좋아요(likes) 로직
    * 이미 좋아요가 눌러진 상태라면, 좋아요 취소*/
    @Transactional
    public void toggleLike(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("존재하지 않는 게시글입니다."));

        //이미 좋아요를 누른 경우
        if (likesRepository.existsByMemberAndPost(member, post)) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            Likes likes = likesRepository.findByMemberAndPost(member, post)
                    .orElseThrow(() -> new DataNotFoundException("좋아요 정보를 찾을 수 없습니다."));
            likesRepository.delete(likes); //likes 정보 삭제
            post.decrementLikes(); //게시글 좋아요 수 -1
        } else {
            Likes likes = new Likes();
            likes.setMember(member);
            likes.setPost(post);
            likesRepository.save(likes);
            post.incrementLikes();
        }
    }

    /*member -> post 이미 좋아요를 눌렀는지 확인하는 로직*/
    public boolean isLikedByMember(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 게시글입니다."));

        /*이미 좋아요 누름 : true
        * 좋아요 누른적 없음 : false*/
        return likesRepository.existsByMemberAndPost(member, post);
    }

}
