# AROVIQ BROWSE — Repository Structure

This document defines the **authoritative repository structure**
for the AROVIQ BROWSE monorepo.

It exists to prevent structural drift, incorrect assumptions by agents,
and accidental architectural violations.

---

## Root Layout

aroviq-browse/
├── daemon/
├── browser/
├── scripts/
├── docs/
├── context/
└── README.md


---

## daemon/

Contains the **local runtime daemon** implemented in Java.

Responsibilities:
- Append-only system memory
- Request/response journaling
- Identity and capability APIs
- Guard enforcement
- Execution boundaries

The daemon is the **single source of truth**.

---

## browser/

Contains the **native client application**, implemented using CEF.

The browser is NOT a frontend-only UI.
It is a system client that:
- discovers the daemon
- negotiates capabilities
- captures runtime context
- presents AI suggestions
- gates execution with user approval

### browser/core/
OS-agnostic logic:
- daemon discovery
- routing
- state
- capability awareness

### browser/platform/
Minimal OS-specific glue:
- window creation
- event loop integration
- native lifecycle hooks

Supported platforms (v1):
- linux/
- macos/

### browser/resources/
Internal pages rendered by Chromium:
- aroviq://status
- aroviq://runtime
- aroviq://approvals (later)

### browser/build/
CEF configuration, build files, and tooling.

---

## context/

Authoritative system documents.

Files in this directory define:
- vision
- architecture
- constraints
- decisions
- current focus
- repository structure

If code or agent output conflicts with context files,
**context always wins**.

---

## docs/

Non-authoritative documentation:
- explanations
- guides
- external-facing docs

Docs must never redefine architecture.

---

## scripts/

Utility scripts for:
- development
- testing
- packaging
- distribution

Scripts must not encode business logic.

---

## Structural Rules

- No code may bypass the daemon.
- Browser holds no persistent truth.
- Context files define intent, not implementation.
- Structure changes require a decision entry.

This structure is intentionally conservative and explicit.