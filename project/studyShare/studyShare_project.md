## 주요 소스코드

## 환경 설정
- **JDK 버전** : 17 이상
- **MySQL 버전** : 9.0.1
- **MariaDB 버전** : 10.11.1

## 주요 의존성 및 버전
**Spring**
- **Spring Boot** : 3.3.6
- **QueryDSL** : 5.1.0
- **commons-io** : 2.11.0
- **JUnit** : 5.10.2
- **MyBatis** : 3.0.3

**Node.js**
- **Node.js** : v23.3.0
- **NPM** : 10.9.0
- **Express** : 4.21.2
- **nodemailer** : 6.9.16

**개발 도구**
- **artillery** : 2.0.21 (성능 테스트)

### 회원 관리 시스템 (Node.js MSA)

#### 1. 인증 미들웨어 설정 (auth.middleware.js)
JWT 토큰을 검증하고 사용자 인증을 처리하는 미들웨어입니다.
```javascript
exports.verifyToken = async (req, res, next) => {
    try {
        const token = req.headers.authorization?.split(' ')[1];
        if (!token) {
            return res.status(401).json({
                success: false,
                message: errorMessage.INVALID_TOKEN
            });
        }
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        const isValidToken = await TokenModel.verifyToken(decoded.userId, decoded.jti);
        if (!isValidToken) {
            return res.status(401).json({
                success: false,
                message: errorMessage.INVALID_TOKEN
            });
        }
        req.user = decoded;
        next();
    } catch (error) {
        res.status(401).json({
            success: false,
            message: errorMessage.INVALID_TOKEN
        });
    }
};
```
#### 2. 회원가입/로그인 서비스 (auth.service.js)
회원 인증 관련 핵심 비즈니스 로직을 처리합니다.
```javascript
async login(userId, password, rememberMe) {
    const user = await UserModel.findById(userId);
    if (!user) {
        throw new Error(errorMessage.INVALID_CREDENTIALS);
    }
    if (user.status === 1) throw new Error(errorMessage.ACCOUNT_DORMANT);
    if (user.status === 2) throw new Error(errorMessage.ACCOUNT_RESTRICTED);
    if (user.status === 3) throw new Error(errorMessage.EMAIL_NOT_VERIFIED);
    if (user.status === 4) throw new Error(errorMessage.ACCOUNT_LOCKED);

    const hashedPassword = await this.hashPassword(password, user.salt);
    if (hashedPassword !== user.password) {
        throw new Error(errorMessage.INVALID_CREDENTIALS);
    }
    
    const jti = crypto.randomBytes(32).toString('hex');
    const token = jwt.sign(
        { userId: user.userId, jti },
        process.env.JWT_SECRET,
        { expiresIn: rememberMe ? '7d' : '1d' }
    );

    return { token, user: {...} };
}
```
#### 3. 이메일 인증 서비스 (email.service.js)
회원가입 및 비밀번호 재설정을 위한 이메일 인증 시스템입니다.
```javascript
async sendVerificationEmail(userId, email) {
    const token = this.generateVerificationToken();
    const verificationLink = `https://api.gyeongminiya.asia/api/user/verify-email?token=${token}&userId=${userId}`;
    
    const mailOptions = {
        from: process.env.EMAIL_USER,
        to: email,
        subject: '이메일 인증을 완료해주세요.',
        html: `
            <div style="text-align: center;">
                <h2>이메일 인증</h2>
                <p>아래 버튼을 클릭하여 이메일 인증을 완료해주세요.</p>
                <a href="${verificationLink}" style="background-color: #4CAF50;">
                    이메일 인증하기
                </a>
            </div>
        `
    };

    try {
        await transporter.sendMail(mailOptions);
        await EmailCodeModel.create(userId, token);
        return true;
    } catch (error) {
        throw new Error(errorMessage.EMAIL_ERROR);
    }
}
```

자체 구축한 SMTP 서버와 Gmail을 함께 사용하여 안정적인 이메일 전송을 보장합니다.
```javascript
constructor() {
    // 커스텀 도메인 트랜스포터
    this.customTransporter = nodemailer.createTransport({
        host: process.env.EMAIL_HOST,
        port: process.env.EMAIL_PORT,
        secure: false,
        auth: {
            user: process.env.EMAIL_USER,
            pass: process.env.EMAIL_PASSWORD
        },
        tls: {
            rejectUnauthorized: false
        }
    });

    // Gmail 트랜스포터
    this.gmailTransporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: process.env.GMAIL_USER,
            pass: process.env.GMAIL_PASSWORD
        }
    });
}

// 도메인에 따른 트랜스포터 선택
getTransporter(email) {
    const koreanDomains = ['naver.com', 'daum.net', 'kakao.com', 'hanmail.net'];
    const domain = email.split('@')[1].toLowerCase();
    return koreanDomains.includes(domain) ? this.gmailTransporter : this.customTransporter;
}
```
#### 4. 토큰 관리 시스템 (token.model.js)
JWT 토큰의 저장 및 검증을 담당하는 모델입니다.
```javascript
class TokenModel {
    async saveToken(userId, jti, expiresAt) {
        const query = `
            INSERT INTO ActiveTokens (userId, jti, expiresAt)
            VALUES (?, ?, ?)
        `;
        const [result] = await db.query(query, [userId, jti, expiresAt]);
        return result.insertId;
    }

    async verifyToken(userId, jti) {
        const query = `
            SELECT * FROM ActiveTokens 
            WHERE userId = ? AND jti = ? AND expiresAt > CURRENT_TIMESTAMP
        `;
        const [rows] = await db.query(query, [userId, jti]);
        return rows[0];
    }

    async removeExpiredTokens() {
        const query = 'DELETE FROM ActiveTokens WHERE expiresAt <= CURRENT_TIMESTAMP';
        await db.query(query);
    }
}
```
#### 5. JWT 인증 필터 (AuthenticationFilter.java) - Node.js 인증 서버와 연동
```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtUtil.resolveToken(request);
            if (token != null && tokenService.isTokenValid(token)) {
                String userId = jwtUtil.getUserId(token);
                request.setAttribute("userId", userId);
            }
        } catch (Exception e) {
            log.error("JWT 토큰 검증 실패: ", e);
        }
        filterChain.doFilter(request, response);
    }
}
```
이 필터는 Node.js 인증 서버에서 발급한 JWT 토큰을 검증하고, 유효한 경우 요청에 사용자 ID를 추가합니다. 이를 통해:
1. Node.js 서버에서 발급한 토큰을 Spring Boot 서버에서도 검증 가능
2. MSA 환경에서 일관된 인증 체계 유지
3. 서로 다른 언어로 작성된 서비스 간의 원활한 통신 보장

#### 주요 기능
- 회원가입 및 로그인
    - MSA 구조로 구현
    - 로그인 세션은 JWT 토큰을 사용하여 관리
    - 토큰 검증은 데이터베이스에 저장된 토큰 정보를 확인하여 수행
- 이메일 인증
    - 자체 구축한 SMTP 서버(info@gyeongminiya.asia)를 통해 대부분의 이메일 전송을 처리
    - 한국 주요 메일 서비스(네이버, 다음 등)의 스팸 정책으로 인한 전송 실패를 방지하기 위해 Gmail SMTP도 보조적으로 활용
    - 도메인별로 최적의 전송 경로를 자동 선택하여 전달률 극대화
    - Postfix와 Dovecot을 활용한 자체 메일 서버 구축으로 비용 절감 및 완전한 제어 가능
- 비밀번호 재설정
- 토큰 관리
    - 토큰 검증 및 만료 관리
    - 토큰 저장 및 검증 로직 구현
    - 토큰 만료 처리 및 데이터베이스 정리 (SchedulingConfig.java)


#### Node.js 속도 측정결과
 1. 부하 테스트 결과 요약
    1차 테스트 (5,000 요청)
    - 처리량: 1,934 req/sec
    - 평균 응답: 24.6ms
    - 성공률: 100%
    2차 테스트 (100,000 요청)
    - 처리량: 2,118 req/sec
    - 평균 응답: 258.4ms
    - 성공률: 100%
    
2. 쿼리 성능비교

| 쿼리 유형 | 처리량 (req/sec) | 평균 응답 시간 |
|----------|-----------------|--------------|
| 단일 조회 | 1,816 | 33.2ms |
| 다중 조회 | 1,690 | 28.3ms |
| JOIN | 1,754 | 21.2ms |

3. 핵심 성과
    1. 안정성
        - 모든 테스트에서 100% 성공률 달성
        - 부하 증가에도 안정적 처리
    2. 성능
        - 초당 2,000건 이상의 요청 처리 가능
        - JOIN 쿼리도 평균 21.2ms의 빠른 응답
    3. 확장성
        - 부하 증가 시에도 처리량 증가
        - 시스템 안정성 유지

### 트러블 슈팅
#### 문제상황
- 코드상 문제는 없는데 속도가 저하되는 현상이 발생
- Node.js서버의 속도 측정 결과도 안정적이었음
- Spring Boot 서버가 문제라고 판단

#### 해결 방안
- 자주 조회되는 테이블에 인덱싱 추가
    - ActiveTokens 테이블에 인덱싱 추가 (해당 테이블은 토큰의 유효성을 검사하는 테이블이라 자주 조회되었음)
- Nginx를 앞단에 두어 http/3 프로토콜을 사용하도록 설정
    - 내부적으로는 http/1.1 프로토콜을 사용하지만 클라이언트와 서버는 http/3 프로토콜을 사용하도록 설정
- 정적 요소를 필터에서 제외
- 정적 요소 압축

#### 개선 효과
- 속도 저하 문제 해결및 http/3 프로토콜 사용으로 속도 향상

### 시스템 아키텍처
![system-architecture](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/studyShare/studyShare_architecturediagram.png)
- 클라이언트 - Nginx: HTTPS 및 HTTP/3 프로토콜 사용
- Nginx - 백엔드 서버: HTTP 1.1/2 프로토콜로 내부 통신
    - api.gyeongminiya.asia 도메인을 사용하는 Node.js 인증 서버와 연동
    - www.gyeongminiya.asia 도메인을 사용하는 Spring Boot 서버와 연동

### Class Diagram & ERD
![Class Diagram](https://github.com/ChunjaeStudyShare/ChunjaeStudyShare/blob/main/note/%EC%BD%94%EB%94%A9%EC%9E%AC%ED%8C%90%EC%86%8C_classDiagram.png)

![ERD](https://github.com/ChunjaeStudyShare/ChunjaeStudyShare/blob/main/note/%EC%BD%94%EB%94%A9%EC%9E%AC%ED%8C%90%EC%86%8C_ERD.png)
