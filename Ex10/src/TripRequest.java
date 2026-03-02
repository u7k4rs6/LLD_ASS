
public class TripRequest {
    private final String userName;
    private final GeoPoint userLocation;
    private final String driverName;
    private final GeoPoint driverLocation;

    public TripRequest(String userName, GeoPoint userLocation, String driverName, GeoPoint driverLocation) {
        this.userName = userName;
        this.userLocation = userLocation;
        this.driverName = driverName;
        this.driverLocation = driverLocation;
    }

    public String getUserName() { return userName; }
    public GeoPoint getUserLocation() { return userLocation; }
    public String getDriverName() { return driverName; }
    public GeoPoint getDriverLocation() { return driverLocation; }
}
