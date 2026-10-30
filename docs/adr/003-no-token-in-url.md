# ADR-003: Do not put JWT in the URL

## Decision

Use:

```http
Authorization: Bearer <JWT>
```

instead of:

```text
/viewer?token=<JWT>
```

## Rationale

URLs can appear in:

- Browser history
- Reverse proxy logs
- Access logs
- Referrer headers
- Monitoring traces

The sample therefore uses a request header.

## Trade-off

Direct `<img src>` requests cannot easily attach custom Authorization headers. Real products may use a backend proxy, Fetch/Blob flow, short-lived one-time ticket, cookie-based authorization, or another controlled design.

This repository intentionally does not claim which method was used in the original system.
