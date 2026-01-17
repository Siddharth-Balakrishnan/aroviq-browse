# AROVIQ BROWSE

> **A Developer Runtime Browser**  
> Local-first. Trust-aware. Execution with permission.

---

## What Is AROVIQ BROWSE?

AROVIQ BROWSE is **not** a consumer browser, IDE, or chatbot.

It is a **Developer Runtime Browser** — a local-first execution and reasoning environment that happens to render the web.

- The **browser** is the control surface
- The **local runtime daemon** is the source of truth
- AI **suggests before it acts**
- Every action is **guarded and auditable**

---

## Why This Exists

Modern developers work across:
- IDEs with agentic assistance
- Browser-based LLMs
- CI/CD dashboards
- APIs, logs, configs, and cloud consoles

Each tool holds isolated context and forgets decisions.

**AROVIQ BROWSE solves this by treating context as infrastructure.**

---

## Core Principles

- **Local-first** – your data stays on your machine
- **Append-only memory** – no silent mutation or loss
- **Suggest-first AI** – execution requires approval
- **Trust-aware reasoning** – AI knows when it might be wrong
- **Daemon-first architecture** – stability over cleverness

---

## Architecture (High Level)

CEF Browser (UI)

Tabs / Panels

Keyboard-first UX

Context capture

Local AI (lightweight)
|
| Local IPC
v
Local Runtime Daemon (Java)

Append-only memory

AI orchestration

Guard & policy engine

UCA-lite trust logic

Sandboxed execution

yaml
Copy code

The browser works **with or without** the daemon.

---

## Runtime Modes

### Browser-Only Mode
- No daemon
- Session memory
- Local AI assistance
- No execution

### Developer Runtime Mode
- Daemon enabled
- Persistent append-only memory
- Guarded execution
- Trust scoring
- Repo / API / error understanding

---

## What This Is NOT (v1)

- IDE (no LSP, debugging, autocomplete)
- Mobile browser
- Collaboration tool
- Cloud SaaS
- Plugin marketplace

These are intentional non-goals.

---

## Repository Structure

aroviq-browse/
├── browser/ # CEF Chromium browser
├── daemon/ # Java runtime (aroviqd)
├── context/ # AUTHORITATIVE project context
├── scripts/ # one-command DX
├── docs/ # architecture & research docs
├── AGENT_CONTEXT.md
└── README.md

yaml
Copy code

> The `/context` directory is the **single source of truth** for decisions, constraints, and project direction.

---

## Development Philosophy

- Solo-developer, systems-first
- 1–2 hours/day cadence
- Bi-weekly reviews
- Calm systems over impressive demos
- Correctness before polish

---

## Status

**Active development**  
The repository is currently private and under heavy iteration.

Public release planned after v1 demo milestone.

---

## License

This project is **source-available**.  
See [LICENSE](LICENSE.txt) for details.

---

## Final Note

> **Context is infrastructure, not conversation.**

If you’re interested in trustworthy AI systems, developer tooling, or runtime-level design — this project is for you.
