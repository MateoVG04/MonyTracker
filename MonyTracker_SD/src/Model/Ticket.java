package Model;

import Model.Strategy.PayBehaviour;

import java.util.Map;

public class Ticket {
    // All the final members are final, assuming they can't be changed.
    // If we want to make a new function -> edit ticket, they won't be final anymore
    private final float totalAmount;
    private final Person payer;
    private final Map<Person, Float> paymentsOwed;
    private final Group group;
    private final PayBehaviour payBehaviour;
    private final String tag;
    private final String description;

    public Ticket(float totalAmount, Person payer, Group group, PayBehaviour payBehaviour, String tag, String description) {
        this.totalAmount = totalAmount;
        this.payer = payer;
        this.group = group;
        this.payBehaviour = payBehaviour;
        this.tag = tag;
        this.description = description;
        // When this ticket is created, we will calculate immediately who has to pay what
        paymentsOwed = payBehaviour.pay();
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
}
