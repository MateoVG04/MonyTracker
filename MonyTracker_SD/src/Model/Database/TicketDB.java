package Model.Database;

import Model.Database.Entries.TicketEntry;
import Model.Database.Entries.TicketEntryNull;

import java.util.HashMap;

public class TicketDB {
    private final HashMap<Integer, TicketEntry> db;
    private volatile static TicketDB uniqueInstance;

    private TicketDB() {
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

    public void addTicketEntry(int ticketID, TicketEntry ticketEntry) {
        if (!db.containsKey(ticketID)) {
            db.put(ticketID, ticketEntry);
        } else {
            throw new IllegalArgumentException("Person with ID " + ticketID + " already exists.");
        }
    }

    public TicketEntry getTicketEntry(int ticketID) {
        return this.db.getOrDefault(ticketID, new TicketEntryNull());
    }
}