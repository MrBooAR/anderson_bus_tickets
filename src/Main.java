import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "D:\\anderson_beginners\\anderson_bus_tickets\\src\\ticket_data.json";
        try {
            List<BusTicket> tickets = BusTicket.loadTicketsFromFile(filePath);
            System.out.println("Number of tickets loaded: " + tickets.size());

            TicketValidator validator = new TicketValidator();
            validator.validateTickets(tickets);
            validator.printSummary();
        } catch (IOException e) {
            System.err.println("Error reading tickets from file: " + e.getMessage());
        }
    }
}