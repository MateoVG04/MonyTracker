package Model.Strategy;

import Model.Group;
import Model.Person;

import java.util.Map;

// Split the total amount by percentages between the group
public class SplitByPercentage implements PayBehaviour {
    private Group group;
    private Map<Person, Double> percentages;
    private double totalAmount;

    public SplitByPercentage(Group group, Map<Person, Double> percentages, double totalAmount) {
        this.group = group;
        this.percentages = percentages;
        this.totalAmount = totalAmount;
    }

    @Override
    public void pay() {
        for (Person person : group.getGroupMembers()) {
            double percentage = percentages.get(person);
            double payment = totalAmount * (percentage/100);
            System.out.println(person + "Needs to pay: €" + payment);
        }
    }
}
