package Model.Strategy;

import Model.Group;
import Model.Person;

import java.util.Map;

// Choose how much everyone has to pay
public class SplitUnequally implements PayBehaviour {
    private Group group;
    private Map<Person, Double> personAmounts;

    public SplitUnequally(Group group, Map<Person, Double> personAmounts) {
        this.group = group;
        this.personAmounts = personAmounts;
    }

    @Override
    public void pay() {
        for (Person person : group.getGroupMembers()) {
            double payment = personAmounts.get(person);
            if (payment > 0) {
                System.out.println(person + "Needs to pay: €" + payment);
            }
        }
    }
}
