# sta
sts

##이미지를 게시판

# 🧐 배경
이미지 컨테츠를 사용한 앨범형 게시판을 만들고 싶다는 생각에 계획하게되었습니다.

# ⚙️ 프로젝트 개발 환경
1. 운영체제 : Windows
2. 통합개발환경(IDE) : Eclipse
3. JDK 버전 : JDK 17
4. 데이터 베이스 : MySQL
5. 빌드 툴 : Gradle
6. 관리 툴 : GitHub

# ⚒️ 프로젝트 기술 스택
- Frontend : HTML, CSS, JavaScript, AJAX, Thymeleaf, Lombok
- Backend :  Spring Security, Spring Data JPA, Spring Boot
- Database : MySQL, MyBatis
- OpenAPI : OAuth2 API, BootStrap

# 📜 기능 구현 계획
- 회원 (user)
      - 회원가입 / 회원정보수정 / 회원탈퇴 / 로그인 / 로그아웃 / 프로필 사진 업로드, 삭제
      - 회원아이디검색 / 회원추천
- 보드 (board)
      - 보드생성(이미지업로드 + 내용) / 보드 수정(내용수정) / 보드 삭제(이미지 + 내용)
      - 보드 검색(내용) / 마이페이지 / 회원글 미리보기
- 팔로우 (follow)
      - 팔로우 기능 / 팔로워 기능 / 친구 기능
- 좋아요 (likes)
      - 보드 좋아요 / 좋아요 카운트
- 댓글 (ripple)
      - 댓글 생성 / 댓글 삭제 / 댓글 수정
