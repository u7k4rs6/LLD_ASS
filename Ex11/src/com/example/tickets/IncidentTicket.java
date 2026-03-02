
package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class IncidentTicket {
    private final String id;
    private final String description;
    private final String status;
    private final int priority;
    private final List<String> tags;

    public IncidentTicket(String id, String description, String status, int priority, List<String> tags) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.priority = priority;
        // Defensive copy and unmodifiable list for true immutability
        this.tags = Collections.unmodifiableList(new ArrayList<>(tags));
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public int getPriority() { return priority; }
    public List<String> getTags() { return tags; }

    @Override
    public String toString() {
        return "IncidentTicket{id='" + id + "', description='" + description + 
               "', status='" + status + "', priority=" + priority + ", tags=" + tags + "}";
    }
}
