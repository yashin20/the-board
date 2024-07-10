# SpringBoot-Project-'the-board'



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

   '조회수' 기준으로 내림차순 정렬  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/18250746-ccbb-4911-bcf1-f39d151f0f83)  

   ※ 로그인을 하지 않아도 게시글 정렬이 가능하다.  



   **2. 게시글 등록 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/67099714-c576-4029-8b68-552aa2a8ef5e)  

   로그인한 사용자만 게시글 작성이 가능하며, 작성 후 '게시' 버튼을 누르면 메인 페이지로 리다이렉트 된다.  

   

   **3. 게시글 상세 정보**

   로그인 X  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/92b77621-66c2-4a46-83e7-e13552424ae3)  

   ※ 로그인 하지 않은 경우, 게시글 상세 정보에 접근 가능하지만, '게시글 설정' 옵션에 접근할 수 없다.  


   작성자 계정이 아닌 다른 계정으로 로그인 O  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/bef81a23-7918-4817-a5b6-b0dc5515f885)  

   ※ 작성자 계정이 아닌 다른 계정으로 로그인한 경우, '게시글 설정' 옵션에 접근 가능하지만, '게시글 작성' 기능만 접근 가능하다.  


   작성자 계정으로 로그인 O  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/15f21e93-5271-447d-b708-f7aa6e0feff0)  

   ※ 작성자 계정으로 로그인 한 경우, '게시글 수정' 과 '게시글 삭제' 를 할 수 있다.  



   **4. 게시글 수정 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/2ecc1243-4c45-4f2f-9b42-4af5f6e7e914)  

   게시글 수정 후, '게시' 버튼을 눌러 수정을 마무리한다.    
   '게시' 버튼을 누르면 게시글 목록으로 이동한다.  

   [수정된 게시글 화면]  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/c828dff6-270e-4b36-a597-85969a196c0c)  
   
   

   **5. 게시글 삭제 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/816ef173-4759-4456-b8b5-1ad39da2f7bb)  

   '게시글 삭제' 버튼을 눌러 삭제를 진행한다.  

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/08081d6f-d02c-4c06-9a42-d1c9d963d53d)  

   '게시글 번호'를 포함한 삭제 완료 안내 메시지가 등장한다.

   [게시글 목록]  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/440f7a24-f7f1-4178-b6da-94319eda0f34)  

   게시글이 삭제 된 것을 볼 수 있다.


   **6. 게시글 검색 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/6b0fdefb-da6a-4dc7-9220-14c441fbb801)  



  **6-1. 게시글 검색 후 페이징 화면**

  ['by' 키워드로 검색한 화면]  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/bbd81c39-1be6-45da-a29c-a9431b2a07ca)  

  ['by' 키워드로 검색 내용 中 사용자 기준 4페이지]  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/c56913a8-1e71-4927-a828-27cf637fc195)  



  **6-2. 게시글 검색 후 페이징 + 정렬**

  ['by' 키워드로 검색 내용 && 조회수 기준 내림차순 정렬]  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/79fef79f-8ff0-4c39-99ac-2677d557079b)  


  ['by' 키워드로 검색 내용 && 조회수 기준 내림차순 정렬 中 사용자 기준 1페이지]  
  ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/d346d15f-fe23-401e-a321-e79a19fa6537)   
    
  </details>


  <details>
    <summary>회원</summary>

   **1. 회원가입 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/fe32e1bc-cfeb-4c2b-9bc2-7ac7a2af3fea)  


   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/f7f03b03-c0d4-4526-a495-2cdcb6e0ff46)  

   ※ 회원가입 양식에 대한 경고 메시지 표시  



   **2. 로그인 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/70aeecdb-783d-4469-b23a-b1b814896db5)  

   ※ 로그인 실패에 대한 경고 메시지 표시  



   **3. 회원정보 수정 화면**

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/49fd5a13-5ff2-4ee5-96b1-8fef64824af3)  
   로그인 된 username 을 입력하여, 회원정보 화면으로 이동  

   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/1ffe8588-1a0d-4b49-8bb8-15a53608835e)  

  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/db057e23-35ee-4f4b-afcb-333b7dcc2956)  
   변경하려는 닉네임에 대한 중복 체크    
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/8e879869-e2e9-4503-8ff2-80213486f736)  
   변경하려는 비밀번호에 대한 유효성 체크    



   **4. 회원 탈퇴**
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/86e4f872-27d0-4d94-a470-5e5ad86565dd)  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/109b6351-9b3c-4f57-a1eb-26bc5373da76)  
   ![image](https://github.com/yashin20/BoardServiceV2/assets/92693776/e0a6d5a1-82f8-41b2-bbde-ae1eeaeb9f09)  
   회원 탈퇴 처리 후, 메인 페이지로 리다이렉션, 로그아웃 처리가 된다.    
   또한, 탈퇴한 회원이 작성한 게시글 / 댓글의 작성자는 'unknown' 으로 표기된다.  

    
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


