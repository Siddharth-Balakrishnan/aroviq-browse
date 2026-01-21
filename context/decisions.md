# AROVIQ BROWSE — Decisions Log

APPEND-ONLY FILE
Do NOT edit or rewrite past decisions.

---

## D-001 — Monorepo Architecture
Decision:
Use a single monorepo for browser, daemon, and context.

Why:
- Faster iteration during v1
- Easier refactors
- Unified context spine

Date: 2026-01-06

---

## D-002 — Developer Runtime Focus (v1)
Decision:
v1 focuses on developer runtime, not general AI OS.

Why:
- Clear scope
- Real developer value
- Avoids hype-driven feature creep

Date: 2026-01-06

---

## D-003 — Local Daemon Runtime
Decision:
Introduce a local daemon as the system source of truth.

Why:
- Stability
- Auditability
- Security boundaries
- Enables append-only memory

Date: 2026-01-06

---

## D-004 — Append-Only Memory
Decision:
Memory cannot be deleted; only appended or annotated.

Why:
- Trust
- Reproducibility
- Git-like reasoning history
- UCA alignment

Date: 2026-01-06

---

## D-005 — Java Control Plane
Decision:
Use Java 21 for daemon APIs and orchestration.

Why:
- Strong typing
- Stability
- Enterprise viability
- AI inference delegated elsewhere

Date: 2026-01-06

---

## D-006 — Browser Must Run Without Daemon
Decision:
Daemon is optional; browser must work standalone.

Why:
- Lower trust barrier
- Explicit opt-in for background services
- Broader adoption

Date: 2026-01-06

---

## D-007 — IPC Protocol Selection

Decision:
Use REST (HTTP over localhost) for control and query APIs,
and WebSocket (over localhost) for streaming and event notifications.

Why:
- REST is simple, debuggable, and inspectable
- WebSocket supports streaming AI output and async events
- Avoids premature complexity (gRPC)
- Clear separation of request/response vs event flows

Date: 2026-01-17

---

## D-008 — Execution Sandbox Strategy (v1)

Decision:
Use process-level isolation for v1 execution sandboxing.

Details:
- Commands run as non-privileged user
- Dedicated runtime working directory
- Read-only filesystem by default
- Explicit command allowlist
- No network access initially
- Full audit logging

Why:
- Honest security model for v1
- Avoids Docker and daemon dependencies
- Keeps UX simple for solo developers
- Guard + approval + audit mitigate risk

Date: 2026-01-17

---

## D-009 — Non-Standard Package Naming

Decision:
Retain non-standard Java package naming for daemon modules,
including mixed-case and descriptive grouping such as
com.aroviq.aroviqd.testingAndHealth.

Why:
- Improves semantic clarity for the author
- Reflects logical grouping over convention
- Avoids premature refactors that do not improve correctness

Impact:
- Agents and contributors must not refactor packages
  without explicit instruction.

Date: 2026-01-21

---

## D-010 — Per-Instance Request/Response Journaling

Decision:
Implement deterministic, per-instance request/response journaling
for the AROVIQ daemon using a dedicated HTTP filter.

### Context

During Phase 1 development, it became clear that traditional application
logging was insufficient for debugging a local control-plane daemon.

Requirements included:
- Full visibility into HTTP interactions
- Clear separation between operational logs and system memory
- Ability to inspect daemon behavior per run
- Zero impact on daemon authority or behavior

### Decision Details

A dedicated request/response journaling subsystem was introduced with the
following properties:

- Implemented as a Servlet Filter at the HTTP I/O boundary
- Wraps HttpServletRequest and HttpServletResponse
- Captures:
  - HTTP method and path
  - Headers
  - Request body
  - Response body
  - Status code
  - Timing and duration
- Uses structured NDJSON (one event per line)
- Explicit truncation for large payloads (10,000 KB limit)
- One journal file per daemon instance
- Journal lifecycle bound to daemon lifecycle

### Storage Model

- Journals are written to:
  ~/.aroviq/logs/
- File naming includes:
  - startup timestamp
  - daemon PID
- Journals are append-only and immutable per run

### Explicit Non-Goals

The journaling system:
- Is not exposed via API
- Is not queryable in v1
- Is not part of append-only memory
- Does not influence runtime decisions
- Does not replace metrics or tracing systems

### Rationale

This design:
- Preserves daemon determinism
- Improves debuggability without adding complexity
- Avoids polluting business logic
- Aligns with AROVIQ’s “context as infrastructure” philosophy

Alternative approaches (controller logging, AOP, logging frameworks)
were rejected due to poor separation of concerns or lack of determinism.

### Impact

- Improves trust and debuggability
- Enables future tooling (log viewers, replay, diffing)
- Establishes a clear diagnostic boundary early in the project

Date: 2026-01-20

---

## D-011 — Append-Only Memory as Structured Truth Ledger

Decision:
Implement append-only memory as a structured, immutable ledger
of system understanding rather than free-form text or vector storage.

### Context

Agentic IDEs and AI tools do not share model memory.
They reconstruct context per invocation from controlled inputs.

To support cross-model, cross-tool, and cross-website continuity,
AROVIQ requires a memory system that is:
- durable
- auditable
- model-agnostic
- re-hydratable

### Decision Details

Append-only memory entries are:
- structured JSON objects
- schema-versioned
- semantically categorized
- appended sequentially

Memory storage uses NDJSON to preserve append-only semantics
and enable future segmentation.

Vectors and embeddings are explicitly excluded from the source
of truth and may only exist as derived, regenerable indexes.

### Promotion Rules

Only high-confidence, user-approved, or system-verified
information may be promoted into append-only memory.

Raw conversations, logs, and transient data are excluded.

### Rationale

This approach:
- preserves trust and inspectability
- avoids model lock-in
- enables selective retrieval
- reduces long-term token usage indirectly
- supports cross-environment context reuse

### Impact

- Establishes a durable cognitive ledger
- Enables consistent AI behavior across tools
- Prevents memory corruption through over-ingestion

Date: 2026-01-21