
public class ConsoleReceiptPrinter implements ReceiptPrinter {
    @Override
    public void printReceipt(String driverName, String userName, double distance, double fare) {
        System.out.println("Booking Receipt");
        System.out.println("Driver: " + driverName);
        System.out.println("User: " + userName);
        System.out.println("Distance: " + String.format("%.2f", distance) + " km");
        System.out.println("Fare: $" + String.format("%.2f", fare));
    }
}
