package Controller;

import Model.Group;
import Model.Person;
import Model.Ticket;
import View.ViewFrame;

import java.util.ArrayList;

// Separates the Model and the View

public class Controller {
    private ArrayList<Group> model;
    private ViewFrame view;

    public Controller(ViewFrame view) {
        this.view = view;
    }

    public void addGroup(Group group) {
    }

    public void addPerson(Person person) {
    }

    public void addTicket(Ticket ticket) {
    }

}
