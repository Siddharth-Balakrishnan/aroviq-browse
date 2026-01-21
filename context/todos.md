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

### Phase 1 — Daemon

Completed:
- Control-plane daemon
- Identity and capability endpoints
- File-based discovery
- Request/response journaling
- Static informational root page

Next:
- Implement append-only system memory (write-only, v1)

Note:
Append-only memory design is locked and reviewed.
Implementation must strictly follow documented constraints.
