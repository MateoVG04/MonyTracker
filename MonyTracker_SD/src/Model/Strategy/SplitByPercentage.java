package Model.Strategy;

import Model.Group;
import Model.Person;
import Model.Ticket;

import java.util.HashMap;
import java.util.Map;


// Split the total amount by percentages between the group
public class SplitByPercentage implements PayBehaviour {
    private final Map<Person, Float> percentages;
    private final float totalAmount;

    public SplitByPercentage(float totalAmount, Map<Person, Float> percentages) {
        this.percentages = percentages;
        this.totalAmount = totalAmount;
        // Checked if valid is done when the ticket is being Added
    }

    @Override
    public Map<Person, Float> pay() {
        Map<Person, Float> paymentsOwed = new HashMap<>();
        // Calculate payments owed by whom based on percentages
        for (Map.Entry<Person, Float> entry : percentages.entrySet()) {
            Person person = entry.getKey();
            float percentage = entry.getValue();
            float payment = totalAmount * (percentage/100);
            paymentsOwed.put(person, payment);
            System.out.println(person + "Needs to pay: €" + paymentsOwed.get(person));
        }
        return paymentsOwed;
    }

    @Override
    public String toString() {
        return "Split By Percentage";
    }
}
