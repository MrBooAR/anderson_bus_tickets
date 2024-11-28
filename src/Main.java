import java.io.IOException;
import java.util.List;
import models.BusTicket;
import service.TicketValidator;
import utils.TicketFileReader;

public class Main {
    public static void main(String[] args) {
        String filePath = Main.class.getClassLoader().getResource("ticket_data.json").getPath();
        try {
            List<BusTicket> tickets = TicketFileReader.loadTicketsFromFile(filePath);
            System.out.println("Number of tickets loaded: " + tickets.size());

            TicketValidator validator = new TicketValidator();
            validator.validateTickets(tickets);
            validator.printSummary();
        } catch (IOException e) {
            System.err.println("Error reading tickets from file: " + e.getMessage());
        }
    }
}