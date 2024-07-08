package com.project.the_board.repository;

import com.project.the_board.entity.Likes;
import com.project.the_board.entity.Member;
import com.project.the_board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Boolean existsByMemberAndPost(Member member, Post post);

    Optional<Likes> findByMemberAndPost(Member member, Post post);
}
