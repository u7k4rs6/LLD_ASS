
public class BudgetLedger {

    private double totalBalance = 0.0;

    public void add(double amount, String description) {
        totalBalance += amount;
        if (amount >= 0) {
            System.out.println("Ledger: +" + (int) amount + " (" + description + ")"); 
        } else {
            System.out.println("Ledger: " + (int) amount + " (" + description + ")");
        }
    }

    public int balanceInt() {
        return (int) totalBalance;
    }
}
