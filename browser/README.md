# AROVIQ BROWSE — Browser Component

## Role of the Browser

The browser is the **control surface** of AROVIQ BROWSE.

It is responsible for:
- Rendering web content (Chromium / CEF)
- Providing a keyboard-first user interface
- Capturing contextual signals from user activity
- Presenting AI suggestions and system state
- Acting as a client to the local runtime daemon

The browser is **NOT**:
- an IDE
- a system of record
- a long-term memory store
- an autonomous agent

---

## Core Responsibilities

- Chromium rendering via CEF
- Tab and panel management
- Keyboard-driven workflows
- Context capture:
  - repositories
  - APIs
  - errors
  - logs
- Local AI assistance (non-agentic)
- Daemon discovery and capability negotiation
- Capability-driven UI gating

---

## Runtime Modes

### Browser-Only Mode
- No daemon connected
- Session-scoped context only
- Local AI assistance
- No execution
- No persistent memory

### Developer Runtime Mode
- Local daemon connected
- Persistent memory available
- Guarded execution enabled
- Trust scores visible

The browser must **always function without the daemon**.

---

## Architectural Constraints

- Must not store persistent state
- Must not bypass the daemon
- Must not execute commands directly
- Must not infer capabilities without negotiation

---

## Implementation Notes (v1)

- CEF is used for Chromium embedding
- IPC is local-only (localhost or socket)
- UI is minimal and keyboard-first
- No plugin system in v1

---

## Status

Placeholder — implementation begins after daemon skeleton is stable.

Refer to `/context` for authoritative project decisions.
