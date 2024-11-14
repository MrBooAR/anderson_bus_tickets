package ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BusTicket {
    private final String ticketClass;
    private final String ticketType;
    private final String startDate;
    private final double price;

    public BusTicket(String ticketClass, String ticketType, String startDate, double price) {
        this.ticketClass = ticketClass;
        this.ticketType = ticketType;
        this.startDate = startDate;
        this.price = price;
    }

    public String getTicketClass() { return ticketClass; }
    public String getTicketType() { return ticketType; }
    public String getStartDate() { return startDate; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "ticket.BusTicket{" +
                "ticketClass='" + ticketClass + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", price=" + price +
                '}';
    }

    public static List<BusTicket> loadTicketsFromFile(String filePath) throws IOException {
        List<BusTicket> tickets = new ArrayList<>();

        // Read file content
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        // Remove whitespace and outer brackets, and split each ticket entry
        content = content.trim();
        if (content.startsWith("[") && content.endsWith("]")) {
            content = content.substring(1, content.length() - 1);
        } else {
            throw new IOException("Invalid JSON array format");
        }

        // Split each object by "},{"
        String[] entries = content.split("},\\s*\\{");

        for (String entry : entries) {
            // Add braces back if needed for consistency in each entry
            entry = "{" + entry + "}";

            // Parse the individual ticket
            tickets.add(parseSingleTicket(entry));
        }

        return tickets;
    }

    private static BusTicket parseSingleTicket(String entry) {
        String ticketClass = getValue(entry, "ticketClass");
        String ticketType = getValue(entry, "ticketType");
        String startDate = getValue(entry, "startDate");

        double price;
        try {
            String priceStr = getValue(entry, "price");
            price = (priceStr != null) ? Double.parseDouble(priceStr) : 0.0;
        } catch (NumberFormatException e) {
            System.err.println("Invalid price format, defaulting to 0: " + e.getMessage());
            price = 0.0;
        }

        return new BusTicket(ticketClass, ticketType, startDate, price);
    }

    private static String getValue(String json, String key) {
        // Regex to match the key and extract its value
        String regex = "\"" + key + "\"\\s*:\\s*(\"[^\"]*\"|null|\\d+\\.?\\d*)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            String value = matcher.group(1).replace("\"", ""); // Remove quotes
            return "null".equals(value) ? null : value;
        }
        return null;
    }
}