import Containers.CustomArrayList;
import Containers.CustomHashSet;
import ticket.BusTicket;
import ticket.TicketValidator;

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
        CustomArrayList<BusTicket> arrayList = new CustomArrayList<>();
        arrayList.put(new BusTicket("STD", "DAY", "2023-01-01", 10.0));
        arrayList.put(new BusTicket("CLA", "WEEK", "2023-01-02", 50.0));
        arrayList.put(new BusTicket("VIP", "MONTH", "2023-01-03", 100.0));

        System.out.println("CustomArrayList:");
        System.out.println(arrayList);

        arrayList.deletebyindex(1);
        System.out.println("After deleting index 1:");
        System.out.println(arrayList);

        System.out.println("Get ticket at index 0:");
        System.out.println(arrayList.getbyindex(0));

        // Testing CustomHashSet Iteration
        CustomHashSet<BusTicket> hashSet = new CustomHashSet<>();
        hashSet.put(new BusTicket("STD", "DAY", "2023-01-01", 10.0));
        hashSet.put(new BusTicket("CLA", "WEEK", "2023-01-02", 50.0));
        hashSet.put(new BusTicket("VIP", "MONTH", "2023-01-03", 100.0));

        System.out.println("CustomHashSet:");
        for (BusTicket ticket : hashSet) {
            System.out.println(ticket);
        }
    }
}