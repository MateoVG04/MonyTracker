package View.SwingFactory.Panels;

import Model.Group;
import View.SwingFactory.SwingViewFrame;

import javax.swing.*;
import java.awt.*;

public class GroupPanel extends JPanel {
    private SwingViewFrame viewFrame;
    private float moneyTotal = 100;
    private float personInDebt;
    private float personYouOwe;
    private Group group;
    private int groupID;

    public GroupPanel(SwingViewFrame viewFrame, int groupID) {
        this.viewFrame = viewFrame;
        this.groupID = groupID;
//this.group = GroupDB.get(groupID);
        // We use this BoxLayout, so all the SubPanels will come under each other
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // RGB colors: https://teaching.csse.uwa.edu.au/units/CITS1001/colorinfo.html
        setBackground(Color.BLUE);
        // Title panel
        JPanel titlePanel = getTitlePanel();
        // add titlePanel to top of the Home Page
        add(titlePanel);
        // Panel to show how much you are owed/you owe to whom
        JPanel moneyTotalPanel = getMoneyTotalPanel();
        // Add the moneyTotalPanel under the title
        add(moneyTotalPanel);
        // Scroll panel with all the Ticket of this group
        JScrollPane scrollTicketsPanel = getTicketsPanel();
        // Add the scrollPanel in the middle of the screen
        add(scrollTicketsPanel);
        // AddTicket button and back button
        JPanel buttonsPanel = getButtonsPanel();
        // Add the buttons at the bottom of the screen
        add(buttonsPanel);
    }

    private JPanel getTitlePanel() {
        JPanel titlePanel = getNewVerticallyAndCenteredPanel();
        titlePanel.setBackground(Color.BLUE);
        // Title on top of the screen aligned in the center and BIG
//JLabel titleLabel = new JLabel("Group" + this.group.getGroupName(), SwingConstants.CENTER);
        JLabel titleLabel = new JLabel("Group " + groupID, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        titleLabel.setForeground(Color.white);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        https://fonts.google.com/specimen/Montserrat
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel getMoneyTotalPanel() {
        JPanel moneyTotalPanel = getNewVerticallyAndCenteredPanel();
        moneyTotalPanel.setBackground(Color.BLUE);
        JLabel moneyTotalLabel;
        if (moneyTotal >= 0) {
            moneyTotalLabel = new JLabel("You are owed €" + moneyTotal + " from " + personInDebt, SwingConstants.CENTER);
        }
        else {
            moneyTotalLabel = new JLabel("You owe " + personYouOwe + " €" + moneyTotal, SwingConstants.CENTER);
        }
        moneyTotalLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        moneyTotalLabel.setForeground(Color.white);
        moneyTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        moneyTotalPanel.add(moneyTotalLabel);
        return moneyTotalPanel;
    }

    private JScrollPane getTicketsPanel() {
        JPanel ticketsPanel = getNewVerticallyAndCenteredPanel();
        ticketsPanel.setBackground(new Color(51, 204, 255));
        // Little whitespace before all the tickets
        ticketsPanel.add(Box.createVerticalStrut(20));
        // Add the tickets as buttons to the panel with their name
//ArrayList<Ticket> tickets = this.group.getTickets();
        for (int i = 0; i<100; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            final int finalI = i;
            // when button clicked it will show the right ticket page
            button.addActionListener(e -> this.viewFrame.showTicketPage(finalI, groupID));
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
        addTicketButton.setPreferredSize(new Dimension(160, 80));
        addTicketButton.setMaximumSize(addTicketButton.getPreferredSize());
        addTicketButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//addGroupButton.setBackground(Color.GREEN);
        // When button clicked go to addTicket dialogue
        addTicketButton.addActionListener(e -> this.viewFrame.addTicket());
        buttonPanel.add(addTicketButton, BorderLayout.LINE_END);
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(160, 80));
        backButton.setMaximumSize(backButton.getPreferredSize());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//addGroupButton.setBackground(Color.GREEN);
        // when back button is clicked we go back to home page
        backButton.addActionListener(e -> this.viewFrame.showHomePage());
        buttonPanel.add(backButton, BorderLayout.LINE_START);
        return buttonPanel;
    }

    private static JPanel getNewVerticallyAndCenteredPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }
}
