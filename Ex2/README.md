
---

# Ex2 – SRP Refactor

## Campus Cafeteria Billing System

---

## Overview

This exercise focuses on refactoring a cafeteria billing console application to comply with the **Single Responsibility Principle (SRP)**.

The objective is to separate concerns so that each component has a single reason to change, while preserving:

* Identical invoice output
* Existing `MenuItem` and `OrderLine` public fields
* No external libraries
* Console behavior unchanged

---

## Existing System Behavior

The current application performs the following steps:

1. Maintains an in-memory menu.
2. Constructs an order inside `Main`.
3. Calculates:

   * Subtotal
   * Tax
   * Discount
   * Final total
4. Prints a formatted invoice.
5. Saves the invoice to an in-memory store.

---

## Design Problems

The method `CafeteriaSystem.checkout` currently handles multiple unrelated responsibilities:

* Menu lookup
* Pricing calculations
* Tax rules
* Discount rules
* Invoice formatting
* Console printing
* Persistence

### Why This Is Problematic

* Violates Single Responsibility Principle
* Hard-coded tax logic
* Hard-coded discount logic
* Formatting tightly coupled with calculations
* Persistence tied to a concrete implementation
* Difficult to extend or test
* Large, fragile method

This results in a highly coupled system that is hard to maintain.

---

## Refactored Architecture

After applying SRP:

```id="hv7xqk"
Main
 └── CafeteriaSystem
      ├── PricingService
      ├── TaxPolicy (interface)
      ├── DiscountPolicy (interface)
      ├── InvoiceFormatter
      ├── InvoiceRepository (interface)
      │     └── InMemoryInvoiceStore
```

Each component now has a clearly defined responsibility.

---

## Component Responsibilities

### PricingService

**Responsibility:** Calculate subtotal from order lines.

```java
class PricingService {
    double calculateSubtotal(List<OrderLine> lines);
}
```

No formatting. No tax logic.

---

### TaxPolicy (Abstraction)

```java
interface TaxPolicy {
    double calculateTax(double subtotal);
}
```

Example implementation:

```java
class StandardTaxPolicy implements TaxPolicy {
    public double calculateTax(double subtotal) {
        return subtotal * 0.05;
    }
}
```

Tax rules can now change without modifying checkout logic.

---

### DiscountPolicy (Abstraction)

```java
interface DiscountPolicy {
    double calculateDiscount(double subtotal);
}
```

Example:

```java
class StudentDiscountPolicy implements DiscountPolicy {
    public double calculateDiscount(double subtotal) {
        return subtotal > 100 ? 10.0 : 0.0;
    }
}
```

New discounts can be added without editing a large method.

---

### InvoiceFormatter

**Responsibility:** Convert invoice data into formatted text.

```java
class InvoiceFormatter {
    String format(Invoice invoice);
}
```

All string formatting is isolated here.

---

### InvoiceRepository

```java
interface InvoiceRepository {
    void save(Invoice invoice);
}
```

Example implementation:

```java
class InMemoryInvoiceStore implements InvoiceRepository
```

`CafeteriaSystem` depends on abstraction, not a concrete storage class.

---

## Refactored Checkout Flow

After refactoring, `CafeteriaSystem` becomes an orchestrator:

```java
public void checkout(Order order) {

    double subtotal = pricingService.calculateSubtotal(order.getLines());

    double tax = taxPolicy.calculateTax(subtotal);

    double discount = discountPolicy.calculateDiscount(subtotal);

    double total = subtotal + tax - discount;

    Invoice invoice = new Invoice(...);

    String formatted = formatter.format(invoice);

    System.out.println(formatted);

    repository.save(invoice);
}
```

### What It No Longer Does

* No tax calculations inline
* No discount logic inline
* No string formatting
* No direct persistence logic

It coordinates workflow only.

---

## Benefits of Refactor

| Before                 | After              |
| ---------------------- | ------------------ |
| Mixed responsibilities | Clear separation   |
| Hard-coded rules       | Pluggable policies |
| Tight coupling         | Loose coupling     |
| Hard to extend         | Easily extensible  |
| Large method           | Modular design     |

---

## Extensibility Example

### Adding a New Discount Policy

To introduce a staff discount:

```java
class StaffDiscountPolicy implements DiscountPolicy {
    public double calculateDiscount(double subtotal) {
        return subtotal * 0.15;
    }
}
```

No changes required in `CafeteriaSystem`.

---

### Changing Tax Rate

Replace `StandardTaxPolicy` with another implementation.

No modification to checkout logic required.

---

## Acceptance Criteria

* Invoice text remains identical
* Line order remains identical
* `CafeteriaSystem` does not format strings
* `CafeteriaSystem` does not encode tax/discount logic
* Persistence is abstracted
* Adding new discount does not require editing a long method
* No external libraries used

---

## How to Run

```bash
cd SOLID/Ex2/src
javac *.java
java Main
```

---

## Sample Output (Must Match)

```
=== Cafeteria Billing ===
Invoice# INV-1001
- Veg Thali x2 = 160.00
- Coffee x1 = 30.00
Subtotal: 190.00
Tax(5%): 9.50
Discount: -10.00
TOTAL: 189.50
Saved invoice: INV-1001 (lines=7)
```

---

## Stretch Goal

### Staff Invoice with Different Discount Policy

Inject a different `DiscountPolicy` implementation for staff orders.

The billing workflow remains unchanged.

---

## Final Outcome

The system now:

* Follows Single Responsibility Principle
* Encapsulates rules behind interfaces
* Supports policy injection
* Is modular and testable
* Maintains identical console output

This refactor transforms a tightly coupled billing system into a clean, extensible, SRP-compliant design.
