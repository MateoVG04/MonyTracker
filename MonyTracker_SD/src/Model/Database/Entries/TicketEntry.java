package Model.Database.Entries;

import Model.Ticket;

public class TicketEntry {
    private final Ticket ticket;

    public TicketEntry(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
