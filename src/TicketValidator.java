import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TicketValidator {
    private int totalTickets;
    private int validTickets;
    private Map<String, Integer> violationsCount;

    public TicketValidator() {
        totalTickets = 0;
        validTickets = 0;
        violationsCount = new HashMap<>();
    }

    public void validateTickets(List<BusTicket> tickets) {
        for (BusTicket ticket : tickets) {
            totalTickets++;
            List<String> violations = validateTicket(ticket);
            if (violations.isEmpty()) {
                validTickets++;
            } else {
                for (String violation : violations) {
                    violationsCount.put(violation, violationsCount.getOrDefault(violation, 0) + 1);
                }
                System.err.println("Ticket validation error: " + violations + " for ticket class: " + ticket.ticketClass);
            }
        }
    }

    private List<String> validateTicket(BusTicket ticket) {
        List<String> violations = new ArrayList<>();

        if (!isValidType(ticket.type)) {
            violations.add("ticket type");
        }

        if (ticket.price <= 0) {
            violations.add("price");
        }

        if (requiresStartDate(ticket.type) && (ticket.startDate == null || ticket.startDate.isEmpty())) {
            violations.add("start date");
        }

        if (ticket.ticketClass == null || ticket.ticketClass.isEmpty()) {
            violations.add("ticket class");
        }

        return violations;
    }

    private boolean isValidType(String type) {
        return "DAY".equals(type) || "WEEK".equals(type) || "YEAR".equals(type);
    }

    private boolean requiresStartDate(String type) {
        return "DAY".equals(type) || "WEEK".equals(type) || "YEAR".equals(type);
    }

    public void printSummary() {
        System.out.println("Total = " + totalTickets);
        System.out.println("Valid = " + validTickets);

        String mostPopularViolation = violationsCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");

        System.out.println("Most popular violation = " + mostPopularViolation);
    }
}