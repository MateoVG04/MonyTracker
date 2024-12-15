package Model.Strategy;

import Model.Group;
import Model.Person;
import Model.Ticket;

import java.util.HashMap;
import java.util.Map;


// Split the total amount by percentages between the group
public class SplitByPercentage implements PayBehaviour {
    private Map<Person, Float> percentages;
    private Group group;
    private Person payer;
    private float totalAmount;

    public SplitByPercentage(Map<Person, Float> percentages, Ticket ticket) {
        this.percentages = percentages;
        this.group = ticket.getGroup();
        this.payer = ticket.getPayer();
        this.totalAmount = ticket.getTotalAmount();
        // Check if all percentages equal to 100%
        float checkSum = 0;
        for (Person person : group.getGroupMembers()) {
            if (person != payer) {
                checkSum += percentages.get(person);
            }
        }
        if (checkSum != 100) {
            throw new IllegalArgumentException("The total percentages must equal to 100%");
        }
    }

    @Override
    public Map<Person, Float> pay() {
        Map<Person, Float> paymentsOwed = new HashMap<>();
        // Calculate payments owed by whom based on percentages
        for (Person person : group.getGroupMembers()) {
            if (person != payer) {
                float percentage = percentages.get(person);
                float payment = totalAmount * (percentage/100);
                paymentsOwed.put(person, payment);
            }
            System.out.println(person + "Needs to pay: €" + paymentsOwed.get(person));
        }
        return paymentsOwed;
    }
}
