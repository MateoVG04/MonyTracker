package Model;

import Model.Strategy.PayBehaviour;

import java.util.Map;

public class Ticket {
    private float totalAmount;
    private Person payer;
    private Map<Person, Float> paymentsOwed;
    private Group group;
    private PayBehaviour payBehaviour;
    private String tag;
    private String description;

    public Ticket(float totalAmount, Person payer, Group group, PayBehaviour payBehaviour, String tag, String description) {
        this.totalAmount = totalAmount;
        this.payer = payer;
        this.group = group;
        this.payBehaviour = payBehaviour;
        this.tag = tag;
        this.description = description;
        // Wanneer ticket wordt gecreëerd gaan we direct berekenen wie wat moet betalen.
        executePayment();
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
    public void executePayment() {
        paymentsOwed = payBehaviour.pay();
    }
}
