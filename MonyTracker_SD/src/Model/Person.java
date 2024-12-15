package Model;

import Model.Strategy.PayBehaviour;

public class Person {
    private String name;
    private String email;
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public void createTicket(String wieBetaald, String wieOntvangt, float prijs, PayBehaviour payBehaviour, Group group) {
        Ticket ticket = new Ticket(prijs,wieBetaald,wieOntvangt,payBehaviour);
        group.addTicket(ticket);
    }
}
