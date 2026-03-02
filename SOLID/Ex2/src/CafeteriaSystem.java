import java.util.*;

public class CafeteriaSystem {
    private final MenuRegistry menu;
    private final PricingCalculator pricing;
    private final InvoiceFormatter formatter;
    private final InvoiceRepository repository;
    private int invoiceSeq = 1000;

    public CafeteriaSystem(MenuRegistry menu, PricingCalculator pricing, InvoiceFormatter formatter, InvoiceRepository repository) {
        this.menu = menu;
        this.pricing = pricing;
        this.formatter = formatter;
        this.repository = repository;
    }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);
        
        InvoiceData data = pricing.calculate(customerType, invId, lines, menu);
        String printable = formatter.format(data);
        
        System.out.print(printable);

        repository.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + repository.countLines(invId) + ")");
    }
}
