package com.project.the_board.controller;

import com.project.the_board.dto.PostResponseDto;
import com.project.the_board.entity.Post;
import com.project.the_board.service.MemberService;
import com.project.the_board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/")
    public String home(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "sort", required = false) String sort,
                       Model model) {
        //현재 로그인된 Username
        model.addAttribute("signedMember", memberService.getCurrentUsername());


        /* 정렬 조건 설정 (DESC / ASC) */
        if ("createdAt".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if ("likes".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "likes"));
        } else if ("views".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "views"));
        }

        else if ("createdAtA".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "createdAt"));
        } else if ("likesA".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "likes"));
        } else if ("viewsA".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "views"));
        }


        Page<Post> posts = (keyword != null && !keyword.isEmpty())
                            ? postService.searchPost(keyword, pageable)
                            : postService.findPostAll(pageable);
        Page<PostResponseDto> list = posts.map(PostResponseDto::new);

        model.addAttribute("posts", list); //task dto list
        model.addAttribute("keyword", keyword); //검색 키워드
        model.addAttribute("sort", sort); //정렬 조건
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전 페이지 정보
        model.addAttribute("next", pageable.next().getPageNumber()); //다음 페이지 정보
        model.addAttribute("hasPrevious", list.hasPrevious()); //이전 페이지 존재 여부
        model.addAttribute("hasNext", list.hasNext()); //다음 페이지 존재 여부


        //페이지 번호
        /** 페이지 블록 계산
         * currentPage = 5
         * User : 5 , Spring : 4
         * startPage = 1
         * endPage = 5
         * <= 1 2 3 4 5 =>
         */
        int currentPage = pageable.getPageNumber() + 1; //현재 페이지 정보(User side)
        model.addAttribute("current", currentPage);

        int blockSize = 5;
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1; //블럭 시작 페이지
        int endPage = Math.min(startPage + blockSize - 1, list.getTotalPages()); //블럭 마지막 페이지

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }
}
