package Model.Database.Entries;

import Model.Person;

public class PersonEntry {
    private final Person person;

    public PersonEntry(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
