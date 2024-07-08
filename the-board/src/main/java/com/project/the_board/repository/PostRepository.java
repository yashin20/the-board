package com.project.the_board.repository;

import com.project.the_board.entity.Member;
import com.project.the_board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    //post 전체 목록 + paging
    Page<Post> findAll(Pageable pageable);

    //member 조건 post 목록 + paging + sort + searchKeyword
    Page<Post> findByMemberAndTitleContaining(Member member, String searchKeyword, Pageable pageable);

    //member 조건 post 목록 + paging + sort
    Page<Post> findByMember(Member member, Pageable pageable);

    //title 검색 + paging
    Page<Post> findByTitleContaining(String searchKeyword, Pageable pageable);
}
