
package com.example.metrics;

/**
 * Bill Pugh Singleton implementation for metrics loading.
 * This utilizes the JVM's lazy-loading and thread-safety for inner classes.
 */
public class MetricsLoader {
    private MetricsLoader() {}

    private static class LoaderHolder {
        private static final MetricsLoader INSTANCE = new MetricsLoader();
    }

    public static MetricsLoader getInstance() {
        return LoaderHolder.INSTANCE;
    }

    public void loadDefaultMetrics() {
        MetricsRegistry registry = MetricsRegistry.getInstance();
        System.out.println("Loading default metrics keys...");
        registry.incrementMetric("REQUESTS_TOTAL");
        registry.incrementMetric("DB_ERRORS");
        registry.incrementMetric("CACHE_HITS");
        registry.incrementMetric("CACHE_MISSES");
    }
}
