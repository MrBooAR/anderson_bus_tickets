import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<BusTicket> tickets = Arrays.asList(
                new BusTicket("Economy","DAY", "2024-11-07", 10),
                new BusTicket("Business","WEEK", null, 20),
                new BusTicket("First Class","YEAR", "2024-01-01", 0),
                new BusTicket("Economy","MONTH", "2024-02-01", 15),
                new BusTicket("","DAY", "2024-03-01", 30) // Missing ticket class to trigger violation
        );

        TicketValidator validator = new TicketValidator();
        validator.validateTickets(tickets);
        validator.printSummary();
    }
}
