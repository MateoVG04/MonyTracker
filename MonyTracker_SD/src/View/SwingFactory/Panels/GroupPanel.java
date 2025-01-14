package View.SwingFactory.Panels;

import Model.Database.GroupDB;
import Model.Group;
import Model.Person;
import Model.Ticket;
import Controller.Controller;
import View.SwingFactory.SwingViewFrame;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map;

public class GroupPanel extends JPanel implements PropertyChangeListener {
    private final SwingViewFrame viewFrame;
    private final Map<Person, Map<Person, Float>> transactions;
    private final Group group;
    private final Controller controller;
    private final int groupID;

    public GroupPanel(SwingViewFrame viewFrame, Controller controller, int groupID) {
        this.viewFrame = viewFrame;
        this.controller = controller;
        GroupDB groupDB = GroupDB.getInstance();
        this.groupID = groupID;
        this.group = groupDB.getGroupEntry(groupID).getGroup();
        // groupPanel gets recreated every time we add a ticket, so
        // it will calculate how much everyone owes every time a ticket gets created
        this.transactions = group.calculateTransactions();
        // We use this BoxLayout, so all the SubPanels will come under each other
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // RGB colors: https://teaching.csse.uwa.edu.au/units/CITS1001/colorinfo.html
        setBackground(Color.BLUE);
        // add titlePanel to top of the Home Page
        add(getTitlePanel());
        // Panel to show how much you are owed/you owe to whom
        add(getMoneyTotalPanel());
        // Scroll panel with all the Ticket of this group
        add(getTicketsPanel());
        // AddTicket button, back button and remove group button (in RED)
        add(getButtonsPanel());
    }

    private JPanel getTitlePanel() {
        JPanel titlePanel = getNewVerticallyAndCenteredPanel();
        titlePanel.setBackground(Color.BLUE);
        // Title on top of the screen aligned in the center and BIG
        JLabel titleLabel = new JLabel("Group: " + group.getGroupName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        titleLabel.setForeground(Color.white);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        https://fonts.google.com/specimen/Montserrat
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JScrollPane getMoneyTotalPanel() {
        JPanel moneyTotalPanel = getNewVerticallyAndCenteredPanel();
        moneyTotalPanel.setBackground(Color.BLUE);
        JLabel moneyTotalLabel;
        StringBuilder moneyTotal = new StringBuilder();
        for (Person person1 : transactions.keySet()) {
            for (Person person2 : transactions.get(person1).keySet()) {
                moneyTotal.append(person1).append(" owes ").append(person2).append(" €").append(transactions.get(person1).get(person2).toString()).append("\n");
            }
        }
        moneyTotalLabel = new JLabel(moneyTotal.toString());
        moneyTotalLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        moneyTotalLabel.setForeground(Color.white);
        moneyTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        moneyTotalPanel.add(moneyTotalLabel);
        JScrollPane scrollMoneyTotalPanel = new JScrollPane(moneyTotalPanel);
        scrollMoneyTotalPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollMoneyTotalPanel;
    }

    private JScrollPane getTicketsPanel() {
        JPanel ticketsPanel = getNewVerticallyAndCenteredPanel();
        ticketsPanel.setBackground(new Color(51, 204, 255));
        // Little whitespace before all the tickets
        ticketsPanel.add(Box.createVerticalStrut(20));
        // Add the tickets as buttons to the panel with their name
        ArrayList<Ticket> tickets = this.group.getTickets();
        for (Ticket ticket : tickets) {
            // Example: Mike paid €100 SplitEqually
            JButton button = new JButton(ticket.getPayer().getName() + " payed €" + ticket.getTotalAmount() + ticket.getPayBehaviour().toString());
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            // when button clicked it will show the right ticket page
            button.addActionListener(e -> this.viewFrame.updateAndShowTicketPage(ticket, groupID));
            ticketsPanel.add(button);
            ticketsPanel.add(Box.createVerticalStrut(20));
        }
        // Make it a scrollPane, so you can scroll endlessly
        JScrollPane scrollTicketsPanel = new JScrollPane(ticketsPanel);
        scrollTicketsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return scrollTicketsPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonPanel = getNewVerticallyAndCenteredPanel();
        buttonPanel.setBackground(Color.BLUE);
        JButton addTicketButton = new JButton("Add Ticket");
        addTicketButton.setPreferredSize(new Dimension(200, 30));
        addTicketButton.setMaximumSize(addTicketButton.getPreferredSize());
        addTicketButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // When button clicked go to addTicket dialogue
        addTicketButton.addActionListener(e -> this.viewFrame.updateAndShowAddTicketPage(groupID));
        buttonPanel.add(addTicketButton, BorderLayout.LINE_END);
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        backButton.setMaximumSize(backButton.getPreferredSize());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // when back button is clicked we go back to home page
        backButton.addActionListener(e -> this.viewFrame.updateAndShowHomePage());
        buttonPanel.add(backButton, BorderLayout.CENTER);
        JButton removeGroupButton = new JButton("Remove Group");
        removeGroupButton.setPreferredSize(new Dimension(200, 30));
        removeGroupButton.setMaximumSize(removeGroupButton.getPreferredSize());
        removeGroupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeGroupButton.setBackground(Color.RED);
        removeGroupButton.addActionListener(e -> removeGroup());
        buttonPanel.add(removeGroupButton, BorderLayout.LINE_START);
        return buttonPanel;
    }

    private void removeGroup() {
        controller.addPropertyChangeListener(this);
        // show a confirm dialog to make sure the user wants to delete this group
        // in the future we can add an undo button that shows when the user deleted a group
        int option = JOptionPane.showConfirmDialog(viewFrame, "Are you sure you want to delete this group? This can't be undone!", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            controller.removeGroup(group.getGroupID());
        }
        controller.removePropertyChangeListener(this);
        // If the user clicks no, we will do nothing and the group is not removed
    }

    private static JPanel getNewVerticallyAndCenteredPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("removedGroup")) {
            JOptionPane.showMessageDialog(viewFrame, "Group with ID " + evt.getNewValue() + " was removed successfully");
            viewFrame.updateAndShowHomePage();
        }
    }
}
