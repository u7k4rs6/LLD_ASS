import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        
        Map<Integer, Money> roomPrices = new HashMap<>();
        roomPrices.put(LegacyRoomTypes.SINGLE, new Money(14000.0));
        roomPrices.put(LegacyRoomTypes.DOUBLE, new Money(15000.0));
        roomPrices.put(LegacyRoomTypes.TRIPLE, new Money(12000.0));
        
        Map<AddOn, Money> addonPrices = new HashMap<>();
        addonPrices.put(AddOn.MESS, new Money(1000.0));
        addonPrices.put(AddOn.LAUNDRY, new Money(500.0));
        addonPrices.put(AddOn.GYM, new Money(300.0));

        List<FeeComponent> feeComponents = List.of(
            new RoomFeeComponent(roomPrices, new Money(16000.0)),
            new AddOnFeeComponent(addonPrices)
        );

        HostelFeeCalculator calc = new HostelFeeCalculator(feeComponents);
        FakeBookingRepo repo = new FakeBookingRepo();

        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        
        Money monthly = calc.calculateMonthly(req);
        Money deposit = new Money(5000.00);
        
        ReceiptPrinter.print(req, monthly, deposit);
        
        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }
}
