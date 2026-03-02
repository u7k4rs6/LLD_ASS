import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Cafeteria Billing ===");

        MenuRegistry menu = new InMemoryMenuRegistry();
        TaxCalculator tax = new TaxRules();
        DiscountCalculator discount = new DiscountRules();
        PricingCalculator pricing = new PricingCalculator(tax, discount);
        InvoiceFormatter formatter = new InvoiceFormatter();
        InvoiceRepository repository = new FileStore();

        CafeteriaSystem sys = new CafeteriaSystem(menu, pricing, formatter, repository);
        menu.addToMenu(new MenuItem("M1", "Veg Thali", 80.00));
        menu.addToMenu(new MenuItem("C1", "Coffee", 30.00));
        menu.addToMenu(new MenuItem("S1", "Sandwich", 60.00));

        List<OrderLine> order = List.of(
                new OrderLine("M1", 2),
                new OrderLine("C1", 1)
        );

        sys.checkout("student", order);
    }
}
