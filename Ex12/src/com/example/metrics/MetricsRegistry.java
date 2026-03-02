
package com.example.metrics;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Robust Singleton implementation for application metrics.
 * 1. Double-Checked Locking with volatile to ensure thread-safety.
 * 2. readResolve() to protect against serialization.
 * 3. Constructor check to protect against reflection-based attacks.
 */
public class MetricsRegistry implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // volatile to prevent instruction reordering
    private static volatile MetricsRegistry instance;
    private final Map<String, Integer> metrics = new ConcurrentHashMap<>();

    private MetricsRegistry() {
        // Prevent reflection attack
        if (instance != null) {
            throw new RuntimeException("MetricsRegistry is a Singleton!");
        }
    }

    public static MetricsRegistry getInstance() {
        if (instance == null) {
            // Synchronize on the class level
            synchronized (MetricsRegistry.class) {
                if (instance == null) {
                    instance = new MetricsRegistry();
                }
            }
        }
        return instance;
    }

    // Required to maintain Singleton during deserialization
    protected Object readResolve() {
        return getInstance();
    }

    public void incrementMetric(String name) {
        metrics.merge(name, 1, Integer::sum);
    }

    public int getMetric(String name) {
        return metrics.getOrDefault(name, 0);
    }

    public void resetAll() {
        metrics.clear();
    }
}
