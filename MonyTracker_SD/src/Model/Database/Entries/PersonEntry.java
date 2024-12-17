package Model.Database.Entries;

import Model.Person;

public class PersonEntry {
    private final Person person;
    // Additional info

    public PersonEntry(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
