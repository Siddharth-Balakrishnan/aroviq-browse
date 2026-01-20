# AROVIQ BROWSE — TODOs & Current Focus

## Current Focus
Phase: Week 1–2  
Theme: Local Runtime Foundation

---

## Immediate TODOs
- Initialize monorepo
- Create Java daemon project
- Implement /health endpoint
- Implement /identity endpoint
- Implement /capabilities endpoint
- Implement append-only memory core
- Verify crash safety
- Wire browser daemon discovery

---

## Near-Term TODOs
- Browser → daemon context ingestion
- Memory query API
- Local AI integration
- Guard integration
- UCA-lite trust scoring
- Sandboxed dry-run execution

---

## Deferred (Post v1)
- Multi-window support
- Enterprise policies
- Cloud inference
- Collaboration

---

## Explicitly Rejected (v1)
- IDE features
- Mobile support
- Plugin marketplace
- Background auto-start services

### Phase 1 — Daemon (Status)

Completed:
- Spring Boot daemon initialization
- Health and discovery endpoints
- Identity endpoint
- Capabilities endpoint
- File-based daemon discovery (daemon.json)
- Static root informational page
- Request/response journaling (per-instance)

Remaining:
- Append-only memory core (write-only)

Phase 1 Note:
The daemon control plane, observability, and diagnostics are complete.
Remaining work is limited to system memory implementation.
