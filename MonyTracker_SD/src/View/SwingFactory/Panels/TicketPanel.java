package View.SwingFactory.Panels;

import Model.Person;
import Model.Ticket;
import View.SwingFactory.SwingViewFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class TicketPanel extends JPanel {
    private SwingViewFrame viewFrame;
    private Person payer;
    private Map<Person, Float> paymentsOwed;
    private Ticket ticket;
    private int ticketID;
    private int groupID;

    public TicketPanel(SwingViewFrame viewFrame, int ticketID, int groupID) {
        this.viewFrame = viewFrame;
        this.ticketID = ticketID;
        // we keep groupID, so we know which page to go back to
        this.groupID = groupID;
//this.ticket = TicketDB.get(ticketID);
        // We use this BoxLayout, so all the SubPanels will come under each other
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // RGB colors: https://teaching.csse.uwa.edu.au/units/CITS1001/colorinfo.html
        setBackground(Color.BLUE);
        // Title panel
        JPanel titlePanel = getTitlePanel();
        // add titlePanel to top of the Home Page
        add(titlePanel);
        // Panel to show tag of ticket and a description
        JPanel subTitlePanel = getSubTitlePanel();
        // Add the subTitlePanel under the title
        add(subTitlePanel);
        // Scroll panel with all the expenses in this ticket
        JScrollPane scrollExpensesPanel = getExpensesPanel();
        // Add the scrollPanel in the middle of the screen
        add(scrollExpensesPanel);
        // back button
        JPanel buttonPanel = getButtonPanel();
        // Add the button at the bottom of the screen
        add(buttonPanel);
    }

    private JPanel getTitlePanel() {
        JPanel titlePanel = getNewVerticallyAndCenteredPanel();
        titlePanel.setBackground(Color.BLUE);
        // Title on top of the screen aligned in the center and BIG
        JLabel titleLabel = new JLabel("Ticket " + ticketID, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        https://fonts.google.com/specimen/Montserrat
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel getSubTitlePanel() {
        JPanel subTitlePanel = getNewVerticallyAndCenteredPanel();
        subTitlePanel.setBackground(Color.BLUE);
        JLabel tagLabel = new JLabel("Tag: ", SwingConstants.CENTER);
        tagLabel.setForeground(Color.WHITE);
        JLabel descriptionLabel = new JLabel("Description: ", SwingConstants.CENTER);
        descriptionLabel.setForeground(Color.WHITE);
//JLabel tagLabel = new JLabel("Ticket kind: " + ticket.getTag(), SwingConstants.CENTER);
//JLabel descriptionLabel = new JLabel("Description: " + ticket.getDescription(), SwingConstants.CENTER);
        Font font = new Font("Montserrat", Font.BOLD, 18);
        tagLabel.setFont(font);
        descriptionLabel.setFont(font);
        tagLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitlePanel.add(tagLabel);
        subTitlePanel.add(descriptionLabel);
        return subTitlePanel;
    }

    private JScrollPane getExpensesPanel() {
        JPanel expensesPanel = getNewVerticallyAndCenteredPanel();
        expensesPanel.setBackground(new Color(51, 204, 255));
        // Little whitespace before all the expenses
        expensesPanel.add(Box.createVerticalStrut(20));
        // Add the tickets as buttons to the panel with their name

//payer = ticket.getPayer();
//paymentsOwed = ticket.getPaymentsOwed();
//JLabel payerLabel = new JLabel(payer.getName() + " paid €" + ticket.getTotalAmount());
        JLabel payerLabel = new JLabel("Payer: ", SwingConstants.CENTER);
        expensesPanel.add(payerLabel);
        for (int i = 0; i<5; i++){
            JLabel personWhoOwesLabel = new JLabel("Person Owes: " + i, SwingConstants.CENTER);
            expensesPanel.add(personWhoOwesLabel);
        }
//for (Person person : paymentsOwed.keySet()) {
//    JLabel personWhoOwesLabel = new JLabel(person.getName() + " owes €" + paymentsOwed.get(person));
//    expensesPanel.add(personWhoOwesLabel);
//}
        // Make it a scrollPane, so you can scroll endlessly
        JScrollPane scrollExpensesPanel = new JScrollPane(expensesPanel);
        scrollExpensesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return scrollExpensesPanel;
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(160, 80));
        backButton.setMaximumSize(backButton.getPreferredSize());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // when button is clicked we go back to GroupPanel
        backButton.addActionListener(e -> this.viewFrame.showGroupPage(groupID));
        buttonPanel.add(backButton);
        return buttonPanel;
    }

    private static JPanel getNewVerticallyAndCenteredPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }
}