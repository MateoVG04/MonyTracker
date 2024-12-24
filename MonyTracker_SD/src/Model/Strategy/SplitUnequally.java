package Model.Strategy;

import Model.Person;

import java.util.HashMap;
import java.util.Map;

// Choose how much everyone has to pay
public class SplitUnequally implements PayBehaviour {
    private final float totalAmount;
    private final Map<Person, Float> personAmounts;

    public SplitUnequally(float totalAmount, Map<Person, Float> personAmounts) {
        this.totalAmount = totalAmount;
        this.personAmounts = personAmounts;
        // Checked if valid is done when the ticket is being Added
    }

    @Override
    public Map<Person, Float>  pay() {
        Map<Person, Float> paymentsOwed = new HashMap<>();
        for (Map.Entry<Person, Float> entry : personAmounts.entrySet()) {
            Person person = entry.getKey();
            float amount = entry.getValue();
            paymentsOwed.put(person, amount);
            System.out.println(person + "Needs to pay: €" + amount);
        }
        return paymentsOwed;
    }

    @Override
    public String toString() {
        return "Split Unequally";
    }
}