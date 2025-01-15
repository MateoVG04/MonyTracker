package Tests;
import Model.Group;
import Model.Person;
import Model.Strategy.PayBehaviour;
import Model.Strategy.SplitEqually;
import Model.Ticket;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Group_UTest {
    public Group_UTest()
    {
        // Intentionally left blank
    }

    @Before
    public void initialize()
    {

    }

    @Test
    public void t_checkGroupName()
    {
        Group group = new Group("testGroup");
        Assert.assertEquals("Testing the group name","testGroup", group.getGroupName());
    }

    @Test
    public void t_checkTickets()
    {

        Group group = new Group("testGroup");
        Person person1 = new Person("testPerson1","");
        Person person2 = new Person("testPerson2","");
        Person person3 = new Person("testPerson3","");
        group.addPersonToGroup(person1);
        group.addPersonToGroup(person2);
        group.addPersonToGroup(person3);

        PayBehaviour payBehaviour = new SplitEqually(200,group.getGroupMembers());
        Ticket ticket1 = new Ticket(200,person1,group,payBehaviour,"testTicket","To test if getTicket() works correctly");
        Ticket ticket2 = new Ticket(50,person2,group,payBehaviour,"testTicket","To test if getTicket() works correctly");

        group.addTicket(ticket1);
        group.addTicket(ticket2);

        ArrayList<Ticket> ticketArrayList = new ArrayList<>();
        ticketArrayList.add(ticket1);
        ticketArrayList.add(ticket2);

        Assert.assertEquals("check ticket tag","To test if getTicket() works correctly",ticket1.getTag());
        Assert.assertEquals("check to see the cost of the ticket",200,ticket1.getTotalAmount());
        Assert.assertEquals("check tickets in group",ticketArrayList,group.getTickets());
    }

    @Test
    public void t_checkCalucateTransaction()
    {
        Group group = new Group("testGroup");
        Person person1 = new Person("testPerson1","");
        Person person2 = new Person("testPerson2","");
        Person person3 = new Person("testPerson3","");
        group.addPersonToGroup(person1);
        group.addPersonToGroup(person2);
        group.addPersonToGroup(person3);

        PayBehaviour payBehaviour1 = new SplitEqually(200,group.getGroupMembers());
        Ticket ticket1 = new Ticket(200,person1,group,payBehaviour1,"testTicket1","To test if getTicket() works correctly");
        PayBehaviour payBehaviour2 = new SplitEqually(50,group.getGroupMembers());

        Ticket ticket2 = new Ticket(50,person2,group,payBehaviour2,"testTicket2","To test if getTicket() works correctly also");

        group.addTicket(ticket1);
        group.addTicket(ticket2);
        group.calculateTransactions();

        Map<Person, Map<Person,Float>> expectedTransactions = new HashMap<>();

        Map<Person, Float> person1Owes = new HashMap<>();
        person1Owes.put(person2, 16.666666f); // Owes part of ticket2
        person1Owes.put(person3, 0.0f); // Owes part of ticket2
        expectedTransactions.put(person1, person1Owes);

        Map<Person, Float> person2Owes = new HashMap<>();
        person2Owes.put(person1, 66.666664f); // Owes part of ticket1
        person2Owes.put(person3, 0.0f); // Owes part of ticket2
        expectedTransactions.put(person2, person2Owes);

        Map<Person, Float> person3Owes = new HashMap<>();
        person3Owes.put(person1, 66.666664f); // Owes part of ticket1
        person3Owes.put(person2, 16.666666f); // Owes part of ticket2
        expectedTransactions.put(person3, person3Owes);

        Map<Person, Map<Person, Float>> actualTransactions = group.calculateTransactions();

        for (Person payer : group.getGroupMembers()) {
            Map<Person, Float> expectedOwes = expectedTransactions.get(payer);
            Map<Person, Float> actualOwes = actualTransactions.get(payer);

            Assert.assertNotNull("Actual transactions should contain " + payer.getName(), actualOwes);
            Assert.assertEquals("Mismatch in transaction size for " + payer.getName(), expectedOwes.size(), actualOwes.size());

            for (Person payee : group.getGroupMembers()) {
                if (expectedOwes.containsKey(payee) && actualOwes.containsKey(payee)) {
                    Float expectedValue = expectedOwes.get(payee);
                    Float actualValue = actualOwes.get(payee);

                    Assert.assertEquals(
                            "Mismatch for " + payer.getName() + " -> " + payee.getName(),
                            expectedValue,
                            actualValue,
                            0.0001f // Tolerance for floating-point comparison
                    );
                } else if (expectedOwes.containsKey(payee)) {
                    // this is wrong if it comes into this conditional statement -> that's why I made the assert
                    // statement always return an error
                    Assert.assertEquals("payer was not found in actual transactions",4,1);
                }else if (actualOwes.containsKey(payee)) {
                    // this is wrong if it comes into this conditional statement -> that's why I made the assert
                    // statement always return an error
                    Assert.assertEquals("payer was not found in expected transactions",4,1);
                }
            }
        }
    }
}
