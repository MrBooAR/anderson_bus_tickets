public enum TicketType {
    DAY,
    WEEK,
    MONTH,
    YEAR;

    public static boolean isValid(String ticketType) {
        try {
            TicketType.valueOf(ticketType); // Check if the input matches an enum constant
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }
}