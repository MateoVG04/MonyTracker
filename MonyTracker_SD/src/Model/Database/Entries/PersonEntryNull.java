package Model.Database.Entries;

import Model.Person;

public class PersonEntryNull extends PersonEntry {
    public PersonEntryNull() {
        super(null);
    }
    @Override
    public Person getPerson() {
        return null;
    }
}
