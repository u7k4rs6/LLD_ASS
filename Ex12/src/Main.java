
import com.example.metrics.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Robust Singleton Metrics System ===");

        // Using Bill Pugh Singleton to load
        MetricsLoader loader = MetricsLoader.getInstance();
        loader.loadDefaultMetrics();

        // Using Double-Checked Locking Singleton to interact
        MetricsRegistry registry = MetricsRegistry.getInstance();
        registry.incrementMetric("REQUESTS_TOTAL");
        registry.incrementMetric("REQUESTS_TOTAL");
        registry.incrementMetric("DB_ERRORS");

        System.out.println("\nCurrent Metrics:");
        System.out.println("Total Requests: " + registry.getMetric("REQUESTS_TOTAL"));
        System.out.println("DB Errors: " + registry.getMetric("DB_ERRORS"));
        System.out.println("Cache Hits: " + registry.getMetric("CACHE_HITS"));

        // Verify Singleton identity
        MetricsRegistry anotherRegistry = MetricsRegistry.getInstance();
        System.out.println("\nSingleton Verification:");
        System.out.println("Are both registry instances the same? " + (registry == anotherRegistry));
    }
}
