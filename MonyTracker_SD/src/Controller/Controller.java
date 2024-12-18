package Controller;

import Model.Group;
import Model.ModelApp;
import Model.Person;
import Model.Ticket;
import View.SwingFactory.SwingViewFrame;

public class Controller {
    private ModelApp model;
    private SwingViewFrame view;

    public Controller(ModelApp model, SwingViewFrame view) {
        this.model = model;
        this.view = view;
    }

    public void addGroup(Group group) {
        model.addGroup(group);
        view.update();
    }

    public void removeGroup(int groupID) {
        model.removeGroup(groupID);
    }

    public void addTicketToGroup(Ticket ticket, int groupID) {
        model.addTicketToGroup(ticket, groupID);
    }

    public void addPersonToGroup(Person person, int groupID) {
        model.addPersonToGroup(person, groupID);
    }
}
