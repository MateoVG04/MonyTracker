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
    // Moet niet hierstaan denk ik, beter in de group klasse. Want een person kan ook een ticket creeëren dat hij niet
    // zelf heeft betaald, en je kan vanuit hier niet de andere person zien die heeft gepayed.
    public void createTicket(float totalAmount, Person payer, Group group, PayBehaviour payBehaviour, String tag, String description) {
        Ticket ticket = new Ticket(totalAmount, payer, group, payBehaviour, tag, description);
        group.addTicket(ticket);
    }
}