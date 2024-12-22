package View.SwingFactory;

import Model.Ticket;
import View.AbstractViewFactory;
import View.SwingFactory.Panels.*;

import javax.swing.*;

public class SwingViewFactory implements AbstractViewFactory<JPanel> {
    // We implement the abstractFactory with JPanel as type
    private final SwingViewFrame viewFrame;

    // create the frame, on which we will create the panels
    public SwingViewFactory() {
        this.viewFrame = new SwingViewFrame(this);
    }

    public SwingViewFrame getViewFrame() {
        return this.viewFrame;
    }

    @Override
    public JPanel createHomePage() {
        return new HomePanel(this.viewFrame);
    }

    @Override
    public JPanel createGroupPage(int groupID) {
        return new GroupPanel(this.viewFrame, groupID);
    }

    @Override
    public JPanel createTicketPage(Ticket ticket, int groupID) {
        return new TicketPanel(this.viewFrame, ticket, groupID);
    }

    @Override
    public JPanel createAddGroupPage() {
        return new AddGroupPanel(this.viewFrame);
    }

    @Override
    public JPanel createAddTicketPage(int groupID) {
        return new AddTicketPanel(this.viewFrame, groupID);
    }
}
