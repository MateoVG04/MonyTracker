package Controller;

import Model.Group;
import Model.ModelApp;
import Model.Person;
import Model.Strategy.PayBehaviour;
import Model.Strategy.SplitEqually;
import Model.Ticket;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Controller implements PropertyChangeListener {
    private final ModelApp model;
    private final PropertyChangeSupport support;

    public Controller(ModelApp model) {
        this.model = model;
        this.support = new PropertyChangeSupport(this);
        model.addPropertyChangeListener(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void addGroup(String groupName, ArrayList<String> personNames) {
        // if no groupName was provided, show error message and return to old state
        if (groupName.isEmpty()) {
            support.firePropertyChange("error", null, "Group name cannot be empty.");
            return;
        }
        // at least one personName should be provided otherwise -> error and return to old state
        ArrayList<String> validPersonNames = new ArrayList<>();
        for (String personName : personNames) {
            if (!personName.isEmpty()) {
                validPersonNames.add(personName);
            }
        }
        if (validPersonNames.isEmpty()) {
            support.firePropertyChange("error", null, "At least one person name must be provided.");
            return;
        }
        // Only if there was a valid groupName and at least one valid person we add the group to the model groupDB.
        Group group = new Group(groupName);
        for(String personName : validPersonNames) {
            group.addPersonToGroup(new Person(personName, ""));
        }
        model.addGroup(group);
    }

    public void removeGroup(int groupID) {
        model.removeGroup(groupID);
    }

    public void addTicketToGroup(Group group, float totalAmount, Person payer, String stringPayBehaviour, String tag, String description) {
        // Check if totalAmount is valid
        if (totalAmount <= 0) {
            support.firePropertyChange("error", null, "Total amount cannot be zero or negative.");
            return;
        }
        // We don't have to check for validity of Payer and StringPayBehaviour because user has to choose between the valid ones.
        // payer can only be one of the persons in the group and payBehaviour can only be one of the strategy.PayBehaviours.
        if (tag.isEmpty()) {
            tag = "Standard tag";
        }
        if (description == null) {
            description = "";
        }
        //if (stringPayBehaviour.equals("SplitEqually")) {
        PayBehaviour payBehaviour = new SplitEqually(totalAmount, group.getGroupMembers());
        //}
        Ticket ticket = new Ticket(totalAmount, payer, group, payBehaviour, tag, description);
        model.addTicketToGroup(ticket, group);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("addedGroup".equals(evt.getPropertyName())) {
            Group newGroup = (Group) evt.getNewValue();
            support.firePropertyChange("addedGroup", null, newGroup.getGroupName());
        }
        else if ("removedGroup".equals(evt.getPropertyName())) {
            int removedGroupID = (int) evt.getNewValue();
            support.firePropertyChange("removedGroup", null, removedGroupID);
        }
        else if ("addedTicket".equals(evt.getPropertyName())) {
            Ticket newTicket = (Ticket) evt.getNewValue();
            support.firePropertyChange("addedTicket", null, newTicket);
        }
    }
}
