import java.util.ArrayList;
import java.util.List;

public class PricingCalculator {
    private final TaxCalculator taxCalculator;
    private final DiscountCalculator discountCalculator;

    public PricingCalculator(TaxCalculator taxCalculator, DiscountCalculator discountCalculator) {
        this.taxCalculator = taxCalculator;
        this.discountCalculator = discountCalculator;
    }

    public InvoiceData calculate(String customerType, String invoiceId, List<OrderLine> orderLines, MenuRegistry menu) {
        double subtotal = 0.0;
        List<InvoiceData.InvoiceLine> invoiceLines = new ArrayList<>();

        for (OrderLine l : orderLines) {
            MenuItem item = menu.getItem(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
            invoiceLines.add(new InvoiceData.InvoiceLine(item.name, l.qty, lineTotal));
        }

        double taxPct = taxCalculator.calculateTaxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);

        double discount = discountCalculator.calculateDiscount(customerType, subtotal, orderLines.size());

        double total = subtotal + tax - discount;

        return new InvoiceData(invoiceId, subtotal, taxPct, tax, discount, total, invoiceLines);
    }
}
