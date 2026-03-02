
package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

public class TicketService {
    public IncidentTicket updateStatus(IncidentTicket ticket, String newStatus) {
        System.out.println("Updating ticket status to: " + newStatus);
        return new IncidentTicket(
            ticket.getId(),
            ticket.getDescription(),
            newStatus,
            ticket.getPriority(),
            ticket.getTags()
        );
    }

    public IncidentTicket addTag(IncidentTicket ticket, String newTag) {
        System.out.println("Adding tag: " + newTag);
        List<String> newTags = new ArrayList<>(ticket.getTags());
        newTags.add(newTag);
        return new IncidentTicket(
            ticket.getId(),
            ticket.getDescription(),
            ticket.getStatus(),
            ticket.getPriority(),
            newTags
        );
    }
}
