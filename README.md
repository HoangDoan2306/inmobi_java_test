# Number Guessing Game — Java Spring Boot Backend

Backend cho game đoán số, xây dựng bằng **Java 21 + Spring Boot 4.1**, kết hợp với Frontend **Nuxt 4**.

## 1. Công nghệ

### Backend

- Java 21
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA / Hibernate
- Spring Security
- JWT (JJWT 0.12.3)
- H2 Database
- Spring Cache
- Gradle
- JUnit 5 / Spring Boot Test

### Frontend

- Nuxt 4.5.2
- Vue 3.5.41
- Vue Router 5.2.0
- Tailwind CSS 4.3.3
- Node.js 22 LTS
- npm 10+

---

## 2. Chạy ứng dụng

Project có 2 cách sử dụng.

### Option 1 — Sử dụng phiên bản đã deploy

Project đã được deploy tại:

FE: https://inmobi-java-test.vercel.app/

BE: https://inmobi-java-test.onrender.com/

Có thể trực tiếp:

- Đăng ký tài khoản
- Đăng nhập
- Chơi game đoán số
- Mua thêm lượt chơi
- Xem leaderboard

### Option 2 — Chạy toàn bộ project local

Kiến trúc local:

```text
Frontend (Nuxt)
      ↓
Backend (Spring Boot)
      ↓
H2 Database
```

### 2.1. Chạy Backend

**Yêu cầu:**

- JDK 21
- Git

Di chuyển vào thư mục Backend:

```bash
cd be
```

Build project.

**Windows:**

```bash
.\gradlew.bat clean build
```

**Linux/macOS:**

```bash
./gradlew clean build
```

Chạy Backend.

**Windows:**

```bash
.\gradlew.bat bootRun
```

**Linux/macOS:**

```bash
./gradlew bootRun
```

Backend mặc định chạy tại:

```text
http://localhost:8080
```

### 2.2. Chạy Frontend

**Yêu cầu:**

- Node.js 22 LTS
- npm 10+

Kiểm tra version:

```bash
node -v
npm -v
```

Di chuyển vào thư mục Frontend:

```bash
cd fe
```

Cài dependencies:

```bash
npm install
```

Chạy development server:

```bash
npm run dev
```

Frontend mặc định chạy tại:

```text
http://localhost:3000
```

Local environment:

```text
Frontend: http://localhost:3000
Backend:  http://localhost:8080
```

> Đảm bảo Backend đã chạy trước khi sử dụng đầy đủ các chức năng trên Frontend.

### 2.3. H2 Console

Sau khi Backend chạy, H2 Console có thể truy cập tại:

```text
http://localhost:8080/h2-console
```

Thông tin kết nối:

```text
JDBC URL: jdbc:h2:file:./data/javatest
Username: sa
Password: password
```

---

## 3. Cấu hình môi trường

Các biến môi trường chính của Backend:

| Biến | Mặc định | Mục đích |
|---|---|---|
| `JWT_SECRET` | Dev default | Secret dùng để ký JWT |
| `PORT` | `8080` | Port Backend |
| `FRONTEND_URL` | `http://localhost:3000` | CORS origin |

Ví dụ:

```text
JWT_SECRET=your-secret-key
PORT=8080
FRONTEND_URL=http://localhost:3000
```

> Khi triển khai thực tế cần sử dụng `JWT_SECRET` đủ mạnh và không commit secret thật vào source code.

---

## 4. Authentication

Hệ thống sử dụng **Spring Security + JWT**.

### Đăng ký

```http
POST /api/auth/register
Content-Type: application/json
```

Request:

```json
{
  "username": "player1",
  "password": "secret123"
}
```

### Đăng nhập

```http
POST /api/auth/login
Content-Type: application/json
```

Request:

```json
{
  "username": "player1",
  "password": "secret123"
}
```

Response trả về JWT:

```json
{
  "token": "<JWT_TOKEN>",
  "scrId": "SCR-..."
}
```

Sử dụng token cho các API cần authentication:

```http
Authorization: Bearer <JWT_TOKEN>
```

JWT có thời hạn mặc định **24 giờ**.

### Authentication Flow

```text
Register / Login
       ↓
Validate credentials
       ↓
Generate JWT
       ↓
Client lưu JWT
       ↓
Authorization: Bearer <JWT>
       ↓
JwtAuthenticationFilter
       ↓
Validate JWT
       ↓
Spring SecurityContext
       ↓
Authenticated API
```

---

## 5. API

### Authentication

| Method | Endpoint | Auth | Mô tả |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Đăng ký tài khoản |
| POST | `/api/auth/login` | Public | Đăng nhập và lấy JWT |

### User

| Method | Endpoint | Auth | Mô tả |
|---|---|---|---|
| GET | `/api/user/me` | JWT | Lấy user hiện tại |
| GET | `/api/user/{scrId}` | JWT | Lấy user theo `scrId` |

### Game

| Method | Endpoint | Auth | Mô tả |
|---|---|---|---|
| POST | `/api/game/guess` | JWT | Đoán số từ 1 đến 5 |
| POST | `/api/game/buy-turns` | JWT | Mua thêm 5 lượt chơi |
| GET | `/api/game/leaderboard` | JWT | Lấy top 10 người chơi |

---

## 6. Test nhanh API

Có thể truy cập project đã deloy để test trên giao diện: https://inmobi-java-test.vercel.app/

Backend mặc định chạy tại:

```text
http://localhost:8080
```

> Các ví dụ dưới đây dùng **PowerShell trên Windows**, phù hợp với môi trường development của project.

### 1. Register

```powershell
$body = @{ username = "testuser1"; password = "secret123" } | ConvertTo-Json -Compress; $response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/register" -Method POST -ContentType "application/json" -Body $body; $response
```

Response sẽ chứa JWT và `scrId`:

```text
 token
 -----
 eyJhbGciOiJIUzI1NiJ9...
```

### 2. Login

```powershell
$body = @{ username = "testuser"; password = "secret123" } | ConvertTo-Json -Compress
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -ContentType "application/json" -Body $body
$token = $response.token
$token
```

Lưu JWT vào biến `$token` để sử dụng cho các API yêu cầu authentication.

### 3. Lấy thông tin user hiện tại

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/user/me" -Method GET -Headers @{ Authorization = "Bearer $token" }
```

### 4. Mua thêm lượt chơi

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/game/buy-turns" -Method POST -Headers @{ Authorization = "Bearer $token" }
```

### 5. Đoán số

```powershell
$body = @{ guess = 3 } | ConvertTo-Json -Compress
Invoke-RestMethod -Uri "http://localhost:8080/api/game/guess" -Method POST -ContentType "application/json" -Headers @{ Authorization = "Bearer $token" } -Body $body
```

### 6. Xem leaderboard

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/game/leaderboard" -Method GET -Headers @{ Authorization = "Bearer $token" }
```

> Có thể dùng `curl` trên Linux/macOS. Với Windows PowerShell, sử dụng `Invoke-RestMethod` như trên để tránh lỗi xử lý quote của JSON khi truyền qua `curl.exe`.

---

## 7. Bảo mật

Hệ thống sử dụng các cơ chế bảo mật chính:

- **Spring Security** bảo vệ các API yêu cầu authentication.
- **JWT** được sử dụng cho authentication stateless.
- **BCrypt** được sử dụng để hash password.
- `JwtAuthenticationFilter` kiểm tra JWT trên mỗi request.
- Bean Validation kiểm tra dữ liệu đầu vào.
- Có xử lý riêng cho lỗi `401 Unauthorized` và `403 Forbidden`.
- **Pessimistic locking** được sử dụng khi cập nhật user trong các thao tác game để tránh race condition khi có concurrent requests.
- Không sử dụng server-side session cho authentication.
- H2 Console phục vụ development/testing và không nên expose trong production.

---

## 8. Tối ưu hiệu năng

### Database Index

Leaderboard sử dụng query:

```sql
ORDER BY score DESC, scrId ASC
LIMIT 10
```

`User` có:

- **Index đơn trên `scrId`**: tối ưu việc tìm user theo `scrId`.
- **Composite index trên `score DESC, scrId ASC`**: tối ưu truy vấn leaderboard.

### Cache Leaderboard

Leaderboard sử dụng Spring Cache:

```text
Cache name: leaderboard
Cache key: top10
```

Luồng đọc:

```text
GET /api/game/leaderboard
        ↓
    @Cacheable
        ↓
Cache HIT  → trả dữ liệu cache
Cache MISS → query DB → lưu vào cache
```

Khi cache hit, request leaderboard không cần query database.

### Scheduled Cache Refresh

Leaderboard được refresh định kỳ mỗi **60 giây**:

```text
Mỗi 60 giây
      ↓
Query DB trực tiếp
      ↓
@CachePut
      ↓
Cập nhật leaderboard cache
```

Scheduler gọi trực tiếp method `loadLeaderboardFromDb()` thay vì gọi lại method `@Cacheable`, đảm bảo cache được refresh bằng dữ liệu mới và không bị lấy lại giá trị cũ từ cache.

### User Profile Cache

`user_profile` được `@CacheEvict` khi score hoặc turns của user thay đổi, giúp request tiếp theo lấy dữ liệu mới.

### Định hướng production

Hiện tại project sử dụng **Spring Cache + `ConcurrentMapCacheManager`**, phù hợp cho bài test/demo hoặc một backend instance.

Nếu triển khai thực tế với nhiều backend instances, có thể chuyển sang **Redis Distributed Cache**:

```text
Backend A ─┐
Backend B ─┼──> Redis
Backend C ─┘
```

Redis giúp các backend instances dùng chung cache và phù hợp hơn cho horizontal scaling. Đây là định hướng kiến trúc production, không phải dependency đang được sử dụng trong project hiện tại.

---

## 9. Database

Project sử dụng **H2 file-based database**:

```text
jdbc:h2:file:./data/javatest
```

Hibernate sử dụng:

```properties
spring.jpa.hibernate.ddl-auto=update
```

H2 phù hợp cho development/test. Với production nên sử dụng database như PostgreSQL/MySQL và migration tool như Flyway hoặc Liquibase.

---

## 10. Build & Test

### Build

**Windows:**

```bash
.\gradlew.bat clean build
```

**Linux/macOS:**

```bash
./gradlew clean build
```

### Chạy test

**Windows:**

```bash
.\gradlew.bat test
```

**Linux/macOS:**

```bash
./gradlew test
```

Các test chính bao gồm:

- Authentication flow
- Game logic
- Concurrent requests
- Pessimistic locking
- Security / JWT

---

## 11. Cấu trúc Backend

```text
src/main/java/com/inmobivn/javatest/
├── controller/     # REST API
├── service/        # Business logic
├── repository/     # JPA repository
├── entity/         # Database entity
├── dto/            # Request/Response DTO
├── security/       # JWT + Spring Security
├── scheduler/      # Leaderboard cache refresh
└── exception/      # Global exception handling
```

---

## 12. Frontend

Frontend sử dụng:

- Nuxt 4.5.2
- Vue 3.5.41
- Vue Router 5.2.0
- Tailwind CSS 4.3.3
- Node.js 22 LTS
- npm 10+

Các script chính:

```bash
npm install
npm run dev
npm run build
npm run preview
```

Frontend local:

```text
http://localhost:3000
```

---

## 13. Tổng quan kiến trúc

```text
                 Frontend
                Nuxt / Vue
                     │
                     │ REST API + JWT
                     ▼
             Spring Boot Backend
                     │
          ┌──────────┼──────────┐
          │          │          │
     Controller    Service   Security
          │          │          │
          │          │      JWT Filter
          │          │
          ▼          ▼
               Repository
                    │
                    ▼
               H2 Database

Leaderboard:

API → @Cacheable → Cache
                     ↑
                     │
                  Scheduler
                    60 sec
                     │
                     ▼
                    DB
```

---

## 14. Tổng kết

Project tập trung vào các yêu cầu chính của bài test:

- RESTful API rõ ràng.
- Frontend có giao diện game.
- JWT Authentication và Spring Security.
- BCrypt password hashing.
- Input validation và global exception handling.
- Pessimistic locking cho concurrent requests.
- Database index đơn và composite index.
- Cache cho leaderboard.
- Scheduled cache refresh.
- Cache eviction cho user profile khi dữ liệu user thay đổi.
- Unit/Integration testing.
- Có thể chạy local hoặc sử dụng phiên bản đã deploy.
- Có định hướng chuyển sang Redis khi triển khai production với nhiều backend instances.
