# AROVIQ BROWSE — Local Runtime Daemon (aroviqd)

## Role of the Daemon

The local runtime daemon (`aroviqd`) is the **source of truth** for AROVIQ BROWSE.

It is responsible for:
- Persistent append-only memory
- AI orchestration
- Guard enforcement
- Trust evaluation (UCA-lite)
- Sandboxed execution
- Audit logging

All critical reasoning and execution flows pass through the daemon.

---

## Core Responsibilities

- Maintain append-only memory
- Expose memory query APIs
- Orchestrate AI inference
- Enforce guard policies
- Compute trust scores
- Execute commands in a sandbox
- Log all actions immutably

---

## What the Daemon Must NEVER Do

- Render UI
- Auto-start without user consent
- Execute commands without approval
- Modify or delete historical memory
- Assume browser capabilities

---

## Architecture Notes

- Implemented in Java 21
- Spring Boot used for API scaffolding
- Local-only IPC (no remote access)
- Strong typing for policies and memory events
- AI inference delegated to local or external runtimes

---

## Lifecycle

- Started explicitly by the user or scripts
- Discovered by the browser
- Advertises capabilities
- Shut down cleanly with full state persistence

---

## Status

Placeholder — initial skeleton includes:
- `/health`
- `/identity`
- `/capabilities`
- append-only memory core

Refer to `/context` for authoritative design decisions.
