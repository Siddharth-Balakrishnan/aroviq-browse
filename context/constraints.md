# AROVIQ BROWSE — Constraints

## Architectural Constraints
- Daemon is the source of truth
- Browser holds no persistent state
- Memory is append-only
- No hidden state

---

## Safety Constraints
- AI cannot execute without approval
- Default execution is dry-run
- Guard runs on every suggestion
- All actions are logged

---

## Product Constraints
- No IDE features
- No mobile support
- No collaboration
- No cloud storage of user data

---

## Operational Constraints
- Local-first
- Linux + macOS only
- Solo developer
- 1–2 hours/day development pace

---

## UX Constraints
- Keyboard-first
- One golden path
- Minimal configuration
- No settings explosion

## UI Customization Constraint

User customization is limited to the browser UI layer.

Customization may include:
- layout changes
- panels
- keybindings
- visualizations

Customization must NOT:
- alter daemon behavior
- bypass Guard or UCA
- execute commands
- modify memory rules

All customization is client-side and capability-gated.

