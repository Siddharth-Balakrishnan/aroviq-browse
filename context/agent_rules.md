# AROVIQ BROWSE — Agent Rules

Applies to:
- Anti-Gravity
- IDE agents
- Web LLMs
- Code generators
- AI builders

---

## Source of Truth

Authoritative files:
- context/vision.md
- context/architecture.md
- context/constraints.md
- context/decisions.md

If a request conflicts with these, the agent MUST point it out.

---

## Agents MAY
- Explain concepts
- Suggest implementations within constraints
- Generate code respecting architecture
- Ask clarifying questions

---

## Agents MUST NOT
- Introduce IDE features
- Add cloud dependencies
- Bypass the daemon
- Propose deleting memory
- Execute commands
- Assume collaboration or mobile support

---

## Behavior Rules
- Suggest, do not act
- Surface uncertainty
- Prefer safety over cleverness

---

## Final Rule

If an idea makes the system more impressive 
but less trustworthy, it must be rejected.
