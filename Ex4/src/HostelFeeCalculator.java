import java.util.List;

public class HostelFeeCalculator {
    private final List<FeeComponent> feeComponents;

    public HostelFeeCalculator(List<FeeComponent> feeComponents) {
        this.feeComponents = feeComponents;
    }

    public Money calculateMonthly(BookingRequest req) {
        Money monthly = new Money(0.0);
        for (FeeComponent component : feeComponents) {
            monthly = monthly.plus(component.calculate(req));
        }
        return monthly;
    }
}
