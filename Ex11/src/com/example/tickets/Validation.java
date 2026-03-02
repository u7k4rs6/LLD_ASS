
package com.example.tickets;

public class Validation {
    public static boolean isValid(IncidentTicket ticket) {
        return ticket.getId() != null && !ticket.getId().isEmpty() &&
               ticket.getPriority() >= 1 && ticket.getPriority() <= 5;
    }
}
