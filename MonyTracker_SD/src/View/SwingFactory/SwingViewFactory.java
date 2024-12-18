package View.SwingFactory;

import View.AbstractViewFactory;
import View.SwingFactory.Panels.GroupPanel;
import View.SwingFactory.Panels.HomePanel;
import View.SwingFactory.Panels.TicketPanel;

import javax.swing.*;

public class SwingViewFactory implements AbstractViewFactory<JPanel> {
    // We implement the abstractFactory with JPanel as type
    private final SwingViewFrame viewFrame;

    public SwingViewFactory(SwingViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    @Override
    public JPanel createHomePage() {
        return new HomePanel(viewFrame);
    }

    @Override
    public JPanel createGroupPage(int groupID) {
        return new GroupPanel(viewFrame, groupID);
    }

    @Override
    public JPanel createTicketPage(int ticketID, int groupID) {
        return new TicketPanel(viewFrame, ticketID, groupID);
    }
}
