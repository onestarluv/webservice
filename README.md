# 웹서비스설계 게시판 API 과제

Java 17, Spring Boot 3.x, Gradle Kotlin DSL 기반 게시판 API입니다. 기본 경로는 `/api/posts`이며, 데이터는 과제 실행과 검증이 간단하도록 인메모리 저장소에 보관합니다.

## 실행 방법

```bash
./gradlew bootRun
```

Windows PowerShell에서는 다음 명령을 사용합니다.

```powershell
.\gradlew.bat bootRun
```

서버는 `http://127.0.0.1:3000` 에서 실행됩니다.

## 응답 포맷

성공 응답은 모든 API가 같은 구조를 사용합니다.

```json
{
  "status": "success",
  "data": {}
}
```

오류 응답도 모든 오류가 같은 구조를 사용합니다.

```json
{
  "status": "error",
  "statusCode": 404,
  "message": "게시글을 찾을 수 없습니다."
}
```

## API 명세

| 메소드 | 경로 | 요청 예시 | 응답 예시 | 상태 코드 |
| --- | --- | --- | --- | --- |
| POST | `/api/posts` | `{"title":"첫 번째 게시글","content":"본문"}` | `{"status":"success","data":{"id":1,"title":"첫 번째 게시글","content":"본문","comments":[]}}` | `201 Created` |
| POST | `/api/posts/{postId}/comments` | `{"content":"댓글"}` | `{"status":"success","data":{"id":1,"content":"댓글"}}` | `201 Created`, `404 Not Found` |
| GET | `/api/posts?keyword=검색어` | 없음 | `{"status":"success","data":[{"id":1,"title":"제목","content":"본문","commentCount":1}]}` | `200 OK` |
| GET | `/api/posts/{postId}` | 없음 | `{"status":"success","data":{"id":1,"title":"제목","content":"본문","comments":[]}}` | `200 OK`, `404 Not Found` |
| PUT | `/api/posts/{postId}` | `{"title":"수정","content":"수정 본문"}` | `{"status":"success","data":{"id":1,"title":"수정","content":"수정 본문","comments":[]}}` | `200 OK`, `400 Bad Request`, `404 Not Found` |
| PUT | `/api/posts/{postId}/comments/{commentId}` | `{"content":"수정 댓글"}` | `{"status":"success","data":{"id":1,"content":"수정 댓글"}}` | `200 OK`, `400 Bad Request`, `404 Not Found` |
| DELETE | `/api/posts/{postId}` | 없음 | `{"status":"success"}` | `200 OK`, `404 Not Found` |
| DELETE | `/api/posts/{postId}/comments/{commentId}` | 없음 | `{"status":"success"}` | `200 OK`, `404 Not Found` |

## 미들웨어 설명

| 구성 요소 | 역할 |
| --- | --- |
| `RequestLoggingFilter` | 모든 요청마다 requestId를 생성해 MDC와 응답 헤더 `X-Request-Id`에 저장하고, `[시각] METHOD URI -> status (처리시간 ms)` 형식으로 콘솔 로그를 남깁니다. |
| `SimulationInterceptor` | 요청 헤더 `X-Simulate-Error: 500`이면 `RuntimeException`, `X-Simulate-Error: 503`이면 `ServiceUnavailableException`을 던져 5xx 응답을 시연합니다. |
| `GlobalExceptionHandler` | 커스텀 예외, 검증 오류, JSON 파싱 오류, ID 형식 오류, 그 외 예외를 공통 오류 응답으로 변환합니다. 500 계열 상세 내용은 서버 로그에만 남깁니다. |

## 상태 코드 발생 조건

| 상태 코드 | 발생 API | 발생 조건 |
| --- | --- | --- |
| `200 OK` | `GET /api/posts`, `GET /api/posts/{postId}`, `PUT`, `DELETE` | 조회, 수정, 삭제 성공 |
| `201 Created` | `POST /api/posts`, `POST /api/posts/{postId}/comments` | 게시글 또는 댓글 생성 성공, `Location` 헤더 포함 |
| `400 Bad Request` | `POST`, `PUT`, 경로 ID가 있는 모든 API | `title`/`content`가 비어 있거나, ID가 숫자가 아니거나 양수가 아닌 경우, JSON 파싱 실패 |
| `404 Not Found` | 단건 조회, 댓글 생성/수정/삭제, 게시글 삭제 | 대상 게시글 또는 댓글이 없는 경우 |
| `500 Internal Server Error` | `/api/posts` 이하 모든 API | `X-Simulate-Error: 500` 헤더로 재현 |
| `503 Service Unavailable` | `/api/posts` 이하 모든 API | `X-Simulate-Error: 503` 헤더로 재현 |

## curl 검증

`test.sh`는 8개 API와 `400`, `404`, `500`, `503` 케이스를 모두 호출합니다.

```bash
bash test.sh
```

IDE HTTP Client를 사용한다면 `requests.http`를 실행하면 됩니다.

## 테스트

```bash
./gradlew test
```

MockMvc 테스트는 `201`, `400`, `404`, `503` 케이스를 포함합니다.
