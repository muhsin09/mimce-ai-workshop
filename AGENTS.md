# AGENTS.md

## Spec Approval Gate (Mandatory)

Before any implementation step, the agent MUST present the generated spec `.md` file to the user and obtain explicit approval.

Rules:
- Do NOT start implementation before user approval.
- If the spec changes later, request approval again.
- Approval must be explicit (e.g., "approved", "onaylandı", "ok proceed").
- In plan/read-only mode, only analysis and planning are allowed; no file edits or system changes.

Primary agent guidance for this repository is defined in `.opencode/AGENTS.md`.

All coding agents must read and follow `.opencode/AGENTS.md` before doing any work.

If any instruction in this file conflicts with `.opencode/AGENTS.md`, the rules in `.opencode/AGENTS.md` take precedence.
