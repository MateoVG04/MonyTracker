package Model;

import java.util.*;

public class Group {
    private final String groupName;
    private final ArrayList<Person> groupMembers;
    private final ArrayList<Ticket> tickets;
    private final int groupID;
    private static int IDCounter = 0;
    // Map<Person1,Map<Person2,Float>> person 1 is iemand die een Float moet betalen aan Person2
    private final Map<Person,Map<Person,Float>> transactions;
    // Each time person gets created, IDCounter will be incremented
    // And make it synchronized, so it's thread safe
    private static synchronized int generateID() {
        return IDCounter++;
    }

    public Group(String groupName) {
        this.groupName = groupName;
        this.groupMembers = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.groupID = generateID();
        this.transactions = new HashMap<>();
    }

    /* Dit was in constructor maar snap niet wat dit doet.
    for (Person person1 : groupMembers) {
            Map<Person,Float> owedList = new HashMap<>();
            for (Person person2 : groupMembers) {
                if (person1 != person2) {
                    owedList.put(person2,0.0f);
                }
            }
            transactions.put(person1,owedList);
        }*/

    public String getGroupName() {
        return groupName;
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
    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public Map<Person,Map<Person,Float>> calculateTransactions() {
        transactions.clear();

        for (Person person : groupMembers) {
            transactions.put(person, new HashMap<>());
            for (Person otherPerson : groupMembers) {
                if (!person.equals(otherPerson)) {
                    transactions.get(person).put(otherPerson, 0.0f);
                }
            }
        }

        for (Ticket ticket : tickets) {
            Person payer = ticket.getPayer();
            Map<Person,Float> paymentsOwed = ticket.getPaymentsOwed();

            for (Map.Entry<Person,Float> entry : paymentsOwed.entrySet()) {
                Person debtor = entry.getKey();
                Float amountOwed = entry.getValue();

                if (transactions.containsKey(debtor) && transactions.get(debtor).containsKey(payer)) {
                    float updateAmount = transactions.get(debtor).get(payer) + amountOwed;
                    transactions.get(debtor).put(payer, updateAmount);
                }
            }
        }
        System.out.println(transactions);
        return transactions;
    }

    public Map<Person,Map<Person,Float>> finalizeTransactions() {
        Map<Person, Float> netBalances = new HashMap<>();

        for (Map.Entry<Person, Map<Person, Float>> entry : transactions.entrySet()) {
            Person person = entry.getKey();
            netBalances.put(person, 0.0f);

            for (Map.Entry<Person, Float> subEntry : entry.getValue().entrySet()) {
                Person otherPerson = subEntry.getKey();
                Float amount = subEntry.getValue();
                netBalances.put(person, netBalances.get(person) - amount);
                netBalances.put(otherPerson, netBalances.getOrDefault(otherPerson, 0.0f) + amount);
            }
        }
        PriorityQueue<Map.Entry<Person, Float>> creditors = new PriorityQueue<>((a, b) -> Float.compare(b.getValue(), a.getValue()));
        PriorityQueue<Map.Entry<Person, Float>> debtors = new PriorityQueue<>((a, b) -> Float.compare(a.getValue(), b.getValue()));

        for (Map.Entry<Person, Float> entry : netBalances.entrySet()) {
            if (entry.getValue() > 0) {
                creditors.add(entry);
            } else if (entry.getValue() < 0) {
                debtors.add(entry);
            }
        }
        Map<Person, Map<Person, Float>> finalTransactions = new HashMap<>();

        while (!creditors.isEmpty() && !debtors.isEmpty()) {
            Map.Entry<Person, Float> creditor = creditors.poll();
            Map.Entry<Person, Float> debtor = debtors.poll();
            float credit = creditor.getValue();
            float debt = -debtor.getValue();
            float settledAmount = Math.min(credit, debt);
            finalTransactions.putIfAbsent(debtor.getKey(), new HashMap<>());
            finalTransactions.get(debtor.getKey()).put(creditor.getKey(), settledAmount);
            credit -= settledAmount;
            debt -= settledAmount;

            if (credit > 0) {
                creditors.add(new AbstractMap.SimpleEntry<>(creditor.getKey(), credit));
            }

            if (debt > 0) {
                debtors.add(new AbstractMap.SimpleEntry<>(debtor.getKey(), -debt));
            }
        }
        System.out.println("final: "+finalTransactions);
        return finalTransactions;
    }
}

