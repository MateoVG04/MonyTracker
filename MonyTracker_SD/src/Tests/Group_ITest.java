package Tests;

import Model.Group;
import Model.Person;
import Model.Strategy.PayBehaviour;
import Model.Strategy.SplitEqually;
import Model.Ticket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Group_ITest {
    private Group group;
    private Person person1;
    private Person person2;
    private Person person3;

    public Group_ITest() {
        // Intentionally left blank
    }

    @Before
    public void initialize() {
        group = new Group("testGroup");
        person1 = new Person("testPerson1","");
        person2 = new Person("testPerson2","");
        person3 = new Person("testPerson3","");
        group.addPersonToGroup(person1);
        group.addPersonToGroup(person2);
        group.addPersonToGroup(person3);

        // 1 ticket of €300 paid by person1 split equally -> person 2 owes 100 to person 1, person 3 owes 100 to person 1
        PayBehaviour payBehaviour1 = new SplitEqually(300,group.getGroupMembers());
        Ticket ticket1 = new Ticket(300,person1,group,payBehaviour1,"testTicket1","To test if getTicket() works correctly");
        // On top of first ticket, another ticket of €60 paid by person 2 split equally -> person 1 owes 20 to person 2
        //                                                                                 person 2 owes 100 to person 1
        //                                                                                 person 3 owes 100 to person 1
        //                                                                                 person 3 owes 20 to person 2
        PayBehaviour payBehaviour2 = new SplitEqually(60,group.getGroupMembers());
        Ticket ticket2 = new Ticket(60,person2,group,payBehaviour2,"testTicket2","To test if getTicket() works correctly as well");

        group.addTicket(ticket1);
        group.addTicket(ticket2);
    }



    @Test
    public void t_checkCalculateTransaction()
    {
        Map<Person, Map<Person,Float>> expectedTransactions = new HashMap<>();

        Map<Person, Float> person1Owes = new HashMap<>();
        person1Owes.put(person2, 20f); // Owes part of ticket2
        person1Owes.put(person3, 0.0f); // Owes part of ticket2
        expectedTransactions.put(person1, person1Owes);

        Map<Person, Float> person2Owes = new HashMap<>();
        person2Owes.put(person1, 100f); // Owes part of ticket1
        person2Owes.put(person3, 0.0f); // Owes part of ticket2
        expectedTransactions.put(person2, person2Owes);

        Map<Person, Float> person3Owes = new HashMap<>();
        person3Owes.put(person1, 100f); // Owes part of ticket1
        person3Owes.put(person2, 20f); // Owes part of ticket2
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
