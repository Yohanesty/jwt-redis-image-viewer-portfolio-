# Security Flow

## Request authorization

```mermaid
sequenceDiagram
    participant C as Client
    participant I as Interceptor
    participant J as JWT Service
    participant R as Redis
    participant A as Access Service
    participant API as Image API

    C->>I: Authorization: Bearer JWT
    I->>J: verify signature / iss / aud / exp
    J-->>I: sub + sid
    I->>R: GET viewer session by sid
    R-->>I: session
    I->>A: compare sub/session user
    A-->>I: access context
    I->>API: continue request
```

## Image authorization

For `/api/images/{imageId}/stream`, the application additionally checks whether the requested `imageId` exists in the Redis viewer session's allowed image set.

## Fail policy

This sample is Fail-closed.

- Invalid JWT -> 401
- Expired JWT -> 401
- Missing Redis session -> 401
- Subject/session mismatch -> 401
- Image not allowed -> 403
