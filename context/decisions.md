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

---

## D-012 — Browser Evolution Strategy (Option 1 → Option 3 → Option 2)

Decision:
Adopt a staged browser evolution strategy:
Option 1 (Minimal CEF Shell) → Option 3 (Runtime Console) → Option 2 (Full Browser).

### Context

AROVIQ BROWSE is not intended to be a consumer browser first.
It is a developer runtime with strong emphasis on:
- trust boundaries
- local-first execution
- explicit user approval
- clear daemon authority

During Phase 2 planning, three browser approaches were evaluated:

Option 1: Minimal native window with embedded Chromium (CEF), no tabs or address bar.  
Option 2: Traditional browser shell with tabs, navigation, and address bar.  
Option 3: Single-purpose runtime console UI focused on system state and approvals.

Each option has different trade-offs in complexity, correctness, and risk.

### Options Evaluated

#### Option 1 — Minimal CEF Shell

Description:
A native application window embedding Chromium purely as a rendering engine.
No browsing features are exposed.

Pros:
- Lowest implementation complexity
- Clean separation between browser and daemon
- Minimal OS-specific code
- Fast iteration and high correctness
- Easy to discard or evolve

Cons:
- Does not feel like a browser
- No real web context initially
- Limited demo appeal

#### Option 3 — Runtime Console

Description:
A system control surface implemented using Chromium-rendered internal pages.
Focuses on daemon status, runtime context, approvals, and diagnostics.

Pros:
- Strong alignment with AROVIQ’s philosophy
- Clear identity as a system tool
- Low risk of feature creep
- Natural place for trust and approval workflows

Cons:
- Still not a general-purpose browser
- Requires deliberate expansion to reach full browsing

#### Option 2 — Full Browser

Description:
A traditional browser UI with tabs, address bar, navigation, and external websites.

Pros:
- Familiar to users
- Immediate access to rich web context
- Strong positioning as a browser

Cons:
- Highest complexity
- High risk of scope creep
- Risk of becoming “another AI browser”
- Browser concerns can dominate system design prematurely

### Decision Details

The project will evolve deliberately through all three options:

1. Start with Option 1 to validate:
   - daemon discovery
   - lifecycle integration
   - IPC correctness
   - platform stability

2. Evolve into Option 3 to:
   - establish AROVIQ as a runtime control surface
   - introduce structured context capture
   - design approval and trust workflows

3. Only later evolve into Option 2 to:
   - expose controlled web browsing
   - treat web pages as context sources, not authorities

At each stage, only UI surface area expands.
Daemon contracts, memory design, and trust boundaries remain unchanged.

### Why This Matters

This staged approach:
- avoids premature browser complexity
- preserves daemon-first architecture
- prevents early AI-browser coupling
- enables safe growth without rewrites

### Testing & Validation

- Option 1 validated by:
  - daemon discovery
  - status rendering
  - stable startup/shutdown on all platforms

- Option 3 validated by:
  - runtime context visibility
  - approval workflows
  - absence of unintended execution

- Option 2 validated by:
  - correct isolation between web content and daemon
  - explicit user-mediated actions
  - preserved trust boundaries

Date: 2026-01-21

---

## D-013 — Platform Support Strategy (Linux-first, macOS-validated)

Decision:
Target Linux and macOS for v1, with Linux as the primary development platform
and macOS as a compatibility validation platform.

Windows support is explicitly deferred.

### Context

The AROVIQ BROWSE architecture consists of:
- a local daemon (Java-based)
- a native browser client (CEF-based)

Both components interact closely with the operating system:
- process lifecycle
- filesystem semantics
- windowing systems
- packaging and distribution

The project is developed primarily by a single author with:
- daily access to Linux
- periodic access to macOS
- limited access to Windows

### Options Evaluated

#### Linux-only

Pros:
- Simplest development environment
- Fastest iteration

Cons:
- Limits early portability
- Delays macOS validation

#### Linux + macOS

Pros:
- Covers the two most common developer OS platforms
- Similar Unix-like semantics
- Compatible filesystem and process models
- CEF supports both reliably

Cons:
- Requires periodic macOS testing
- Requires thin OS-specific glue code

#### Windows-first or Windows-inclusive

Pros:
- Large potential user base

Cons:
- Significantly different process and windowing model
- Higher maintenance burden
- Increased packaging and testing complexity
- Not aligned with current development constraints

### Decision Details

The project will:
- Use Linux as the canonical development environment
- Ensure all core browser logic is OS-agnostic
- Isolate OS-specific behavior in minimal platform modules
- Validate macOS compatibility at defined checkpoints

Windows support is intentionally deferred until:
- browser architecture stabilizes
- trust and memory models are proven
- packaging strategy matures

### Testing & Validation Strategy

Linux:
- Daily development and testing
- Treated as authoritative behavior

macOS:
- Periodic validation testing
- Focused on:
  - application launch
  - window creation
  - rendering correctness
  - daemon discovery

Windows:
- No testing in v1
- No assumptions made in code
- Platform abstraction designed to allow future support

### Why This Matters

This strategy:
- maximizes development velocity
- avoids premature platform debt
- ensures correctness on Unix-like systems
- keeps future expansion possible without rewrite

Date: 2026-01-21

---

## D-014 — Explicit Browser vs Daemon Repository Separation

Decision:
Rename and formalize the client-side codebase as `browser/`
instead of a generic `ui/` directory.

### Context

As AROVIQ BROWSE enters Phase 2 (Browser),
it became clear that the client is not a simple UI layer.

The browser:
- embeds Chromium (CEF)
- manages OS-level windows
- performs daemon discovery
- captures runtime system context
- acts as the execution control surface

Calling this layer `ui/` incorrectly implies:
- frontend-only logic
- web-app semantics
- lack of system responsibility

### Decision Details

The repository structure is updated to:

- `daemon/` — local runtime (source of truth)
- `browser/` — native client (CEF-based)
- `context/` — authoritative system documents

The browser directory is further structured into:
- core (OS-agnostic)
- platform (Linux/macOS glue)
- resources (internal pages)
- build (CEF tooling)

### Why This Matters

This separation:
- reinforces daemon-first architecture
- prevents logic leakage across boundaries
- enables correct mental models for agents
- supports future native packaging (.deb, .app, .exe)
- avoids long-term structural debt

### Impact

- One-time rename from `ui/` to `browser/`
- No behavioral changes
- Structure is now locked for Phase 2+

Date: 2026-01-21