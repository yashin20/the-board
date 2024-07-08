package com.project.the_board.controller;

import com.project.the_board.dto.CommentRequestDto;
import com.project.the_board.dto.CommentResponseDto;
import com.project.the_board.dto.PostRequestDto;
import com.project.the_board.dto.PostResponseDto;
import com.project.the_board.entity.Comment;
import com.project.the_board.entity.Member;
import com.project.the_board.entity.Post;
import com.project.the_board.exception.DataAlreadyExistsException;
import com.project.the_board.exception.DataNotFoundException;
import com.project.the_board.exception.PasswordCheckFailedException;
import com.project.the_board.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final CommentLikesService commentLikesService;

    /**
     * 게시글(Post) 생성 - "/posts/new"
     */
    @GetMapping("/new")
    public String createPostForm(Model model) {
        //post 작성 폼
        model.addAttribute("postCreateForm", new PostRequestDto());

        return "posts/create-post";
    }

    @PostMapping("/new")
    public String createForm(@ModelAttribute("postCreateForm") @Validated(PostRequestDto.Create.class) PostRequestDto dto,
                             BindingResult bindingResult, Model model) {
        //현재 로그인된 Member
        Member currentMember = memberService.getCurrentMember();
        //현재 로그인된 Username
        model.addAttribute("signedMember", currentMember.getUsername());


        /* '유효성 검사' 에러처리 */
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);

            return "posts/create-post";
        }


        try {
            dto.setMember(currentMember);

            //Post create 로직
            postService.createPost(dto);
        }
        /* '중복 검사' + 'PW 이중검사' 에러처리 */ catch (DataNotFoundException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "posts/create-post";
        }

        return "redirect:/";
    }


    /**
     * 게시글(Post) 상세 정보 - "/posts/{postId}"
     * 해당 게시글의 댓글 목록 + 작성 폼
     */
    @GetMapping("/{postId}")
    public String postInfo(@PathVariable Long postId,
                           @PageableDefault(sort = "id", size = 5, direction = Sort.Direction.DESC) Pageable pageable,
                           Model model) {
        /* Header.html - 현재 로그인된 회원 */
        Member currentMember = memberService.getCurrentMember();
        model.addAttribute("memberId", currentMember.getId());
        model.addAttribute("signedMember", currentMember.getUsername());

        /* 게시글 상세정보 */
        PostResponseDto postInfo = postService.getPostInfo(postId);
        model.addAttribute("postInfo", postInfo);

        /* 좋아요 여부 */
        boolean isLiked = likesService.isLikedByMember(currentMember.getId(), postId);
        model.addAttribute("isLiked", isLiked);

        /**Comment List**/
        /* 댓글 작성 폼 */
        model.addAttribute("createCommentForm", new CommentRequestDto());

        /* 댓글 목록 */
        Page<Comment> commentList = commentService.findCommentList(postId, pageable);
        Page<CommentResponseDto> list = commentList.map(comment -> {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDto.setLiked((commentLikesService.isLikedByMember(currentMember.getId(), comment.getId())));
            return commentResponseDto;
        });

        model.addAttribute("comments", list); //comment dto list
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전 페이지 정보
        model.addAttribute("next", pageable.next().getPageNumber()); //다음 페이지 정보
        model.addAttribute("hasPrevious", list.hasPrevious()); //이전 페이지 존재 여부
        model.addAttribute("hasNext", list.hasNext()); //다음 페이지 존재 여부

        /* 페이지 번호 */
        int currentPage = pageable.getPageNumber() + 1; //현재 페이지 정보(User side)
        model.addAttribute("current", currentPage);

        int blockSize = 5;
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1; //블럭 시작 페이지
        int endPage = Math.min(startPage + blockSize - 1, list.getTotalPages()); //블럭 마지막 페이지

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "posts/post-info";
    }


    /**
     * 게시글(Post) 수정 - "/posts/{postId}/update"
     */
    @GetMapping("/{postId}/update")
    public String postUpdateForm(@PathVariable Long postId, Model model) {
        Post post = postService.findPostById(postId);
        model.addAttribute("postUpdateForm", new PostRequestDto(post.getId(), post.getTitle(), post.getContent()));
        return "posts/update-post";
    }

    @PostMapping("/{postId}/update")
    public String postUpdate(@PathVariable Long postId,
                             @ModelAttribute("postUpdateForm") @Validated(PostRequestDto.Update.class) PostRequestDto dto,
                             BindingResult bindingResult, Model model) {
        /* '유효성 검사' 에러처리 */
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);

            return "posts/update-post";
        }

        try {
            //Post update 로직
            postService.updatePost(dto);
        }
        /* '중복 검사' + 'PW 이중검사' 에러처리 */ catch (DataNotFoundException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "posts/update-post";
        }

        return "redirect:/posts/" + postId;
    }


    /**
     * 게시글(Post) 삭제 - "/posts/{postId}/delete"
     */
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }


    /**
     * 사용자(Member) 작성 게시글(Post) 목록 -  - "/posts/{memberId}/list"
     * 사용자 닉네임을 클릭하면, 해당 사용자가 작성한 게시글 목록을 보여줌
     * 검색 조건 : member + sort + keyword
     */
    @GetMapping("/{memberId}/list")
    public String postListByMember(@PathVariable Long memberId,
                                   @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "sort", required = false) String sort,
                                   Model model) {
        //현재 로그인된 Username (Header.html)
        model.addAttribute("signedMember", memberService.getCurrentUsername());


        /** 검색 조건 : member **/
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("memberNickname", member.getNickname());

        /** 검색 조건 : sort - 정렬 조건 **/
        if ("createdAt".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if ("likes".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "likes"));
        } else if ("views".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "views"));
        } else if ("createdAtA".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "createdAt"));
        } else if ("likesA".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "likes"));
        } else if ("viewsA".equals(sort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "views"));
        }

        Page<Post> posts = (keyword != null && !keyword.isEmpty())
                ? postService.findPostByMemberAndKeyword(member, keyword, pageable)
                : postService.findPostByMember(member, pageable);
        Page<PostResponseDto> list = posts.map(PostResponseDto::new);

        model.addAttribute("posts", list); //task dto list
        model.addAttribute("keyword", keyword); //검색 키워드
        model.addAttribute("sort", sort); //정렬 조건
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전 페이지 정보
        model.addAttribute("next", pageable.next().getPageNumber()); //다음 페이지 정보
        model.addAttribute("hasPrevious", list.hasPrevious()); //이전 페이지 존재 여부
        model.addAttribute("hasNext", list.hasNext()); //다음 페이지 존재 여부


        /* 페이지 번호 */
        int currentPage = pageable.getPageNumber() + 1; //현재 페이지 정보(User side)
        model.addAttribute("current", currentPage);

        int blockSize = 5;
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1; //블럭 시작 페이지
        int endPage = Math.min(startPage + blockSize - 1, list.getTotalPages()); //블럭 마지막 페이지

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "posts/member-post-list";
    }

}
