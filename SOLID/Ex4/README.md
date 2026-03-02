
---

# Ex4 – OCP Refactor

## Hostel Fee Calculator

---

## Overview

This exercise refactors a hostel fee calculation system to comply with the **Open/Closed Principle (OCP)**.

The objective is to design the system so that:

* New room types can be added without modifying a large switch statement
* New add-ons can be introduced without editing core calculation logic
* Output format remains identical
* Responsibilities are clearly separated

Constraints:

* `BookingRequest` fields must remain unchanged
* Receipt formatting must remain identical
* No external libraries

---

## Existing System Behavior

The current application:

1. Accepts a `BookingRequest` containing:

   * `roomType`
   * List of `addOns`

2. Calculates:

   * Monthly fee
   * One-time deposit
   * Total due

3. Prints a formatted receipt.

4. Saves the booking to a fake store.

All logic is handled inside `HostelFeeCalculator.calculate`.

---

## Design Problems

`HostelFeeCalculator.calculate` currently:

* Uses a `switch` statement to determine room pricing
* Uses repeated branching logic for add-ons
* Mixes calculation, formatting, and persistence
* Hard-codes pricing rules
* Scatters money arithmetic across condition blocks

### Why This Is a Problem

* Violates Open/Closed Principle
* Adding a new room type requires editing the switch-case
* Adding a new add-on requires modifying core logic
* High regression risk
* Difficult to test pricing independently
* Formatting mixed with business logic

The design is rigid and difficult to extend.

---

## Refactored Architecture

After applying OCP:

```id="fyre1x"
Main
 └── HostelFeeCalculator
      ├── PricingComponent (interface)
      │     ├── RoomPricing (interface)
      │     ├── AddOnPricing (interface)
      │
      ├── ReceiptFormatter
      └── BookingRepository (interface)
            └── FakeBookingStore
```

The calculator becomes extensible without modification.

---

## Core Abstractions

### PricingComponent

```java id="z91w3f"
interface PricingComponent {
    double monthlyCharge();
    double depositCharge();
}
```

Both room types and add-ons implement this interface.

Each component contributes independently to the final total.

---

## Room Pricing Abstraction

```java id="qaxn4j"
interface RoomPricing extends PricingComponent {
}
```

Example:

```java id="g8hr39"
class DoubleRoomPricing implements RoomPricing {

    public double monthlyCharge() {
        return 15000.00;
    }

    public double depositCharge() {
        return 5000.00;
    }
}
```

No switch-case required.

---

## Add-On Pricing Abstraction

```java id="ptw0mk"
interface AddOnPricing extends PricingComponent {
}
```

Example:

```java id="t8o5cs"
class LaundryAddOn implements AddOnPricing {

    public double monthlyCharge() {
        return 1000.00;
    }

    public double depositCharge() {
        return 0.00;
    }
}
```

Each add-on is independent and self-contained.

---

## Refactored Calculation Flow

`HostelFeeCalculator` now orchestrates pricing components:

```java id="sl0x9u"
public void calculate(BookingRequest request) {

    List<PricingComponent> components = pricingFactory.create(request);

    double totalMonthly = 0;
    double totalDeposit = 0;

    for (PricingComponent component : components) {
        totalMonthly += component.monthlyCharge();
        totalDeposit += component.depositCharge();
    }

    double totalDue = totalMonthly + totalDeposit;

    Receipt receipt = new Receipt(...);

    String formatted = formatter.format(receipt);

    System.out.println(formatted);

    repository.save(receipt);
}
```

### What Changed

* No switch-case
* No branching per add-on
* No inline formatting
* No direct persistence logic

The calculator coordinates pricing components instead of implementing pricing rules itself.

---

## Adding a New Room Type

To add a `TripleRoomPricing`:

```java id="5p3m4u"
class TripleRoomPricing implements RoomPricing {

    public double monthlyCharge() {
        return 18000.00;
    }

    public double depositCharge() {
        return 7000.00;
    }
}
```

No edits required in the calculator.

---

## Adding a New Add-On

```java id="21ybhs"
class GymAddOn implements AddOnPricing {

    public double monthlyCharge() {
        return 1500.00;
    }

    public double depositCharge() {
        return 0.00;
    }
}
```

No modification to core fee logic required.

---

## Receipt Formatting Responsibility

Formatting is handled separately:

```java id="l1qg5r"
class ReceiptFormatter {
    String format(Receipt receipt);
}
```

The calculator does not handle string construction.

---

## Persistence Abstraction

```java id="a9w2hf"
interface BookingRepository {
    void save(Receipt receipt);
}
```

`FakeBookingStore` implements this interface.

The calculator depends on abstraction rather than a concrete store.

---

## Benefits of Refactor

| Before                 | After                   |
| ---------------------- | ----------------------- |
| Large switch-case      | Polymorphic pricing     |
| Repeated branching     | Component-based pricing |
| Hard-coded logic       | Extensible design       |
| Mixed responsibilities | Clear separation        |
| OCP violation          | OCP compliant           |

---

## Acceptance Criteria

* No switch-case in calculator
* New room types added via new classes
* New add-ons added without editing core logic
* Receipt output unchanged
* `BookingRequest` unchanged
* No external libraries

---

## How to Run

```bash id="2b0o7k"
cd SOLID/Ex4/src
javac *.java
java Main
```

---

## Sample Output (Must Match Exactly)

```id="3qep5x"
=== Hostel Fee Calculator ===
Room: DOUBLE | AddOns: [LAUNDRY, MESS]
Monthly: 16000.00
Deposit: 5000.00
TOTAL DUE NOW: 21000.00
Saved booking: H-7781
```

---

## Stretch Goal

### Add Late Fee Rule

Introduce a new component:

```java id="psv9km"
class LateFeeComponent implements PricingComponent {

    public double monthlyCharge() {
        return 2000.00;
    }

    public double depositCharge() {
        return 0.00;
    }
}
```

Add it to the component list without modifying the main calculation loop.

---

## Final Result

The hostel fee system now:

* Complies with the Open/Closed Principle
* Uses polymorphism instead of switch-case
* Separates calculation, formatting, and persistence
* Allows new pricing rules without modifying core logic
* Preserves existing output behavior

The design is modular, extensible, and maintainable.
