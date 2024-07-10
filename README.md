![image](https://github.com/yashin20/the-board/assets/92693776/93463335-61f5-4380-8f70-66074f848ec8)# SpringBoot-Project-'the-board'



## 목차
 - [1. 프로젝트 소개](#1-프로젝트-소개)
   - [1-1. 프로젝트 소개](#1-1-프로젝트-소개)
   - [1-2. 프로젝트 기능](#1-2-프로젝트-기능)
   - [1-3. 개발 환경](#1-3-개발-환경)
   - [1-4. 실행 화면](#1-4-실행-환경)
  
 - [2. 프로젝트 구조](#2-프로젝트-구조)
   - [2-1. 패키지 구조](#2-1-패키지-구조)
   - [2-2. DB 설계](#2-2-DB-설계)
   - [2-3. API 설계](#2-3-API-설계)
  
 - [개발 내용](#개발-내용)

 - [마치며](#마치며)
   - [1. 프로젝트 보완사항](#1-프로젝트-보완사항)
   - [2. 프로젝트 과정에서 발생한 문제](#2-프로젝트-과정에서-발생한-문제)
   - [3. 후기](#3-후기)
  
     


## 1. 프로젝트 소개


### 1-1. 프로젝트 소개

기존 게시판 프로젝트에 다양한 기능을 넣지 못해 굉장히 아쉬움이 있었다.
또한, 단순히 기능 구현 만이 목표였기 때문에, 체계적으로 코드를 구성하지 못한 것에도 역시 많은 아쉬움이 남았기에 다시한번 게시판 프로젝트를 진행하게 되었다.

### 1-2. 프로젝트 기능

'the-board' 의 주요 기능은 다음과 같습니다.

게시판  
- 게시글 CRUD 기능
- 게시글 정렬 기능 ('조회수', '작성일자', '좋아요' 오름 / 내림 차순)
- 게시글 페이징
- 게시글 검색 기능
- 게시글 좋아요 기능
- 게시글 공유 기능(URL 복사)

사용자
 - Security 회원가입 및 로그인 기능
 - 회원 정보 수정
 - 회원 탈퇴
 - 유효성 및 중복 검사

댓글
 - CRUD 기능
 - 좋아요 기능
 - 대댓글 기능
   

### 1-3. 개발 환경

#### Back-end
 - Java 21
 - SpringBoot 3.2.7
 - JPA(Spring Data JPA)
 - Spring Security

##### Build Tool
 - Gradle 8.7

##### DataBase
 - MySQL 8.0.36

#### Front-end
 - html/css
 - JavaScript
 - Thymeleaf
 - Bootstrap 5.3.3


### 1-4. 실행 화면
  
  <details>
    <summary>게시글</summary>
    
   **1. 게시글 전체 목록 ("/")**

   [로그인 X 화면]  
   ![image](https://github.com/yashin20/the-board/assets/92693776/3f5612a2-df74-4baa-ad87-79cd2feaccc0)  
   상단 네이게이션 바에 '로그인' , '회원가입' 버튼이 보이는 것을 볼 수 있다.  
 

   [로그인 O 화면]  
   ![image](https://github.com/yashin20/the-board/assets/92693776/e80a1840-6e80-4acd-9ce2-e948d3d2c1e4)  
   상단 네이게이션 바에 '[회원 닉네임]님 환영합니다!' , '로그아웃' 버튼이 보이는 것을 볼 수 있다.  
  
  
  
  
   **1-1. 게시글 전체 목록 정렬**  

   'Sort' 드롭다운 버튼을 통해, 정렬 기준을 선택할 수 있다.
   ![image](https://github.com/yashin20/the-board/assets/92693776/9d867928-bdd0-407e-a042-b52fc34fd039)  
   "생성 일자 내림차순", "생성 일자 오름차순", "좋아요 내림차순", "좋아요 오름차순", "조회수 내림차순", "조회수 오름차순"  



   '조회수' 기준으로 내림차순 정렬  
   ![image](https://github.com/yashin20/the-board/assets/92693776/dedcbe33-2bbb-4826-b5f9-f8de28a4fbdc)  

   '좋아요' 기준으로 내림차순 정렬  
   ![image](https://github.com/yashin20/the-board/assets/92693776/a8d725ab-3660-4169-8e20-c3caf1accd21)  

   '생성일자' 기준으로 내림차순 정렬  
   ![image](https://github.com/yashin20/the-board/assets/92693776/f672c5cc-7afd-42e1-a813-a0969c909e66)  

   ※ 오름차순은 모두 생략  
   ※ 로그인을 하지 않아도 게시글 정렬이 가능하다.  



   **2. 게시글 등록 화면**

   ![image](https://github.com/yashin20/the-board/assets/92693776/aee8e647-2a40-4de5-8521-254efc7f3a06)  

   로그인한 사용자만 게시글 작성이 가능하며, 작성 후 '게시' 버튼을 누르면 메인 페이지로 리다이렉트 된다.  

   

   **3. 게시글 상세 정보**

   ![image](https://github.com/yashin20/the-board/assets/92693776/701e27d3-2beb-4322-be38-1a2dc56dd4cd)  


   작성자 계정이 아닌 다른 계정으로 로그인 O  
   ![image](https://github.com/yashin20/the-board/assets/92693776/f97d3d0c-6639-4279-99c1-a9d38c2eb2bb)  

   ※ 작성자 계정이 아닌 다른 계정으로 로그인한 경우, 
     '게시글 수정하기', '게시글 삭제하기' 버튼이 보이지 않는다.  


   작성자 계정으로 로그인 O  
   ![image](https://github.com/yashin20/the-board/assets/92693776/fe1ded97-2f5d-490a-8dff-7b4b06d748e6)  

   ※ 작성자 계정으로 로그인 한 경우, '게시글 수정하기' 와 '게시글 삭제하기' 버튼을 통해 수정/삭제 를 할 수 있다.  



   **4. 게시글 수정 화면**

   ![image](https://github.com/yashin20/the-board/assets/92693776/aadd3f4f-8176-45e1-9df3-bbb22304492d)  

   게시글 수정 후, 'Save Post' 버튼을 눌러 수정을 마무리한다.    
   'Save Post' 버튼을 누르면 수정한 게시글 상세 정보 페이지로 이동한다.  

   [수정된 게시글 화면]  
   ![image](https://github.com/yashin20/the-board/assets/92693776/95dca49d-54ad-47f0-98e2-cd32023e097c)  

   

   **5. 게시글 삭제 화면**

   ![image](https://github.com/yashin20/the-board/assets/92693776/d8273e78-0af3-4334-9760-004e823a188d)  

   '게시글 삭제' 버튼을 눌러 삭제를 진행한다.  



   **6. 게시글 검색 화면**

   ![image](https://github.com/yashin20/the-board/assets/92693776/6b4607c1-202f-4615-9d1e-e5e6a9e430ea)  

   검색 키워드 : '9' 를 통해 검색한 결과이다. 
   제목(title) 에 '9'가 포함된 게시글 목록을 확인할 수 있다.

    
  </details>


  <details>
    <summary>회원</summary>

   **1. 회원가입 화면**

   ![image](https://github.com/yashin20/the-board/assets/92693776/ef165550-deb9-43bd-b1dd-3a51f6e5c183)  


   ![image](https://github.com/yashin20/the-board/assets/92693776/12ae4e8c-4a87-4a5d-b3e7-c3d82614d814)  

   ※ 회원가입 양식에 대한 경고 메시지 표시  



   **2. 로그인 화면**

   ![image](https://github.com/yashin20/the-board/assets/92693776/c2d9d390-e71a-4b5d-a04b-6d8b0ab1d19a)  

   ![image](https://github.com/yashin20/the-board/assets/92693776/4fbcce4a-2e85-475c-8f0d-76c59064a72b)  

   ※ 로그인 실패에 대한 경고 메시지 표시  


   **3. 회원정보 화면**  

   ![image](https://github.com/yashin20/the-board/assets/92693776/803b7248-3fab-4ebe-ab56-eb9cefae4774)  
  

   **4. 회원정보 수정 화면**

   ![image](https://github.com/yashin20/the-board/assets/92693776/fc09e9d0-0229-4521-8053-279d95f00273)  
   'Nickname' , 'Email' , 'Phone' 에 대해 회원정보 수정을 진행한다. 


   **4. 회원 탈퇴**
   ![image](https://github.com/yashin20/the-board/assets/92693776/4c0256eb-f88f-46fe-ba55-a21167f3486b)  
   회원 탈퇴 처리 후, 메인 페이지로 리다이렉션, 로그아웃 처리가 된다.    
   또한, 탈퇴한 회원이 작성한 게시글 / 댓글은 함께 삭제된다. 

    
  </details>


  <details>
    <summary>댓글</summary>

  **1. 댓글 작성 화면**
  
  로그인 X - 댓글 작성 화면  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/9cf7227a-1bc9-47d5-b173-bdebc02a7d64)  

  로그인 O - 댓글 작성 화면  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/97112208-bde3-474e-8283-f1195d8ceea1)  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/d771292d-46c4-461b-8c18-a5f929166c88)  


  **2. 댓글 수정**

  로그인 X  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/3f198fc2-6e9e-46f5-a6e1-898147562efe)  


  작성자 != 로그인 회원 - 로그인 O  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/a0ee73f7-912f-4561-acfe-ec125d0eb977)  

  작성자 == 로그인 회원 - 로그인 O  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/7e5fc904-1300-46c1-b3c0-ab732fb893b3)  
  작성자 본인이 로그인 한 상태에서만 댓글 수정 / 삭제 에 접근 가능하다.  

  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/4f4107bf-e01b-480f-832e-7149459ad179)  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/c2cf7c26-9a62-4183-9928-785486321a8a)  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/b08c429d-fa29-40c6-8a82-8647bd790f21)  

  댓글이 수정된 모습과 '작성일자' 뒤에 '(수정됨)' 표식이 생긴 것을 볼 수 있다.  



  **3. 댓글 삭제**

  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/c2c1e45a-1990-4a82-98d7-e4414d202543)  
  '댓글 2번!' 을 삭제 한다.    
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/2ddb34c3-fd21-467b-b7a1-a452fcbb9b61)  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/2dbb7acc-c455-48f4-bbd5-30a108e4941f)  
  '댓글 2번!' 이 삭제 된 모습을 볼 수 있다.

    
  </details>




## 2. 프로젝트 구조

### 2-1. 패키지 구조

<details>

<summary>패키지 구조 보기</summary>

```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂project
 ┃ ┃ ┃ ┃ ┗ 📂the_board
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetailsServiceAuthorities.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebSecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentLikesController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HomeController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜InitMember.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LikesController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PostController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChildCommentDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentRequestDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentResponseDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberRequestDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberResponseDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PostRequestDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PostResponseDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BaseEntity.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Comment.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentLikes.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Likes.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Member.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberRole.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Post.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DataAlreadyExistsException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DataNotFoundException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PasswordCheckFailedException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UnauthorizedAccessException.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentLikesRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LikesRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PostRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentLikesService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LikesService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PostService.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜TheBoardApplication.java
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┗ 📜bootstrap.min.css
 ┃ ┃ ┃ ┗ 📂img
 ┃ ┃ ┃ ┃ ┣ 📜empty_heart.png
 ┃ ┃ ┃ ┃ ┗ 📜full_heart.png
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┣ 📂comments
 ┃ ┃ ┃ ┃ ┣ 📜child-comment-list.html
 ┃ ┃ ┃ ┃ ┣ 📜comment-list.html
 ┃ ┃ ┃ ┃ ┗ 📜create-comment-form.html
 ┃ ┃ ┃ ┣ 📂fragments
 ┃ ┃ ┃ ┃ ┣ 📜footer.html
 ┃ ┃ ┃ ┃ ┣ 📜header.html
 ┃ ┃ ┃ ┃ ┗ 📜pagination.html
 ┃ ┃ ┃ ┣ 📂members
 ┃ ┃ ┃ ┃ ┣ 📜info-update.html
 ┃ ┃ ┃ ┃ ┣ 📜info.html
 ┃ ┃ ┃ ┃ ┣ 📜join.html
 ┃ ┃ ┃ ┃ ┣ 📜login.html
 ┃ ┃ ┃ ┃ ┗ 📜password-update.html
 ┃ ┃ ┃ ┣ 📂posts
 ┃ ┃ ┃ ┃ ┣ 📜create-post.html
 ┃ ┃ ┃ ┃ ┣ 📜member-post-list.html
 ┃ ┃ ┃ ┃ ┣ 📜post-info.html
 ┃ ┃ ┃ ┃ ┗ 📜update-post.html
 ┃ ┃ ┃ ┗ 📜index.html
 ┃ ┃ ┣ 📜application-oauth.yml
 ┃ ┃ ┗ 📜application.yml
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂project
 ┃ ┃ ┃ ┃ ┗ 📂the_board
 ┃ ┃ ┃ ┃ ┃ ┗ 📜TheBoardApplicationTests.java
```


</details>



### 2-2. DB 설계

![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/34bac54a-31d9-458a-83e3-33ca74f29413)

**MEMBER TABLE**   
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/688dd71e-b194-41d7-8366-5634e666f748)  

**POST TABLE**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/e56c4f17-526d-4c94-92b3-14cb63b0aee3)  

**COMMENT TABLE**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/422d6e53-7c6c-4d22-bc08-fd9febfa44a6)  



### 2-3. API 설계

**Post 관련 API**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/04184267-86fe-41fd-af99-a6772c85633a)  
  
**Member 관련 API**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/22b3ae7b-34c0-4746-86cc-bdf8feadf447)  
  
**Comment 관련 API**  
![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/bc8aa6de-89c6-4e65-97a5-374b5d8df839)  
  



## 개발 내용

 - <a href="https://notorious.tistory.com/352" target="_blank">[The Board] The Board 프로젝트 환경 설정 + 프로젝트 선정 이유</a>
 - <a href="https://notorious.tistory.com/354" target="_blank">[The Board] 게시글 댓글 좋아요 기능 구현하기</a>
 - <a href="https://notorious.tistory.com/355" target="_blank">[The Board] 댓글 / 대댓글 기능 구현 (생성, 수정 삭제)</a>


## 마무리

### 1. 프로젝트 보완사항




### 2. 프로젝트 과정에서 발생한 문제
- <a href="https://notorious.tistory.com/353" target="_blank">[The Board - MySQL Error] org.hibernate.tool.schema.spi.CommandAcceptanceException: Error executing DDL 해결방법</a>


### 3. 후기


