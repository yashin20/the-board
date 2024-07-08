package com.project.the_board.repository;

import com.project.the_board.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {

    Boolean existsByMemberAndComment(Member member, Comment comment);

    Optional<CommentLikes> findByMemberAndComment(Member member, Comment comment);
}
