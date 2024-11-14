package ticket;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TicketValidator {
    private int totalTickets;
    private int validTickets;
    private final Map<String, Integer> violationsCount;

    public TicketValidator() {
        totalTickets = 0;
        validTickets = 0;
        violationsCount = new HashMap<>();
    }

    public void validateTickets(List<BusTicket> tickets) {
        for (BusTicket ticket : tickets) {
            totalTickets++;
            try {
                validateTicket(ticket);
                validTickets++;
            } catch (InvalidTicketException e) {
                System.err.println(e.getMessage() + " for ticket class: " + ticket.getTicketClass());
            }
        }
    }

    private void validateTicket(BusTicket ticket) throws InvalidTicketException {
        List<String> violations = new ArrayList<>();

        if (!isValidTicketType(ticket.getTicketType())) {
            violations.add("ticket type");
        }

        if (!isPriceValid(ticket.getPrice())) {
            violations.add("price");
        }

        if (isStartDateRequired(ticket.getTicketType()) && !isStartDateValid(ticket.getStartDate())) {
            violations.add("start date");
        }

        if (!isTicketClassValid(ticket.getTicketClass())) {
            violations.add("ticket class");
        }

        if (!violations.isEmpty()) {
            registerViolations(violations);
            throw new InvalidTicketException("Ticket validation error: " + violations);
        }
    }

    private boolean isValidTicketType(String ticketType) {
        return "DAY".equals(ticketType) || "WEEK".equals(ticketType) || "MONTH".equals(ticketType) || "YEAR".equals(ticketType);
    }

    private boolean isPriceValid(double price) {
        return price > 0 && price % 2 == 0;
    }

    private boolean isStartDateRequired(String ticketType) {
        return isValidTicketType(ticketType);
    }

    private boolean isStartDateValid(String startDate) {
        if (startDate == null || startDate.isEmpty()) {
            return false;
        }
        try {
            LocalDate date = LocalDate.parse(startDate);
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isTicketClassValid(String ticketClass) {
        return ticketClass != null && !ticketClass.isEmpty();
    }

    private void registerViolations(List<String> violations) {
        for (String violation : violations) {
            violationsCount.put(violation, violationsCount.getOrDefault(violation, 0) + 1);
        }
    }

    public void printSummary() {
        System.out.println("Total Tickets = " + totalTickets);
        System.out.println("Valid Tickets = " + validTickets);
        String mostFrequentViolation = violationsCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
        System.out.println("Most Frequent Violation = " + mostFrequentViolation);
    }
}