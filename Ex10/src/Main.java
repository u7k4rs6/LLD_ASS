
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Ride-Hailing Service (SOLID) ===");
        
        TransportBookingService service = new TransportBookingService(
                new EuclideanDistanceCalculator(),
                new SimpleDriverAllocator(),
                new ConsolePaymentProcessor(),
                new ConsoleReceiptPrinter()
        );

        TripRequest request = new TripRequest(
                "User1", new GeoPoint(12.9716, 77.5946),
                "Driver1", new GeoPoint(12.9816, 77.6046)
        );

        service.bookTrip(request);
    }
}
