

---

# Ex3 – OCP Refactor

## Placement Eligibility Rules Engine

---

## Overview

This exercise refactors a placement eligibility evaluation system to comply with the **Open/Closed Principle (OCP)**.

The objective is to design the system so that:

* New eligibility rules can be added without modifying core evaluation logic
* Existing behavior and report output remain unchanged
* No large conditional chains exist

Constraints:

* `StudentProfile` fields must remain unchanged
* Order of reasons in output must be preserved
* No external libraries

---

## Existing System Behavior

The current application:

1. Evaluates a `StudentProfile`
2. Applies multiple eligibility checks:

   * CGR threshold
   * Attendance percentage
   * Earned credits
   * Disciplinary flag
3. Returns:

   * `ELIGIBLE` or `NOT_ELIGIBLE`
   * A list of reasons (if not eligible)
4. Prints a formatted report
5. Saves evaluation data to a fake store

All of this logic is implemented inside `EligibilityEngine.evaluate`.

---

## Design Issues

`EligibilityEngine.evaluate` currently:

* Uses a long `if/else` chain
* Hard-codes rule thresholds
* Mixes evaluation with formatting
* Handles persistence/logging
* Scatters rule logic in one place

### Why This Is a Problem

* Violates Open/Closed Principle
* Adding a new rule requires editing the same method
* High regression risk
* Hard to test rules independently
* Poor separation of concerns
* Difficult to extend

This results in a rigid and fragile rules engine.

---

## Refactored Architecture

After applying OCP:

```id="b6a2vn"
Main
 └── EligibilityEngine
      ├── EligibilityRule (interface)
      │     ├── CgrRule
      │     ├── AttendanceRule
      │     ├── CreditsRule
      │     └── DisciplinaryRule
      ├── EligibilityReportFormatter
      └── EligibilityStore (interface)
            └── FakeEligibilityStore
```

The engine becomes extensible without modification.

---

## Core Abstraction

### EligibilityRule Interface

```java
interface EligibilityRule {
    String evaluate(StudentProfile profile);
}
```

* Returns `null` if rule passes
* Returns a reason string if rule fails

Each rule is now a small, independent unit.

---

## Example Rule Implementations

### Attendance Rule

```java
class AttendanceRule implements EligibilityRule {
    public String evaluate(StudentProfile profile) {
        if (profile.attendance < 75) {
            return "attendance below 75";
        }
        return null;
    }
}
```

---

### CGR Rule

```java
class CgrRule implements EligibilityRule {
    public String evaluate(StudentProfile profile) {
        if (profile.cgr < 7.5) {
            return "CGR below required threshold";
        }
        return null;
    }
}
```

Each rule is:

* Single-purpose
* Self-contained
* Independently testable

---

## Refactored Evaluation Flow

`EligibilityEngine` now orchestrates rule execution:

```java
public EligibilityResult evaluate(StudentProfile profile) {

    List<String> reasons = new ArrayList<>();

    for (EligibilityRule rule : rules) {
        String result = rule.evaluate(profile);
        if (result != null) {
            reasons.add(result);
        }
    }

    boolean eligible = reasons.isEmpty();

    return new EligibilityResult(eligible, reasons);
}
```

### What Changed

* No conditional chains
* No hard-coded rule logic
* Rules stored in a list
* New rules can be added without modifying evaluation loop

The engine is now **closed for modification but open for extension**.

---

## Formatting Responsibility

Formatting is moved to a dedicated component:

```java
class EligibilityReportFormatter {
    String format(StudentProfile profile, EligibilityResult result);
}
```

The engine no longer handles printing.

---

## Persistence Abstraction

```java
interface EligibilityStore {
    void save(StudentProfile profile, EligibilityResult result);
}
```

`FakeEligibilityStore` implements this interface.

The engine depends on abstraction instead of a concrete store.

---

## Adding a New Rule

To introduce a new rule:

```java
class InternshipClearanceRule implements EligibilityRule {
    public String evaluate(StudentProfile profile) {
        if (!profile.internshipCleared) {
            return "internship requirement not satisfied";
        }
        return null;
    }
}
```

Then register it:

```java
rules.add(new InternshipClearanceRule());
```

No edits required in the evaluation logic.

---

## Benefits of Refactor

| Before                 | After                    |
| ---------------------- | ------------------------ |
| Long if/else chain     | Rule list iteration      |
| Hard-coded rules       | Independent rule classes |
| High regression risk   | Safe extensibility       |
| Mixed formatting logic | Dedicated formatter      |
| Hard to test           | Rule-level testing       |

---

## Acceptance Criteria

* New rule added by creating a new class
* No modification to core evaluation loop
* No giant conditional chains
* Output remains identical
* Order of reasons preserved
* No external libraries

---

## How to Run

```bash
cd SOLID/Ex3/src
javac *.java
java Main
```

---

## Sample Output (Must Match Exactly)

```
=== Placement Eligibility ===
Student: Ayaan (CGR=8.10, attendance=72, credits=18, flag=NONE)
RESULT: NOT_ELIGIBLE
- attendance below 75
Saved evaluation for roll=23BCS1001
```

---

## Stretch Goal

Introduce a configuration object:

```java
class EligibilityConfig {
    double minCgr;
    int minAttendance;
    int minCredits;
}
```

Inject this into rule constructors.

This allows threshold changes without editing rule logic.

---

## Final Result

The eligibility system now:

* Complies with Open/Closed Principle
* Eliminates large conditional chains
* Encapsulates rules as independent units
* Separates formatting and persistence
* Preserves original behavior

The design is now modular, extensible, and maintainable.
