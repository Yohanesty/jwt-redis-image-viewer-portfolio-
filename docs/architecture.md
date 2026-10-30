# Architecture

```mermaid
flowchart TD
    C[Client] -->|POST /api/demo/session| SC[Demo Session Controller]
    SC --> TS[Viewer Token Service]
    SC --> RS[(Redis Viewer Session Store)]
    TS --> C

    C -->|Bearer JWT| I[Viewer Authorization Interceptor]
    I --> TS
    I --> AS[Viewer Access Service]
    AS --> RS

    I -->|authorized| LC[Image List Controller]
    I -->|authorized| VC[Image Stream Controller]

    LC --> AS
    VC --> AS
    VC --> SI[Synthetic Image Service]
```

## Responsibility

- `ViewerTokenService`: JWT issue / signature / issuer / audience / expiry validation
- `ViewerSessionStore`: Server-side viewer context abstraction
- `RedisViewerSessionStore`: Redis implementation
- `ViewerAccessService`: JWT + Redis context consistency and resource authorization
- `ViewerAuthorizationInterceptor`: Shared request authorization entry point
- `ImageController`: Image list / streaming HTTP API
