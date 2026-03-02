
public class EuclideanDistanceCalculator implements DistanceCalculator {
    @Override
    public double calculateDistance(GeoPoint point1, GeoPoint point2) {
        return Math.sqrt(Math.pow(point1.getLatitude() - point2.getLatitude(), 2) +
                Math.pow(point1.getLongitude() - point2.getLongitude(), 2));
    }
}
