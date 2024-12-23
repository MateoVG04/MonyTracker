package Model;

import Model.Strategy.PayBehaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Group {
    private String groupName;
    private ArrayList<Person> groupMembers;
    private ArrayList<Ticket> tickets;
    private final int groupID;
    private static int IDCounter = 0;
    // Each time person gets created, IDCounter will be incremented
    // And make it synchronized, so it's thread safe
    private static synchronized int generateID() {
        return IDCounter++;
    }
    // Map<Person1,Map<Person2,Float>> person 1 is iemand die een Float moet betalen aan Person2
    private Map<Person,Map<Person,Float>> transactions = new HashMap<>();

    public Group(String groupName) {
        this.groupName = groupName;
        this.groupMembers = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.groupID = generateID();
        for (Person person1 : groupMembers) {
            Map<Person,Float> owedList = new HashMap<>();
            for (Person person2 : groupMembers) {
                if (person1 != person2) {
                    owedList.put(person2,0.0f);
                }
            }
            transactions.put(person1,owedList);
        }
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public int getGroupID() {
        return groupID;
    }
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
    public void addPersonToGroup(Person person) {
        this.groupMembers.add(person);
    }
    public void removePersonFromGroup(Person person) {
        this.groupMembers.remove(person);
    }
    public ArrayList<Person> getGroupMembers() {
        return groupMembers;
    }
    public int getSize() {
        return groupMembers.size();
    }
    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
    public void createTicket(float totalAmount, Person payer, Group group, PayBehaviour payBehaviour, String tag, String description) {
        Ticket ticket = new Ticket(totalAmount, payer, group, payBehaviour, tag, description);
        addTicket(ticket);
    }
    public void removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
    }
    public Map<Person,Map<Person,Float>> calculateTransactions() {
        // wordt aangeroepen als de reis gedaan is en je op calculate transactions of group klikt
        // Er wordt een list gemaakt met in deze list dictionary (key1, value1) met de key1 de username of
        // userid en als value1 nog een dictionary (key2, value2) met als key2 de username of userid aan wie
        // de eerste user moet betalen en als value2 het totaal bedrag dat die user aan de 2e user (key2)
        // moet betalen
        // Map<wieBetaald,Map<wieOntvangt,bedragTeOntvangen>>
        for (Ticket ticket : tickets) {
            Map<Person,Float> innerMap = transactions.get(ticket.getPayer());
            for (Map.Entry<Person,Float> needsToPay : innerMap.entrySet()) {
                Map<Person,Float> payToPersonMap = new HashMap<>();
                payToPersonMap.put(ticket.getPayer(),ticket.getPaymentsOwed().get(needsToPay.getKey())+transactions.get(needsToPay.getKey()).get(ticket.getPayer()));
                transactions.put(needsToPay.getKey(),payToPersonMap);
            }
        }
        return transactions;
    }
}
