package View.SwingFactory.Panels;

import Model.Person;
import Model.Ticket;
import View.SwingFactory.SwingViewFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// This panel will show when you click on a particular ticket in a group page
// There are some long functions which is not ideal, but it is functional
// We have solutions for this, but we had no time to add these
public class TicketPanel extends JPanel {
    private final SwingViewFrame viewFrame;
    private final Ticket ticket;
    private final int groupID;

    public TicketPanel(SwingViewFrame viewFrame, Ticket ticket, int groupID) {
        this.viewFrame = viewFrame;
        // we keep groupID, so we know which page to go back to
        this.groupID = groupID;
        this.ticket = ticket;
        // We use this BoxLayout, so all the SubPanels will come under each other
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // RGB colors: https://teaching.csse.uwa.edu.au/units/CITS1001/colorinfo.html
        setBackground(Color.BLUE);
        // add titlePanel to top of the Home Page
        add(getTitlePanel());
        // Panel to show tag of ticket and a description
        add(getSubTitlePanel());
        // Scroll panel with all the expenses in this ticket
        add(getExpensesPanel());
        // Back button
        add(getButtonPanel());
    }

    private JPanel getTitlePanel() {
        JPanel titlePanel = getNewVerticallyAndCenteredPanel();
        titlePanel.setBackground(Color.BLUE);
        // Title on top of the screen aligned in the center and BIG
        JLabel titleLabel = new JLabel("totalAmount €" + ticket.getTotalAmount(), SwingConstants.CENTER);
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
        JLabel tagLabel = new JLabel("Tag: " + ticket.getTag(), SwingConstants.CENTER);
        tagLabel.setForeground(Color.WHITE);
        JLabel descriptionLabel = new JLabel("Description: " + ticket.getDescription(), SwingConstants.CENTER);
        descriptionLabel.setForeground(Color.WHITE);
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
        Person payer = ticket.getPayer();
        Map<Person, Float> paymentsOwed = ticket.getPaymentsOwed();
        JLabel payerLabel = new JLabel(payer.getName() + " paid €" + ticket.getTotalAmount());
        expensesPanel.add(payerLabel);
        for (Map.Entry<Person, Float> entry : paymentsOwed.entrySet()){
            Person person = entry.getKey();
            float amount = entry.getValue();
            JLabel personWhoOwesLabel = new JLabel(person + " Owes: €" + amount, SwingConstants.CENTER);
            expensesPanel.add(personWhoOwesLabel);
        }
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
        backButton.addActionListener(e -> this.viewFrame.updateAndShowGroupPage(groupID));
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