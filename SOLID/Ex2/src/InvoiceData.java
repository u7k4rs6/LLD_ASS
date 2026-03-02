public class InvoiceData {
    public final String invoiceId;
    public final double subtotal;
    public final double taxPct;
    public final double tax;
    public final double discount;
    public final double total;
    public final java.util.List<InvoiceLine> lines;

    public InvoiceData(String invoiceId, double subtotal, double taxPct, double tax, double discount, double total, java.util.List<InvoiceLine> lines) {
        this.invoiceId = invoiceId;
        this.subtotal = subtotal;
        this.taxPct = taxPct;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
        this.lines = lines;
    }

    public static class InvoiceLine {
        public final String name;
        public final int qty;
        public final double lineTotal;

        public InvoiceLine(String name, int qty, double lineTotal) {
            this.name = name;
            this.qty = qty;
            this.lineTotal = lineTotal;
        }
    }
}
