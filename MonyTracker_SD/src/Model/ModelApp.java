package Model;

import Model.Database.Entries.GroupEntry;
import Model.Database.GroupDB;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelApp {
    private final PropertyChangeSupport support;

    public ModelApp() {
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void addGroup(Group group) {
        GroupEntry groupEntry = new GroupEntry(group);
        GroupDB.getInstance().addGroupEntry(groupEntry);
        support.firePropertyChange("addedGroup", null, group);
    }

    public void removeGroup(int groupID) {
        GroupDB.getInstance().removeGroupEntry(groupID);
        support.firePropertyChange("removedGroup", null, groupID);
    }

    public void removePersonFromGroup(int groupID, Person person) {
        Group group = GroupDB.getInstance().getGroupEntry(groupID).getGroup();
        group.removePersonFromGroup(person);
        support.firePropertyChange("removedPerson", null, person);
    }

    public void addTicketToGroup(Ticket ticket, Group group) {
         group.addTicket(ticket);
         support.firePropertyChange("addedTicket", null, ticket);
    }
}
