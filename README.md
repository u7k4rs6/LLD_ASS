
---

# SOLID Practice

This repository contains **10 structured refactoring exercises** designed for SST students who have completed foundational OOP and are now strengthening their understanding of **SOLID principles** through practical, behavior-preserving improvements.

Each exercise provides:

* Fully working Java code (compiles and runs)
* Deliberately flawed design that strongly violates one SOLID principle
* Clear constraints and measurable acceptance criteria
* A defined refactoring objective

The focus is on improving structure without altering observable behavior.

---

## Objective

The goal of these exercises is to develop the ability to:

* Detect design smells in working systems
* Refactor incrementally without breaking behavior
* Improve modularity using core OOP constructs
* Apply SOLID principles through disciplined restructuring

Important:

Design patterns are intentionally **not introduced in instructions**.
Solutions should rely only on:

* Classes
* Interfaces
* Composition
* Polymorphism
* Encapsulation
* Clear separation of responsibilities

---

## Recommended Approach

For each exercise:

1. Read the `README.md` inside the exercise folder.
2. Compile and run the program.
3. Observe and record the console output.
4. Identify concrete design flaws.
5. Refactor gradually in small steps.
6. Re-run after each change to ensure behavior remains unchanged.

The refactor is considered successful only if output is preserved exactly.

---

## SOLID Reference Summary

### S — Single Responsibility Principle

A class should have one primary reason to change.

### O — Open/Closed Principle

New behavior should be introduced by extension, not by modifying large conditional blocks.

### L — Liskov Substitution Principle

Subtypes must remain safely substitutable for their base type without breaking expectations.

### I — Interface Segregation Principle

Clients should not depend on methods they do not use.

### D — Dependency Inversion Principle

High-level modules should depend on abstractions rather than concrete implementations.

---

## Exercise Mapping

| Exercise | Principle Focus |
| -------- | --------------- |
| Ex1–Ex2  | SRP             |
| Ex3–Ex4  | OCP             |
| Ex5–Ex6  | LSP             |
| Ex7–Ex8  | ISP             |
| Ex9–Ex10 | DIP             |

Each pair intensifies the violation of the target principle.

---

## Running an Exercise

Navigate to the desired exercise directory. Example:

```bash
cd SOLID/Ex1/src
javac *.java
java Main
```

### Environment Requirements

* Java 17
* Default package (no `package` declarations)
* No Maven or Gradle
* No external libraries

---

## Expected Outcome

After completing all exercises, you should be able to:

* Recognize structural design flaws in working code
* Refactor large conditional chains safely
* Separate responsibilities cleanly
* Replace inheritance misuse with correct abstraction
* Reduce coupling through dependency inversion
* Write modular, extensible OOP code

These exercises are intentionally minimal in tooling and framework usage so that architectural thinking remains the primary focus.

---

This repository is designed for disciplined, incremental refactoring practice with strict behavioral preservation.
