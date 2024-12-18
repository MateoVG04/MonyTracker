package Model.Database;

import Model.Database.Entries.TicketEntry;
import Model.Database.Entries.TicketEntryNull;

import java.util.HashMap;

public class TicketDB {
    private final HashMap<Integer, TicketEntry> db;
    private volatile static TicketDB uniqueInstance;

    public TicketDB() {
        this.db = new HashMap<>();
    }

    public static TicketDB getInstance() {
        if (uniqueInstance == null) {
            synchronized (TicketDB.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new TicketDB();
                }
            }
        }
        return uniqueInstance;
    }

    public void addTicketEntry(TicketEntry ticketEntry) {
        int ticketID = ticketEntry.getTicket().getTicketID();
        if (!db.containsKey(ticketID)) {
            db.put(ticketID, ticketEntry);
        } else {
            throw new IllegalArgumentException("Person with ID " + ticketID + " already exists.");
        }
    }

    public TicketEntry getTicketEntry(int ticketID) {
        return this.db.getOrDefault(ticketID, new TicketEntryNull());
    }

    public void removeTicketEntry(int ticketID) {
        if (db.containsKey(ticketID)) {
            db.remove(ticketID);
        }
        else {
            throw new IllegalArgumentException("Person with ID " + ticketID + " does not exist.");
        }
    }
}
