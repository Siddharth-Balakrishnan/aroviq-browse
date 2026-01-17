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

