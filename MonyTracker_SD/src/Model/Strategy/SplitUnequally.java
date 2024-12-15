package Model.Strategy;

import Model.Group;
import Model.Person;
import Model.Ticket;

import java.util.HashMap;
import java.util.Map;

// Choose how much everyone has to pay
public class SplitUnequally implements PayBehaviour {
    private Map<Person, Float> personAmounts;
    private Group group;
    private Person payer;
    private float totalAmount;

    public SplitUnequally(Map<Person, Float> personAmounts, Ticket ticket) {
        this.personAmounts = personAmounts;
        this.group = ticket.getGroup();
        this.payer = ticket.getPayer();
        this.totalAmount = ticket.getTotalAmount();
        // Check if all personAmounts equal the totalAmount
        float checkSum = 0;
        for (Person person : group.getGroupMembers()) {
            if (person != payer) {
                checkSum += personAmounts.get(person);
            }
        }
        if (checkSum != totalAmount) {
            throw new IllegalArgumentException("The sum of all person amounts must equal to the total owed amount");
        }
    }

    @Override
    public Map<Person, Float>  pay() {
        Map<Person, Float> paymentsOwed = new HashMap<>();
        for (Person person : group.getGroupMembers()) {
            if (person != payer) {
                float payment = personAmounts.get(person);
                paymentsOwed.put(person, payment);
            }
            System.out.println(person + "Needs to pay: €" + paymentsOwed.get(person));
        }
        return paymentsOwed;
    }
}