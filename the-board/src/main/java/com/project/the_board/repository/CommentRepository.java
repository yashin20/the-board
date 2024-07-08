package com.project.the_board.repository;

import com.project.the_board.entity.Comment;
import com.project.the_board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /*특성 게시글(Post)에 소속된 댓글 목록*/
    Page<Comment> findByPost(Post post, Pageable pageable);
}
