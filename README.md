# 🔐 Spring Security 로그인 구현 학습

## 📚 커밋 내역별 정리

### 1️⃣ Security 설정 추가
**커밋:** `feat: Add SecurityConfig`
**날짜:** 2024-12-01

#### 🎯 목표
- Spring Security 기본 설정
- 로그인/로그아웃 경로 설정
- 비밀번호 암호화 설정

#### 📝 핵심 코드
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        // ...
    }
}
```

#### 🧠 핵심 개념
- **@EnableWebSecurity**: Spring Security 활성화
- **BCryptPasswordEncoder**: 비밀번호 암호화
- **permitAll() vs authenticated()**: 접근 권한 설정

#### ❓ 스스로 질문
- [ ] Spring Security가 하는 일을 설명할 수 있나?
- [ ] BCrypt는 왜 쓰는가?
- [ ] .loginProcessingUrl()은 뭐하는 건가?

#### 🔗 참고 링크
- [내가 정리한 SecurityConfig 설명](#)
- [공식 문서](https://docs.spring.io/spring-security/)

---

### 2️⃣ User 모델링
**커밋:** `feat: Add User entity and BaseEntity`
**날짜:** 2024-12-01

#### 🎯 목표
- User 엔티티 설계
- BaseEntity로 공통 필드 분리
- Repository 생성

#### 📝 핵심 코드
```java
@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;
}

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    // ...
}
```

#### 🧠 핵심 개념
- **@MappedSuperclass**: 테이블 생성 안 함, 필드만 상속
- **@CreatedDate**: JPA Auditing으로 자동 시간 기록
- **@Builder**: 빌더 패턴으로 객체 생성

#### ❓ 스스로 질문
- [ ] BaseEntity를 왜 분리했나?
- [ ] @Builder의 장점은?
- [ ] Role enum은 DB에 어떻게 저장되나?

---

### 3️⃣ 회원가입 기능 (GET)
**커밋:** `feat: Add signup form`
**날짜:** 2024-12-01

#### 🎯 목표
- 회원가입 폼 페이지 구현
- SignUpRequest DTO 생성
- 유효성 검증 어노테이션 추가

#### 📝 핵심 코드
```java
@GetMapping("/signup")
public String signupForm(Model model) {
    model.addAttribute("signUpRequest", new SignUpRequest());
    return "auth/signup";
}

@Getter @Setter
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
}
```

#### 🧠 핵심 개념
- **DTO (Data Transfer Object)**: 계층 간 데이터 전달
- **@NotBlank vs @NotNull**: 빈 문자열 검증 차이
- **@Size**: 길이 제한

#### ❓ 스스로 질문
- [ ] DTO를 왜 쓰나? Entity를 직접 쓰면 안 되나?
- [ ] @NotBlank는 언제 검증되나?
- [ ] Model.addAttribute()는 왜 하는 건가?

---

### 4️⃣ 회원가입 기능 (POST)
**커밋:** `feat: Implement user registration`
**날짜:** 2024-12-01

#### 🎯 목표
- 회원가입 처리 로직 구현
- UserService 생성
- 비밀번호 암호화

#### 📝 핵심 코드
```java
@PostMapping("/signup")
public String signup(@Valid @ModelAttribute SignUpRequest request,
                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "auth/signup";
    }
    userService.register(request);
    return "redirect:/auth/login";
}

@Transactional
public User register(SignUpRequest request) {
    User user = User.builder()
        .password(passwordEncoder.encode(request.getPassword()))
        .build();
    return userRepository.save(user);
}
```

#### 🧠 핵심 개념
- **@Valid**: 유효성 검증 실행
- **BindingResult**: 검증 결과 담는 객체
- **@Transactional**: 트랜잭션 관리
- **passwordEncoder.encode()**: 비밀번호 암호화

#### ❓ 스스로 질문
- [ ] @Valid가 없으면 어떻게 되나?
- [ ] BindingResult를 왜 쓰나?
- [ ] 비밀번호를 평문으로 저장하면?
- [ ] @Transactional을 Service에만 붙이는 이유?

---

### 5️⃣ 로그인 기능
**커밋:** `feat: Implement login with Spring Security`
**날짜:** 2024-12-01

#### 🎯 목표
- CustomUserDetails 구현
- CustomUserDetailsService 구현
- Spring Security와 연동

#### 📝 핵심 코드
```java
public class CustomUserDetails implements UserDetails {
    private final User user;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
}

public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }
}
```

#### 🧠 핵심 개념
- **UserDetails**: Spring Security가 이해하는 사용자 정보
- **UserDetailsService**: 사용자 로드하는 서비스
- **CustomUserDetails**: User → UserDetails 변환
- **loadUserByUsername()**: Spring Security가 자동 호출

#### ❓ 스스로 질문
- [ ] UserDetails가 왜 필요한가?
- [ ] loadUserByUsername()은 누가 호출하나?
- [ ] getAuthorities()는 뭐 반환하나?
- [ ] "ROLE_" 접두사는 왜 붙이나?

---

### 6️⃣ 중복 체크
**커밋:** `feat: Add username duplicate check`
**날짜:** 2024-12-01

#### 🎯 목표
- username 중복 검증
- bindingResult.rejectValue() 사용
- existsByUsername() 구현

#### 📝 핵심 코드
```java
// Repository
boolean existsByUsername(String username);

// Controller
if (userService.existsByUsername(request.getUsername())) {
    bindingResult.rejectValue("username", "duplicate", "중복된 아이디입니다.");
    return "auth/signup";
}
```

#### 🧠 핵심 개념
- **existsByUsername()**: COUNT(*) 쿼리 실행
- **bindingResult.rejectValue()**: 필드 에러 추가
- **검증 순서**: 기본 검증 → 중복 체크

#### ❓ 스스로 질문
- [ ] existsByUsername() vs findByUsername() 차이는?
- [ ] rejectValue() 파라미터 3개는 각각 뭔가?
- [ ] 중복 체크를 왜 Controller에서 하나?

---

## 🎯 학습 체크리스트

### SecurityConfig
- [ ] Spring Security 역할 설명 가능
- [ ] BCrypt 암호화 설명 가능
- [ ] 로그인 흐름 설명 가능

### User 모델링
- [ ] BaseEntity 분리 이유 설명 가능
- [ ] JPA Auditing 동작 설명 가능
- [ ] Builder 패턴 장점 설명 가능

### 회원가입
- [ ] DTO 사용 이유 설명 가능
- [ ] 유효성 검증 시점 설명 가능
- [ ] 트랜잭션 역할 설명 가능

### 로그인
- [ ] UserDetails 역할 설명 가능
- [ ] 로그인 처리 흐름 설명 가능
- [ ] 권한 체계 설명 가능

### 중복 체크
- [ ] exists vs find 차이 설명 가능
- [ ] 검증 순서 이유 설명 가능

---

## 🔧 실습 과제

### 레벨 1: 따라하기
- [ ] 전체 코드 처음부터 다시 작성
- [ ] 주석 달면서 이해하기

### 레벨 2: 변형하기
- [ ] 닉네임 필드 추가
- [ ] 이메일 중복 체크 추가
- [ ] 전화번호 필드 추가

### 레벨 3: 확장하기
- [ ] 비밀번호 확인 필드 추가
- [ ] 프로필 이미지 업로드
- [ ] 회원 정보 수정 기능

---

## 📖 참고 자료

### 공식 문서
- [Spring Security 공식 문서](https://docs.spring.io/spring-security/)
- [Spring Data JPA 공식 문서](https://docs.spring.io/spring-data/jpa/)

### 내가 정리한 문서
- [SecurityConfig 상세 설명](./docs/security-config.md)
- [로그인 처리 흐름도](./docs/login-flow.md)
- [에러 처리 방법](./docs/error-handling.md)
```

---

### 방법 2: docs 폴더에 파일별로 정리
```
프로젝트/
├── src/
├── docs/
│   ├── 01-security-config.md
│   ├── 02-user-modeling.md
│   ├── 03-signup-get.md
│   ├── 04-signup-post.md
│   ├── 05-login.md
│   └── 06-duplicate-check.md
└── README.md
