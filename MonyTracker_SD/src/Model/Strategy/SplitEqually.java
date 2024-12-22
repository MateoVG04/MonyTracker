package Model.Strategy;

import Model.Group;
import Model.Person;
import Model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


// Split the total amount equally between the group
public class SplitEqually implements PayBehaviour {
    private final float totalAmount;
    private final ArrayList<Person> groupMembers;

    public SplitEqually(float totalAmount, ArrayList<Person> groupMembers) {
        this.totalAmount = totalAmount;
        this.groupMembers = groupMembers;
        // Checked if valid is done when the ticket is being Added
    }

    @Override
    public Map<Person, Float> pay() {
        Map<Person, Float> paymentsOwed = new HashMap<>();
        float payment = totalAmount/groupMembers.size();
        for (Person person : groupMembers) {
            paymentsOwed.put(person, payment);
            System.out.println(person + "Needs to pay: €" + paymentsOwed.get(person));
        }
        return paymentsOwed;
    }

    @Override
    public String toString() {
        return "Split Equally";
    }
}
