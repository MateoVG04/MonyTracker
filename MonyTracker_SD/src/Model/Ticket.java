package Model;

import Model.Strategy.PayBehaviour;

import java.util.Map;

public class Ticket {
    private float totalAmount;
    private Person payer;
    private Map<Person, Float> paymentsOwed;
    private Group group;
    private final PayBehaviour payBehaviour;
    private final String tag;
    private final String description;
    private final int ticketID;
    private static int IDCounter = 0;
    // Each time person gets created, IDCounter will be incremented
    // And make it synchronized, so it's thread safe
    private static synchronized int generateID() {
        return IDCounter++;
    }
    public Ticket(float totalAmount, Person payer, Group group, PayBehaviour payBehaviour, String tag, String description) {
        this.totalAmount = totalAmount;
        this.payer = payer;
        this.group = group;
        this.payBehaviour = payBehaviour;
        this.tag = tag;
        this.description = description;
        // Wanneer ticket wordt gecreëerd gaan we direct berekenen wie wat moet betalen voor dit ticket.
        paymentsOwed = payBehaviour.pay();
        ticketID = generateID();
    }
    public float getTotalAmount() {
        return totalAmount;
    }
    public Person getPayer() {
        return payer;
    }
    public Map<Person, Float> getPaymentsOwed() {
        return paymentsOwed;
    }
    public Group getGroup() {
        return group;
    }
    public PayBehaviour getPayBehaviour() {
        return payBehaviour;
    }
    public String getTag() {
        return tag;
    }
    public String getDescription() {
        return description;
    }
    public int getTicketID() {
        return ticketID;
    }
}
