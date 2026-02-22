
---

# Ex1 – SRP Refactor

## Student Onboarding Registration

---

## Overview

This exercise demonstrates how to refactor a student onboarding system to comply with the **Single Responsibility Principle (SRP)**.

The objective is to restructure the design so that each class has one clear responsibility while preserving:

* Identical console output
* The existing `StudentRecord` implementation
* The existing `Main` class behavior
* No external libraries
* Default package usage only

---

## Existing System Behavior

The application currently performs the following steps:

1. Accepts a raw input string in key-value format:

   ```
   name=...;email=...;phone=...;program=...
   ```

2. Validates:

   * Fields are not empty
   * Email contains `@`
   * Phone contains only digits
   * Program belongs to an allowed list

3. Generates a unique student ID in this format:

   ```
   SST-YYYY-XXXX
   ```

4. Stores the student record in an in-memory database.

5. Prints:

   * A success message
   * The created student object
   * A formatted database table dump

---

## Design Problems

The method `OnboardingService.registerFromRawInput` currently performs multiple unrelated tasks:

* Parsing input
* Validation
* ID generation
* Data persistence
* Output formatting
* Console printing
* Error handling

### Why This Is a Problem

* Violates Single Responsibility Principle
* High coupling
* Difficult to test in isolation
* Difficult to extend
* Risky to modify
* Centralized “god method”

---

## Refactored Architecture

After applying SRP, the system structure becomes:

```
Main
 └── OnboardingService
      ├── StudentParser
      ├── StudentValidator
      ├── StudentIdGenerator
      ├── StudentRepository (interface)
      │     └── FakeDb
      └── StudentPrinter
```

Each class now has a single responsibility.

---

## Component Responsibilities

### StudentParser

**Responsibility:** Convert raw string input into structured data.

```java
class StudentParser {
    StudentData parse(String rawInput);
}
```

* No validation
* No printing
* Pure transformation

---

### StudentValidator

**Responsibility:** Validate structured student data.

```java
class StudentValidator {
    List<String> validate(StudentData data);
}
```

* Returns validation errors
* Does not print
* Fully unit testable

---

### StudentIdGenerator

**Responsibility:** Generate sequential student IDs.

```java
class StudentIdGenerator {
    String generate();
}
```

No persistence or formatting logic.

---

### StudentRepository (Abstraction Layer)

```java
interface StudentRepository {
    void save(StudentRecord record);
    int count();
    List<StudentRecord> findAll();
}
```

`FakeDb` implements this interface.

This removes direct dependency on a concrete database implementation.

---

### StudentPrinter

**Responsibility:** Handle all console output and formatting.

```java
class StudentPrinter {
    void printSuccess(StudentRecord record);
    void printDbDump(List<StudentRecord> records);
    void printErrors(List<String> errors);
}
```

Formatting is isolated from business logic.

---

## Refactored Workflow

After refactoring, `OnboardingService` acts only as a coordinator:

```java
public void registerFromRawInput(String rawInput) {

    StudentData data = parser.parse(rawInput);

    List<String> errors = validator.validate(data);

    if (!errors.isEmpty()) {
        printer.printErrors(errors);
        return;
    }

    String id = idGenerator.generate();

    StudentRecord record =
        new StudentRecord(id, data.name, data.email, data.phone, data.program);

    repository.save(record);

    printer.printSuccess(record);
    printer.printDbDump(repository.findAll());
}
```

### What Changed

* No parsing logic inside service
* No validation logic inside service
* No formatting inside service
* No direct knowledge of `FakeDb`

The service now orchestrates workflow only.

---

## Benefits of Refactor

| Before         | After                        |
| -------------- | ---------------------------- |
| God method     | Clean orchestration          |
| Tight coupling | Loose coupling               |
| Hard to test   | Independently testable units |
| Fragile design | Maintainable architecture    |
| SRP violated   | SRP enforced                 |

---

## Extensibility Example

### Adding a New Field

To add a field such as `city`:

* Update `StudentData`
* Update `StudentParser`
* Update `StudentValidator`
* Update `StudentRecord`

No changes required in `OnboardingService`.

This confirms SRP compliance.

---

## Acceptance Criteria

* Program output remains unchanged
* `OnboardingService` does not format output
* `OnboardingService` does not directly depend on `FakeDb`
* Validation is testable independently
* New fields do not require modifying a large centralized method
* No external libraries
* Default package maintained

---

## How to Run

```bash
cd SOLID/Ex1/src
javac *.java
java Main
```

---

## Final Outcome

The system now follows a clean, modular design where responsibilities are clearly separated. The refactor improves maintainability, testability, and extensibility without changing external behavior.
