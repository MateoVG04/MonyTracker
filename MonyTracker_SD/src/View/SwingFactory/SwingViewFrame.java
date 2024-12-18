package View.SwingFactory;

import View.AbstractViewFactory;

import javax.swing.*;
import java.awt.*;

public class SwingViewFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final AbstractViewFactory<JPanel> viewFactory;

    public SwingViewFrame() {
        this.viewFactory = new SwingViewFactory(this);
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
        showHomePage();
        setVisible(true);
    }

    public void showHomePage() {
        cardLayout.show(cardPanel, "HOME");
    }

    public void showGroupPage(int groupID) {
        // Make a new groupPanel with this groupID
        JPanel groupPanel = viewFactory.createGroupPage(groupID);
        // Remove previous groupPanel if it exists
        remove(groupPanel);
        // add the new groupPanel and show it
        cardPanel.add(groupPanel, "GROUP");
        cardLayout.show(cardPanel, "GROUP");
    }

    public void showTicketPage(int ticketID, int groupID) {
        // Make a new ticketPanel with this ticketID
        JPanel ticketPanel = viewFactory.createTicketPage(ticketID, groupID);
        // Remove previous ticketPanel if it exists
        remove(ticketPanel);
        // add the new ticketPanel and show it
        cardPanel.add(ticketPanel, "TICKET");
        cardLayout.show(cardPanel, "TICKET");
    }

    public void addGroup() {

    }

    public void addTicket() {
        // Go to a new addTicket dialogue
    }

    public void update() {
        // update the whole view
    }
}
