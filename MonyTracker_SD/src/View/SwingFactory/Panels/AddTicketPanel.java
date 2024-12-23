package View.SwingFactory.Panels;

import Model.Group;
import Model.Person;
import View.SwingFactory.SwingViewFrame;
import Model.Database.GroupDB;
import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddTicketPanel extends JPanel implements PropertyChangeListener {
    private final SwingViewFrame viewFrame;
    private final Controller controller;
    private JComboBox<Person> payerComboBox;
    private JTextField totalAmountField;
    private JComboBox<String> payBehaviourComboBox;
    private JTextField tagField;
    private JTextField descriptionField;
    private final int groupID;
    private final Group group;

    public AddTicketPanel(SwingViewFrame viewFrame, Controller controller, int groupID) {
        this.viewFrame = viewFrame;
        this.controller = controller;
        controller.addPropertyChangeListener(this);
        this.groupID = groupID;
        this.group = GroupDB.getInstance().getGroupEntry(groupID).getGroup();
        // We use this BoxLayout, so all the SubPanels will come under each other
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // RGB colors: https://teaching.csse.uwa.edu.au/units/CITS1001/colorinfo.html
        setBackground(Color.BLUE);
        // Title panel
        JPanel titlePanel = getTitlePanel();
        add(titlePanel);
        add(getTotalAmountPanel());
        add(getPayerPanel());
        add(getPayBehaviourPanel());
        add(getTagPanel());
        add(getDescriptionPanel());
        add(getButtonsPanel());
    }

    private JPanel getTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setBackground(Color.BLUE);
        // Title on top of the screen aligned in the center and BIG
        JLabel title = new JLabel("Adding Ticket", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        return titlePanel;
    }

    private JPanel getTotalAmountPanel() {
        JPanel totalAmountPanel = new JPanel();
        totalAmountPanel.setLayout(new BoxLayout(totalAmountPanel, BoxLayout.X_AXIS));
        totalAmountPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalAmountPanel.setBackground(Color.BLUE);
        JLabel totalAmountLabel = new JLabel("Total Amount");
        totalAmountLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        totalAmountLabel.setForeground(Color.WHITE);
        totalAmountField = new JTextField(20);
        totalAmountField.setMaximumSize(new Dimension(200, 30));
        totalAmountField.setPreferredSize(getMaximumSize());
        totalAmountPanel.add(totalAmountLabel);
        totalAmountPanel.add(totalAmountField);
        return totalAmountPanel;
    }

    private JPanel getPayerPanel() {
        JPanel payerPanel = new JPanel();
        payerPanel.setLayout(new BoxLayout(payerPanel, BoxLayout.X_AXIS));
        payerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        payerPanel.setBackground(Color.BLUE);
        JLabel payerLabel = new JLabel("Paid by: ");
        payerLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        payerLabel.setForeground(Color.WHITE);
        payerComboBox = new JComboBox<>(group.getGroupMembers().toArray(new Person[0]));
        payerComboBox.setMaximumSize(new Dimension(200, 30));
        payerComboBox.setPreferredSize(getMaximumSize());
        payerPanel.add(payerLabel);
        payerPanel.add(payerComboBox);
        return payerPanel;
    }

    private JPanel getPayBehaviourPanel() {
        JPanel payBehaviourPanel = new JPanel();
        payBehaviourPanel.setLayout(new BoxLayout(payBehaviourPanel, BoxLayout.X_AXIS));
        payBehaviourPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        payBehaviourPanel.setBackground(Color.BLUE);
        JLabel payBehaviourLabel = new JLabel("Pay Behaviour:");
        payBehaviourLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        payBehaviourLabel.setForeground(Color.WHITE);
        String[] payBehaviours = {"Split Equally", "SplitByPercentage", "SplitUnequally"};
        payBehaviourComboBox = new JComboBox<>(payBehaviours);
        payBehaviourComboBox.setMaximumSize(new Dimension(200, 30));
        payBehaviourPanel.add(payBehaviourLabel);
        payBehaviourPanel.add(payBehaviourComboBox);
        return payBehaviourPanel;
    }

    private JPanel getTagPanel() {
        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.X_AXIS));
        tagPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tagPanel.setBackground(Color.BLUE);
        JLabel tagLabel = new JLabel("Tag:");
        tagLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        tagLabel.setForeground(Color.WHITE);
        tagField = new JTextField(20);
        tagField.setMaximumSize(new Dimension(200, 30));
        tagPanel.add(tagLabel);
        tagPanel.add(tagField);
        return tagPanel;
    }

    private JPanel getDescriptionPanel() {
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));
        descriptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionPanel.setBackground(Color.BLUE);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionField = new JTextField(20);
        descriptionField.setMaximumSize(new Dimension(200, 30));
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionField);
        return descriptionPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setBackground(Color.BLUE);
        // Cancel button to go back to the home Page
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> this.viewFrame.updateAndShowGroupPage(groupID));
        buttonsPanel.add(cancelButton);
        // Save group button will first validate the users, and the save the group to the database
        JButton saveTicketButton = new JButton("Save Ticket");
        saveTicketButton.addActionListener(e -> saveTicket());
        buttonsPanel.add(saveTicketButton);
        return buttonsPanel;
    }

    private void saveTicket() {
        float totalAmount = Float.parseFloat(totalAmountField.getText());
        Person payer = (Person) payerComboBox.getSelectedItem();
        String stringPayBehaviour = (String) payBehaviourComboBox.getSelectedItem();
        String tag = tagField.getText();
        String description = descriptionField.getText();
        controller.addTicketToGroup(group, totalAmount, payer, stringPayBehaviour, tag, description);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // When the controller fires error -> show error message
        if (evt.getPropertyName().equals("error")) {
            JOptionPane.showMessageDialog(viewFrame, evt.getNewValue());
        }
        // When the controller fires addedTicket -> ticket was added successfully to the group
        else if (evt.getPropertyName().equals("addedTicket")) {
            JOptionPane.showMessageDialog(viewFrame, "Ticket was added succesfully to group " + this.group.getGroupName());
            viewFrame.updateAndShowGroupPage(groupID);
        }
    }
}
