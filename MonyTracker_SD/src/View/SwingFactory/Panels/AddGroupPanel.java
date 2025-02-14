package View.SwingFactory.Panels;

import Controller.Controller;
import Model.Person;
import View.SwingFactory.SwingViewFrame;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

// This panel will show when you click add Group
// There are some long functions which is not ideal, but it is functional
// We have solutions for this, but we had no time to add these
public class AddGroupPanel extends JPanel implements PropertyChangeListener {
    private final SwingViewFrame viewFrame;
    private final Controller controller;
    private JTextField groupNameField;
    private final ArrayList<JTextField> personNameFields;
    private final ArrayList<JTextField> personEmailFields;
    private JPanel personPanel;

    public AddGroupPanel(SwingViewFrame viewFrame, Controller controller) {
        this.viewFrame = viewFrame;
        this.controller = controller;
        personNameFields = new ArrayList<>();
        personEmailFields = new ArrayList<>();
        // We use this BoxLayout, so all the SubPanels will come under each other
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // RGB colors: https://teaching.csse.uwa.edu.au/units/CITS1001/colorinfo.html
        setBackground(Color.BLUE);
        // Add all the subPanels to this panel
        add(getTitlePanel());
        // Group name input panel
        add(getGroupNamePanel());
        // Person name input panel
        personPanel = getPersonPanel();
        // Scroll panel so you can scroll between all the members you want to add
        JScrollPane personNameScroller = new JScrollPane(personPanel);
        personNameScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(personNameScroller);
        // Buttons panel
        add(getButtonsPanel());
    }

    private JPanel getTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setBackground(Color.BLUE);
        // Title on top of the screen aligned in the center and BIG
        JLabel title = new JLabel("Adding Group", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        return titlePanel;
    }

    // Input groupName
    private JPanel getGroupNamePanel() {
        JPanel groupNamePanel = new JPanel();
        groupNamePanel.setLayout(new BoxLayout(groupNamePanel, BoxLayout.X_AXIS));
        groupNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        groupNamePanel.setBackground(Color.BLUE);
        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        groupNameLabel.setForeground(Color.WHITE);
        groupNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        groupNameField = new JTextField(20);
        groupNameField.setMaximumSize(new Dimension(200, 30));
        groupNamePanel.add(groupNameLabel);
        groupNamePanel.add(groupNameField);
        return groupNamePanel;
    }

    // This will add the first PersonName input
    private JPanel getPersonPanel() {
        personPanel = new JPanel();
        personPanel.setLayout(new BoxLayout(personPanel, BoxLayout.Y_AXIS));
        personPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        personPanel.setBackground(Color.BLUE);
        addPersonField();
        return personPanel;
    }

    // Adds a new input for a personName
    private void addPersonField() {
        personPanel.add(Box.createVerticalStrut(10));
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowPanel.setBackground(Color.BLUE);
        // Person Name Input
        JLabel personNameLabel = new JLabel("Person Name:");
        personNameLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        personNameLabel.setForeground(Color.WHITE);
        personNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField personNameField = new JTextField(20);
        personNameField.setMaximumSize(new Dimension(200, 30));
        personNameField.setPreferredSize(new Dimension(200, 30));
        personNameFields.add(personNameField);
        // Person Email Input
        JLabel personEmailLabel = new JLabel("Email:");
        personEmailLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        personEmailLabel.setForeground(Color.WHITE);
        personEmailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField personEmailField = new JTextField(20);
        personEmailField.setMaximumSize(new Dimension(200, 30));
        personEmailField.setPreferredSize(new Dimension(200, 30));
        personEmailFields.add(personEmailField);
        rowPanel.add(personNameLabel);
        rowPanel.add(personNameField);
        rowPanel.add(personEmailLabel);
        rowPanel.add(personEmailField);
        personPanel.add(rowPanel);
        revalidate();
        repaint();
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setBackground(Color.BLUE);
        // Cancel button to go back to the home Page
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> this.viewFrame.updateAndShowHomePage());
        buttonsPanel.add(cancelButton);
        // Add person button will add an extra personName input field
        JButton addPersonButton = new JButton("+ Add Person");
        addPersonButton.addActionListener(e -> addPersonField());
        buttonsPanel.add(addPersonButton);
        // Save group button will first validate the users, and the save the group to the database
        JButton saveGroupButton = new JButton("Save Group");
        saveGroupButton.addActionListener(e -> saveGroup());
        buttonsPanel.add(saveGroupButton);
        return buttonsPanel;
    }

    private void saveGroup() {
        // The view observes the controller
        controller.addPropertyChangeListener(this);
        String groupName = groupNameField.getText();
        ArrayList<Person> persons = new ArrayList<>();
        for (int i = 0; i < personNameFields.size(); i++) {
            String personName = personNameFields.get(i).getText();
            String personEmail = personEmailFields.get(i).getText();
            persons.add(new Person(personName, personEmail));
        }
        controller.addGroup(groupName, persons);
        controller.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // When the controller fires error -> show error message
        if (evt.getPropertyName().equals("error")) {
            JOptionPane.showMessageDialog(viewFrame, evt.getNewValue());
        }
        // When the controller fires addedGroup -> group was added successfully to the model
        else if (evt.getPropertyName().equals("addedGroup")) {
            JOptionPane.showMessageDialog(viewFrame, "Group " + evt.getNewValue() + " was added successfully");
            viewFrame.updateAndShowHomePage();
        }
    }
}
