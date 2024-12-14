package Model.Strategy;

import Model.Group;
import Model.Person;

// Split the total amount equally between the group
public class SplitEqually implements PayBehaviour {
    private Group group;
    private double totalAmount;

    public SplitEqually(Group group, double totalAmount) {
        this.group = group;
        this.totalAmount = totalAmount;
    }

    @Override
    public void pay() {
        double payment = totalAmount/group.getSize();
        for (Person person : group.getGroupMembers()) {
            System.out.println(person + "Needs to pay: €" + payment);
        }
    }
}
