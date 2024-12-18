package Model;

import Model.Database.Entries.GroupEntry;
import Model.Database.GroupDB;

import java.util.ArrayList;

public class ModelApp {
    private GroupDB groupDatabase;

    public ModelApp(GroupDB groupDatabase) {
        this.groupDatabase = groupDatabase;
    }

    public void addGroup(Group group) {
        GroupEntry groupEntry = new GroupEntry(group);
        groupDatabase.addGroupEntry(groupEntry);
    }

    public void removeGroup(int groupID) {
        groupDatabase.removeGroupEntry(groupID);
    }

    public void addTicketToGroup(Ticket ticket, int groupID) {
        GroupEntry groupEntry = groupDatabase.getGroupEntry(groupID);
        if (groupEntry != null) {
            groupEntry.getGroup().addTicket(ticket);
        }
    }

    public void addPersonToGroup(Person person, int groupID) {
        GroupEntry groupEntry = groupDatabase.getGroupEntry(groupID);
        if (groupEntry != null) {
            groupEntry.getGroup().addPersonToGroup(person);
        }
    }
}
