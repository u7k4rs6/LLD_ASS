
public class TransportBookingService {
    private final DistanceCalculator distanceCalculator;
    private final DriverAllocator driverAllocator;
    private final PaymentProcessor paymentProcessor;
    private final ReceiptPrinter receiptPrinter;

    public TransportBookingService(
            DistanceCalculator distanceCalculator,
            DriverAllocator driverAllocator,
            PaymentProcessor paymentProcessor,
            ReceiptPrinter receiptPrinter) {
        this.distanceCalculator = distanceCalculator;
        this.driverAllocator = driverAllocator;
        this.paymentProcessor = paymentProcessor;
        this.receiptPrinter = receiptPrinter;
    }

    public void bookTrip(TripRequest request) {
        double distance = distanceCalculator.calculateDistance(request.getUserLocation(), request.getDriverLocation());
        String driver = driverAllocator.allocateDriver(request.getDriverName());
        double fare = distance * 2.0; 
        paymentProcessor.processPayment(fare);
        receiptPrinter.printReceipt(driver, request.getUserName(), distance, fare);
    }
}
