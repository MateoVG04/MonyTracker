package Controller;

import Model.Database.Entries.GroupEntry;
import Model.Group;
import Model.ModelApp;
import Model.Person;
import Model.Strategy.PayBehaviour;
import Model.Strategy.SplitByPercentage;
import Model.Strategy.SplitEqually;
import Model.Strategy.SplitUnequally;
import Model.Ticket;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Map;

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

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
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

    public void addTicketToGroup(Group group, float totalAmount, Person payer, String stringPayBehaviour, String tag, String description, Map<Person, Float> personAmounts) {
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
        PayBehaviour payBehaviour;
        if (stringPayBehaviour.equals("Split Unequally")) {
            // Check if the totalAmount equals the total amount inputs
            float checkTotalAmount = 0;
            for (float amount : personAmounts.values()) {
                checkTotalAmount += amount;
            }
            float difference = checkTotalAmount - totalAmount;
            if (difference > 0.01) {
                support.firePropertyChange("error", null, "Total inputted amounts should be equal to the total amount.");
                return;
            }
            payBehaviour = new SplitUnequally(totalAmount, personAmounts);
        }
        else if (stringPayBehaviour.equals("Split By Percentage")) {
            // Check if the total percentages equal 100%
            float checkPercentages = 0;
            for (Float percentage : personAmounts.values()) {
                checkPercentages += percentage;
            }
            float difference = checkPercentages - 100;
            if (difference > 0.01) {
                support.firePropertyChange("error", null, "Total inputted percentages should be equal to 100%.");
                return;
            }
            payBehaviour = new SplitByPercentage(totalAmount, personAmounts);
        }
        else {
            payBehaviour = new SplitEqually(totalAmount, group.getGroupMembers());
        }
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
