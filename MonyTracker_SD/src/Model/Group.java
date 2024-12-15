package Model;

import Model.Strategy.PayBehaviour;
import Model.Strategy.SplitByPercentage;
import Model.Strategy.SplitEqually;

import java.beans.BeanDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Group {
    private String groupName;
    private int groupID;
    private ArrayList<Person> groupMembers;
    private ArrayList<Ticket> tickets;
    public Group(String groupName, String groupDescription, int groupID) {
        this.groupName = groupName;
        this.groupID = groupID;
        this.groupMembers = new ArrayList<>();
        this.tickets = new ArrayList<>();
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
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    public void addPersonGroup(Person person) {
        this.groupMembers.add(person);
    }
    public void removePersonGroup(Person person) {
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
    public Map<String,Map<String,Float>> calculateTransactions() {
        // wordt aangeroepen als de reis gedaan is en je op calculate transactions of group klikt

        // Er wordt een list gemaakt met in deze list dictionary (key1, value1) met de key1 de username of
        // userid en als value1 nog een dictionary (key2, value2) met als key2 de username of userid aan wie
        // de eerste user moet betalen en als value2 het totaal bedrag dat die user aan de 2e user (key2)
        // moet betalen
        // Map<wieBetaald,Map<wieOntvangt,bedragTeOntvangen>>
        Map<String,Map<String,Float>> transactions = new HashMap<>();
        for (Person person : groupMembers) {
            Map<String,ArrayList<Map<String,Float>>> transactionUser = new HashMap<>();
            if (transactions.get(person.getName()) == null) {
                transactions.put(person.getName(), new HashMap<>());
            }
            for (Ticket ticket : tickets) {
                if (ticket.getWieBetaald() == person.getName()){
                    float bedragTeBetalen = 0;
                    if (transactions.get(person.getName()).get(ticket.getWieOntvangt()) == null) {
                        // Hier moeten we nog zien wat de ticket.getPayBehaviour returnt... En op basis van deze
                        // return kan je dan de prijs die persoon1 aan persoon2 moet betalen
                        //
                        PayBehaviour payBehaviour = ticket.getPayBehaviour();
                        if (payBehaviour == SplitEqually){
                            bedragTeBetalen = ticket.getPrijs()/this.groupMembers.size();
                        } else if (payBehaviour == SplitByPercentage) {
                            // nog vinden welk percentage van wie is??
                            bedragTeBetalen = 1;
                        } else{
                            // moet duidelijk zijn van welk unequal deel van deze person is om te betalen
                            bedragTeBetalen = 2;
                        }
                        //
                        //
                        // OPGELET HIER BOVEN MOET NOG EEN STUKJE CODE KOMEN... ANDERES WERKT HET NIET
                        transactions.get(person.getName()).put(ticket.getWieOntvangt(), bedragTeBetalen);
                    }else {
                        bedragTeBetalen += transactions.get(person.getName()).get(ticket.getWieOntvangt());
                        transactions.get(person.getName()).put(ticket.getWieOntvangt(), bedragTeBetalen);
                    }
                }
            }

        }
        return transactions;
    }
}
