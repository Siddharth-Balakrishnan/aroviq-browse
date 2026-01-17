# AROVIQ BROWSE — Architecture

## High-Level Architecture

AROVIQ BROWSE follows a **daemon-first, capability-negotiated architecture**.

CEF Browser (UI)

Tabs / Panels

Keyboard-first UX

Context capture

Local AI (lightweight)
|
| Local IPC (HTTP / socket)
v
Local Runtime Daemon (Java)

Append-only memory

AI orchestration

Guard & policy engine

UCA-lite trust logic

Sandboxed execution

yaml
Copy code

---

## Architectural Principles

1. Daemon is the **single source of truth**
2. Browser holds **no critical state**
3. Memory is **append-only**
4. AI suggests before it acts
5. Every action is auditable
6. Capabilities are discovered, never assumed

---

## Runtime Modes

### Browser-Only Mode
- No daemon
- Session memory
- Local AI assistance
- No execution
- No trust verification

### Developer Runtime Mode
- Daemon connected
- Persistent append-only memory
- Guarded execution
- Trust scoring (UCA-lite)
- Repo / API / error understanding

---

## Browser Responsibilities

- Chromium rendering (CEF)
- UI & panels
- Keyboard-driven workflows
- Context capture:
  - repositories
  - APIs
  - errors
  - logs
- Local inference (non-agentic)
- Daemon discovery
- Capability-gated UI

---

## Daemon Responsibilities

- Append-only memory
- Memory querying
- AI orchestration
- Guard enforcement
- Trust evaluation
- Sandboxed execution
- Audit logging

Daemon must NEVER:
- render UI
- auto-start silently
- execute without approval

---

## Technology Choices (v1)

- Browser: CEF
- Daemon: Java 21 (Spring Boot)
- AI:
  - Local 2–3B model
  - Optional cloud models via user keys
- OS support: Linux + macOS

---

## Explicit Non-Goals (v1)

- IDE features (LSP, debugging, autocomplete)
- Mobile support
- Collaboration
- Cloud storage of user data
- Plugin marketplace

## UCA-Lite (Unified Cognitive Architecture – v1 Scope)

AROVIQ BROWSE implements a **UCA-lite** model inspired by:
https://github.com/ShyamSathish005/uca-architect

UCA-lite is NOT a full cognitive architecture.
It is a trust and verification scaffold.

In v1, UCA-lite consists of:
1. Context completeness checks
2. Confidence estimation (heuristic)
3. Risk classification (read, write, execute)
4. Verification routing (user confirmation paths)

UCA-lite governs:
- how AI suggestions are presented
- when Guard escalation occurs
- when execution requires stronger confirmation

UCA-lite does NOT:
- plan autonomously
- spawn agents
- self-execute actions


## Daemon Discovery Mechanism

The local runtime daemon advertises its presence via a file-based handshake.

On startup, the daemon writes:
~/.aroviq/daemon.json

This file contains:
- PID
- HTTP port
- WebSocket port
- version
- start timestamp

The browser:
- checks for this file
- validates daemon health via /health
- negotiates capabilities via /capabilities

This approach avoids fixed ports, mDNS, and implicit assumptions.

