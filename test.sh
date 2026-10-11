#!/usr/bin/env bash
set -euo pipefail

BASE_URL="http://127.0.0.1:3000"

call() {
  local name="$1"
  shift
  echo
  echo "### ${name}"
  curl -i -s "$@"
  echo
}

call "1. POST /api/posts - 201" \
  -X POST "${BASE_URL}/api/posts" \
  -H "Content-Type: application/json" \
  -d '{"title":"첫 번째 게시글","content":"웹서비스설계 과제입니다."}'

call "2. POST /api/posts/1/comments - 201" \
  -X POST "${BASE_URL}/api/posts/1/comments" \
  -H "Content-Type: application/json" \
  -d '{"content":"첫 번째 댓글입니다."}'

call "3. GET /api/posts - 200" \
  "${BASE_URL}/api/posts"

call "4. GET /api/posts/1 - 200" \
  "${BASE_URL}/api/posts/1"

call "5. PUT /api/posts/1 - 200" \
  -X PUT "${BASE_URL}/api/posts/1" \
  -H "Content-Type: application/json" \
  -d '{"title":"수정된 게시글","content":"수정된 본문입니다."}'

call "6. PUT /api/posts/1/comments/1 - 200" \
  -X PUT "${BASE_URL}/api/posts/1/comments/1" \
  -H "Content-Type: application/json" \
  -d '{"content":"수정된 댓글입니다."}'

call "7. DELETE /api/posts/1/comments/1 - 200" \
  -X DELETE "${BASE_URL}/api/posts/1/comments/1"

call "8. DELETE /api/posts/1 - 200" \
  -X DELETE "${BASE_URL}/api/posts/1"

call "400 입력 검증 오류" \
  -X POST "${BASE_URL}/api/posts" \
  -H "Content-Type: application/json" \
  -d '{"title":"","content":"본문"}'

call "400 ID 형식 오류" \
  "${BASE_URL}/api/posts/abc"

call "404 게시글 없음" \
  "${BASE_URL}/api/posts/9999"

call "500 시뮬레이션" \
  "${BASE_URL}/api/posts" \
  -H "X-Simulate-Error: 500"

call "503 시뮬레이션" \
  "${BASE_URL}/api/posts" \
  -H "X-Simulate-Error: 503"
