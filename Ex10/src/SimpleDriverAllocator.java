
public class SimpleDriverAllocator implements DriverAllocator {
    @Override
    public String allocateDriver(String driverName) {
        System.out.println("Allocating driver: " + driverName);
        return driverName;
    }
}
