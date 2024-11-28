package models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class BusTicket {
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
        return "BusTicket{" +
                "ticketClass='" + ticketClass + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", price=" + price +
                '}';
    }

}