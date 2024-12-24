package View.SwingFactory;

import Model.Ticket;
import Controller.Controller;
import View.AbstractViewFactory;
import View.SwingFactory.Panels.*;

import javax.swing.*;

public class SwingViewFactory implements AbstractViewFactory<JPanel> {
    // We implement the abstractFactory with JPanel as type
    private final SwingViewFrame viewFrame;
    private final Controller controller;

    // create the frame, on which we will create the panels
    public SwingViewFactory(Controller controller) {
        this.viewFrame = new SwingViewFrame(this);
        this.controller = controller;
        // only when swingViewFactory is initialised we can call viewFrame.init() because of circular dependency
        viewFrame.init();
    }

    @Override
    public JPanel createHomePage() {
        return new HomePanel(this.viewFrame);
    }

    @Override
    public JPanel createGroupPage(int groupID) {
        return new GroupPanel(this.viewFrame, this.controller, groupID);
    }

    @Override
    public JPanel createTicketPage(Ticket ticket, int groupID) {
        return new TicketPanel(this.viewFrame, ticket, groupID);
    }

    @Override
    public JPanel createAddGroupPage() {
        return new AddGroupPanel(this.viewFrame, this.controller);
    }

    @Override
    public JPanel createAddTicketPage(int groupID) {
        return new AddTicketPanel(this.viewFrame, this.controller, groupID);
    }
}
