# Security & Confidentiality Policy

이 저장소는 외부 공개용 Clean-room Portfolio입니다.

## 절대 포함하지 않는 자료

- 회사/고객사 원본 Java/JSP/JavaScript/SQL 소스
- 실제 JWT Secret, 인증서, Key, Token
- 실제 JWT Claim 구성
- 실제 Redis Key / Value / TTL
- 실제 사용자 ID, 고객정보, 거래정보, 이미지 Key
- 실제 URL, IP, Port, DNS, Context Path
- 실제 DB Table / Column / DDL / Query
- 내부 로그, 장애보고서, 설계서, 화면 캡처
- 사내 Gitea Workflow 또는 운영 Script 원본
- 실제 이미지/전자문서 파일

## 공개 가능한 자료

- 처음부터 새로 작성한 Sample Code
- Synthetic Data
- 일반화해서 다시 그린 Architecture
- 기술적 판단을 설명하는 ADR
- 실제 회사 구현과 동일하다고 주장하지 않는 Reference Design

## Secret Handling

`PORTFOLIO_JWT_SECRET`은 환경변수로만 받습니다.

예:

```bash
export PORTFOLIO_JWT_SECRET='replace-with-a-long-random-demo-secret'
```

Secret을 Git에 Commit하지 않습니다.
