package View.SwingFactory;

import Model.Ticket;
import View.AbstractViewFactory;

import javax.swing.*;
import java.awt.*;

public class SwingViewFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private final AbstractViewFactory<JPanel> viewFactory;

    public SwingViewFrame(SwingViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    // Can't do this in constructor, because of circular dependency -> viewFactory and viewFrame.
    // viewFactory isn't fully initialised yet, so using it causes a null error later on.
    public void init() {
        setTitle("Money Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Initialize main Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        // Initialize home panel
        JPanel homePanel = viewFactory.createHomePage();
        // Add home panel to the card panel,
        // so we can switch between them by doing cardlayout.show(cardPanel, "PanelName");
        cardPanel.add(homePanel, "HOME");
        add(cardPanel);
        updateAndShowHomePage();
        setVisible(true);
    }

    public void updateAndShowHomePage() {
        // Make a new homePanel so everything is refreshed
        JPanel homePanel = viewFactory.createHomePage();
        // Remove previous homePanel if it exists
        remove(homePanel);
        // Add the new homePanel and show it
        cardPanel.add(homePanel, "HOME");
        cardLayout.show(cardPanel, "HOME");
    }

    public void updateAndShowGroupPage(int groupID) {
        // Make a new groupPanel with this groupID
        JPanel groupPanel = viewFactory.createGroupPage(groupID);
        // Remove previous groupPanel if it exists
        remove(groupPanel);
        // add the new groupPanel and show it
        cardPanel.add(groupPanel, "GROUP");
        cardLayout.show(cardPanel, "GROUP");
    }

    public void updateAndShowTicketPage(Ticket ticket, int groupID) {
        // Make a new ticketPanel with this ticketID
        JPanel ticketPanel = viewFactory.createTicketPage(ticket, groupID);
        // Remove previous ticketPanel if it exists
        remove(ticketPanel);
        // add the new ticketPanel and show it
        cardPanel.add(ticketPanel, "TICKET");
        cardLayout.show(cardPanel, "TICKET");

    }

    public void updateAndShowAddGroupPage() {
        JPanel addGroupPanel = viewFactory.createAddGroupPage();
        // Remove previous addGroupPanel if it exists
        remove(addGroupPanel);
        // add the new addGroupPanel and show it
        cardPanel.add(addGroupPanel, "ADD GROUP");
        cardLayout.show(cardPanel, "ADD GROUP");

    }

    // groupID so you know which group to go back to
    public void updateAndShowAddTicketPage(int groupID) {
        JPanel addTicketPanel = viewFactory.createAddTicketPage(groupID);
        // Remove previous addTicketPanel if it exists
        remove(addTicketPanel);
        // add the new addTicketPanel and show it
        cardPanel.add(addTicketPanel, "ADD TICKET");
        cardLayout.show(cardPanel, "ADD TICKET");

    }

    public void update() {
        // update the whole view
    }
}
