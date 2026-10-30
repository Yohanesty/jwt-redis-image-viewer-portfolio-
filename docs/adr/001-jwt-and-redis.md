# ADR-001: Use JWT and Redis for different responsibilities

## Context

A viewer request needs a verifiable client token, but the server may also need a short-lived context that can be revoked or changed without embedding every resource detail in the token.

## Decision

- JWT: signed identity/session reference
- Redis: short-lived viewer session and allowed resource IDs

## Rationale

JWT provides integrity and expiry without putting all server-side authorization state into the client token.

Redis provides:

- short-lived state
- explicit revocation
- small server-side authorization context
- shared access across multiple application instances

## Important

This is a portfolio Reference Design. It is not a statement that the original production service used the same claim/key/value structure.
