package Model.Database;

import Model.Database.Entries.PersonEntry;
import Model.Database.Entries.PersonEntryNull;

import java.util.HashMap;



// For all the accounts, right now were not using this
// We could use this to verify if a user has an account and only then add it to the group
public class PersonDB {
    private final HashMap<Integer, PersonEntry> db;
    private volatile static PersonDB uniqueInstance;

    private PersonDB() {
        this.db = new HashMap<>();
    }

    public static PersonDB getInstance() {
        if (uniqueInstance == null) {
            synchronized (PersonDB.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new PersonDB();
                }
            }
        }
        return uniqueInstance;
    }

    public void addPersonEntry(PersonEntry personEntry) {
        int personID = personEntry.getPerson().getPersonID();
        if (!db.containsKey(personID)) {
            db.put(personID, personEntry);
        } else {
            throw new IllegalArgumentException("Person with ID " + personID + " already exists.");
        }
    }

    public PersonEntry getPersonEntry(int personID) {
        return this.db.getOrDefault(personID, new PersonEntryNull());
    }

    public void removePersonEntry(int personID) {
        if (db.containsKey(personID)) {
            db.remove(personID);
        }
        else {
            throw new IllegalArgumentException("Person with ID " + personID + " does not exist.");
        }
    }
}