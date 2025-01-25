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
}
