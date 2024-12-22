package Model;

import Model.Strategy.PayBehaviour;

public class Person {
    private final String name;
    private final String email;
    private final int personID;
    private static int IDCounter = 0;
    // Each time person gets created, IDCounter will be incremented
    // And make it synchronized, so it's thread safe
    private static synchronized int generateID() {
        return IDCounter++;
    }
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
        personID = generateID();
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getPersonID() {
        return personID;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}