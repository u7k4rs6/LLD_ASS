import java.util.Map;

public class AddOnFeeComponent implements FeeComponent {
    private final Map<AddOn, Money> addOnPrices;

    public AddOnFeeComponent(Map<AddOn, Money> addOnPrices) {
        this.addOnPrices = addOnPrices;
    }

    @Override
    public Money calculate(BookingRequest req) {
        Money total = new Money(0.0);
        for (AddOn a : req.addOns) {
            Money price = addOnPrices.get(a);
            if (price != null) {
                total = total.plus(price);
            }
        }
        return total;
    }
}
