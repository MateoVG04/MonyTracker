package Model.Database.Entries;

import Model.Ticket;

public class TicketEntryNull extends TicketEntry {
    public TicketEntryNull() {
        super(null);
    }

    @Override
    public Ticket getTicket() {
        return null;
    }
}
