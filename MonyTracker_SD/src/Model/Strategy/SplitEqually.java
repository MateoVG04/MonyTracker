package Model.Strategy;

import Model.Group;
import Model.Person;
import Model.Ticket;

import java.util.HashMap;
import java.util.Map;


// Split the total amount equally between the group
public class SplitEqually implements PayBehaviour {
    private Group group;
    private Person payer;
    private float totalAmount;

    public SplitEqually(Ticket ticket) {
        this.group = ticket.getGroup();
        this.payer = ticket.getPayer();
        this.totalAmount = ticket.getTotalAmount();
    }

    @Override
    public Map<Person, Float> pay() {
        Map<Person, Float> paymentsOwed = new HashMap<>();
        float payment = totalAmount/group.getSize();
        for (Person person : group.getGroupMembers()) {
            if (person != payer) {
                paymentsOwed.put(person, payment);
            }
            System.out.println(person + "Needs to pay: €" + paymentsOwed.get(person));
        }
        return paymentsOwed;
    }
}
