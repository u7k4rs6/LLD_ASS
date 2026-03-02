
import com.example.tickets.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Immutable Ticket System ===");
        
        List<String> initialTags = new ArrayList<>(Arrays.asList("network", "server"));
        IncidentTicket ticket = new IncidentTicket("TKT-101", "Database connectivity issue", "Open", 2, initialTags);
        
        System.out.println("Original Ticket: " + ticket);
        
        TicketService service = new TicketService();
        
        // Operations return NEW instances
        IncidentTicket inProgressTicket = service.updateStatus(ticket, "In Progress");
        IncidentTicket finalTicket = service.addTag(inProgressTicket, "urgent");
        
        System.out.println("\nAfter updates:");
        System.out.println("Original Ticket (Unchanged): " + ticket);
        System.out.println("Latest Ticket: " + finalTicket);
        
        if (Validation.isValid(finalTicket)) {
            System.out.println("\nTicket is valid.");
        }
    }
}
