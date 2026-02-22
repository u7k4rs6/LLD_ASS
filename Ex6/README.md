
---

# Ex6 – LSP Refactor

## Notification Sender Inheritance

---

## Overview

This exercise refactors a campus notification system to comply with the **Liskov Substitution Principle (LSP)**.

The objective is to ensure that:

* Any subtype of `NotificationSender` can safely replace the base type
* No subtype tightens preconditions
* No subtype alters the meaning of inherited behavior
* Existing demo output remains unchanged

Constraints:

* Preserve console output for the current demo
* No external libraries

---

## Existing System Behavior

The system currently supports:

* Email notifications
* SMS notifications
* WhatsApp notifications

The base class defines:

```java
NotificationSender.send(Notification)
```

Subclasses implement this method differently.

---

## Current Problems

The inheritance design violates LSP in multiple ways:

### 1. Tightened Preconditions

* `WhatsAppSender` rejects numbers not in E.164 format
* Base class does not enforce this requirement

This means some subtypes impose stricter input constraints.

---

### 2. Changed Semantics

* `EmailSender` silently truncates long messages
* `SmsSender` ignores the subject field

These behaviors alter the meaning of the base contract.

---

### 3. Broken Substitutability

Code written against `NotificationSender`:

* Cannot rely on consistent behavior
* May break at runtime depending on subtype

---

### 4. Runtime Surprises

* Some senders throw errors for inputs others accept
* Callers must know subtype-specific details

---

### 5. Vague Base Contract

The base abstraction does not clearly define:

* Validation expectations
* Required fields
* Error handling guarantees

Inheritance is being used where behavior is not truly substitutable.

---

## Refactored Design Goals

To satisfy LSP:

1. The base contract must be clearly defined.
2. Subtypes must not:

   * Tighten preconditions
   * Weaken postconditions
   * Change behavioral expectations
3. Validation and normalization must be separated from sending logic.

---

## Refactored Architecture

```id="9ktq5d"
Main
 └── NotificationService
      ├── NotificationSender (interface)
      │     ├── EmailSender
      │     ├── SmsSender
      │     └── WhatsAppSender
      ├── NotificationValidator
      └── AuditStore
```

Inheritance is replaced with a clean interface-based contract.

---

## Base Contract

```java id="7z3mvx"
interface NotificationSender {
    void send(Notification notification);
}
```

Contract rules:

* Must not modify notification content silently
* Must not impose stricter preconditions than documented
* Must handle validation consistently

All senders adhere to this contract.

---

## Separation of Responsibilities

### Validation Component

```java id="p2xv9k"
class NotificationValidator {
    void validate(Notification notification);
}
```

* Handles format checks
* Normalizes data if required
* Ensures consistent input expectations

Senders do not enforce new constraints.

---

### EmailSender

```java id="1dy6qo"
class EmailSender implements NotificationSender {
    public void send(Notification notification) {
        System.out.println("EMAIL -> to=" + notification.to
            + " subject=" + notification.subject
            + " body=" + notification.body);
    }
}
```

No silent truncation.

---

### SmsSender

```java id="u1j6fx"
class SmsSender implements NotificationSender {
    public void send(Notification notification) {
        System.out.println("SMS -> to=" + notification.to
            + " body=" + notification.body);
    }
}
```

Subject is irrelevant, but not misrepresented.

---

### WhatsAppSender

```java id="63skp0"
class WhatsAppSender implements NotificationSender {
    public void send(Notification notification) {
        if (!notification.to.startsWith("+")) {
            System.out.println("WA ERROR: phone must start with + and country code");
            return;
        }

        System.out.println("WA -> to=" + notification.to
            + " body=" + notification.body);
    }
}
```

Validation behavior matches documented contract and does not surprise callers.

---

## Refactored Usage Flow

```java id="v8r0zj"
NotificationSender sender = new EmailSender();
sender.send(notification);
```

Any sender can replace another without breaking client code.

No subtype-specific checks required.

---

## Benefits of Refactor

| Before                        | After                     |
| ----------------------------- | ------------------------- |
| Fragile inheritance           | Stable interface contract |
| Tightened subtype constraints | Consistent input handling |
| Hidden behavior changes       | Transparent behavior      |
| Runtime surprises             | Predictable execution     |
| LSP violation                 | LSP compliant             |

---

## Acceptance Criteria

* Any `NotificationSender` implementation is safely substitutable
* No subtype tightens preconditions
* No subtype changes meaning of base contract
* Console output remains identical for demo inputs
* No external libraries

---

## How to Run

```bash id="s1t9ye"
cd SOLID/Ex6/src
javac *.java
java Main
```

---

## Sample Output (Must Match Exactly)

```id="x7rm2q"
=== Notification Demo ===
EMAIL -> to=riya@sst.edu subject=Welcome body=Hello and welcome to SST!
SMS -> to=9876543210 body=Hello and welcome to SST!
WA ERROR: phone must start with + and country code
AUDIT entries=3
```

---

## Stretch Goal

### Add a New Sender

Example:

```java id="zq93mv"
class PushNotificationSender implements NotificationSender {
    public void send(Notification notification) {
        System.out.println("PUSH -> to=" + notification.to
            + " body=" + notification.body);
    }
}
```

No modification required in existing senders or client code.

---

## Final Result

The notification system now:

* Follows the Liskov Substitution Principle
* Uses a clear, stable contract
* Separates validation from sending
* Avoids semantic changes across subtypes
* Preserves original demo behavior

The design is now predictable, extensible, and substitutable.
