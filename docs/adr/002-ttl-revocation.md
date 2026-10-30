# ADR-002: Align token expiry and Redis TTL

## Decision

The demo issues both JWT and Redis viewer session with the same short TTL.

Sample TTL: 300 seconds.

## Why

It reduces cases where:

- JWT is valid but server session already expired
- Redis session survives much longer than the client token

Redis deletion can still revoke a session before JWT expiry.

## Trade-off

Very short TTL improves exposure control but increases session renewal frequency. The correct production value depends on UX, security policy, and workload.

The 300-second value is synthetic.
