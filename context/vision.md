# AROVIQ BROWSE — Vision

## Project Identity

AROVIQ BROWSE is a **Developer Runtime Browser**.

It is NOT:
- a consumer web browser
- an IDE
- a chatbot
- a cloud SaaS
- a CRUD dashboard

It IS:
- a local-first system runtime
- an execution & reasoning surface
- a trust-aware AI environment
- a browser that understands systems

The browser is the **control surface**.  
The local runtime (daemon) is the **source of truth**.

---

## Core Problem

Developers today operate across:
- Agentic IDEs (Anti-Gravity, VS Code, IntelliJ agents)
- Browser-based LLMs (ChatGPT, Claude, Grok)
- AI builders (Lovable, Firebase Studio)
- API tools, logs, CI/CD dashboards, cloud consoles

Each tool:
- holds isolated context
- forgets architectural decisions
- re-infers constraints repeatedly
- cannot safely coordinate actions
- leaves no audit trail

This results in:
- context drift
- unsafe AI actions
- repeated explanations
- loss of trust
- non-reproducible decisions

---

## Core Insight

**Context is infrastructure, not conversation.**

AI systems must:
- consume explicit, structured context
- respect immutable decisions
- understand constraints
- know when confidence is low
- act only with permission
- record actions immutably

---

## Vision Statement

> Build a browser that understands systems, reasons with trust,  
> and acts only with permission.

---

## v1 Philosophy

- Local-first
- Privacy by default
- Append-only memory
- Suggest-first, execute-last
- Explicit over implicit
- Trust over convenience

v1 optimizes for **credibility**, not popularity.

---

## Target User (v1)

Primary:
- Indie founders doing dev + ops
- Backend / infra engineers
- Solo builders running production systems

Secondary (later):
- Platform teams
- Enterprises needing auditability
- AI safety / systems researchers

---

## Long-Term Direction

AROVIQ BROWSE will evolve into:
- a trusted AI-assisted system runtime
- a bridge between IDEs, browsers, and agents
- a reference architecture for verifiable AI tooling

v1 intentionally avoids scale and feature breadth.

Trust in AROVIQ BROWSE is reinforced through explicit observability.

The system is designed so that developers can inspect, audit,
and reason about daemon behavior without hidden state or implicit actions.

Per-instance request/response journaling is a concrete example of this
philosophy: behavior is visible, deterministic, and attributable
to a specific runtime instance.


