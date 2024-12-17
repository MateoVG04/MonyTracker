package View;

import Model.Group;
import View.Panels.GroupPanel;
import View.Panels.HomePanel;
import View.Panels.TicketPanel;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public ViewFrame() {
        setTitle("Money Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Initialize main Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        // Initialize home panel
        HomePanel homePanel = new HomePanel(this);
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
        GroupPanel groupPanel = new GroupPanel(this, groupID);
        // Remove previous groupPanel if it exists
        remove(groupPanel);
        // add the new groupPanel and show it
        cardPanel.add(groupPanel, "GROUP");
        cardLayout.show(cardPanel, "GROUP");
    }

    public void showTicketPage(int ticketID, int groupID) {
        // Make a new ticketPanel with this ticketID
        TicketPanel ticketPanel = new TicketPanel(this, ticketID, groupID);
        // Remove previous ticketPanel if it exists
        remove(ticketPanel);
        // add the new ticketPanel and show it
        cardPanel.add(ticketPanel, "TICKET");
        cardLayout.show(cardPanel, "TICKET");
    }

    public void addGroup() {
        // Go to a new addGroup dialogue
    }

    public void addTicket() {
        // Go to a new addTicket dialogue
    }

    public static void main(String[] args) {
        new ViewFrame();
    }
}
