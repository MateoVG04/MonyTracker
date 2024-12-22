package View.SwingFactory.Panels;

import Model.Database.Entries.GroupEntry;
import Model.Group;
import Model.Person;
import View.SwingFactory.SwingViewFrame;
import Model.Database.GroupDB;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddGroupPanel extends JPanel {
    private final SwingViewFrame viewFrame;
    private JTextField groupNameField;
    private ArrayList<JTextField> personNameFields;
    private JPanel personNamePanel;
    private JScrollPane personNameScroller;
    private GroupDB groupDB;

    public AddGroupPanel(SwingViewFrame viewFrame) {
        this.viewFrame = viewFrame;
        this.groupDB = GroupDB.getInstance();
        // userDB
        personNameFields = new ArrayList<>();
        // We use this BoxLayout, so all the SubPanels will come under each other
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // RGB colors: https://teaching.csse.uwa.edu.au/units/CITS1001/colorinfo.html
        setBackground(Color.BLUE);
        // Title panel
        JPanel titlePanel = getTitlePanel();
        add(titlePanel);
        // Group name input panel
        JPanel groupNamePanel = getGroupNamePanel();
        add(groupNamePanel);
        // Person name input panel
        personNamePanel = getPersonNamePanel();
        // Very Light Blue
        personNamePanel.setBackground(new Color(51, 204, 255));
        personNameScroller = new JScrollPane(personNamePanel);
        personNameScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(personNameScroller);
        // Buttons panel
        JPanel buttonsPanel = getButtonsPanel();
        add(buttonsPanel);
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
    private JPanel getPersonNamePanel() {
        personNamePanel = new JPanel();
        personNamePanel.setLayout(new BoxLayout(personNamePanel, BoxLayout.Y_AXIS));
        personNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        personNamePanel.setBackground(Color.BLUE);
        addPersonNameField();
        return personNamePanel;
    }

    // Adds a new input for a personName
    private void addPersonNameField() {
        JLabel personNameLabel = new JLabel("Person Name:");
        personNameLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        personNameLabel.setForeground(Color.WHITE);
        personNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField personNameField = new JTextField(20);
        personNameField.setMaximumSize(new Dimension(200, 30));
        personNameField.setPreferredSize(new Dimension(200, 30));
        personNameFields.add(personNameField);
        personNamePanel.add(personNameLabel);
        personNamePanel.add(personNameField);
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
        addPersonButton.addActionListener(e -> addPersonNameField());
        buttonsPanel.add(addPersonButton);
        // Save group button will first validate the users, and the save the group to the database
        JButton saveGroupButton = new JButton("Save Group");
        saveGroupButton.addActionListener(e -> saveGroup());
        buttonsPanel.add(saveGroupButton);
        return buttonsPanel;
    }

    private void saveGroup() {
        String groupName = groupNameField.getText();
        if (groupName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a group name");
            return;
        }
        Group group = new Group(groupName);
        ArrayList<String> persons = new ArrayList<>();
        for (JTextField personNameField : personNameFields) {
            String personName = personNameField.getText();
            if (!personName.isEmpty()) {
                // eerst nog checken of de person in onze database is
                // if (personName in personDB) {
                    // persons.add(personName)
                    // PersonEntry personEntry = personDB.getPersonEntry(groupName)
                    // Person person = personEntry.getPerson()
                    // group.addPersonToGroup(person)
                persons.add(personName);
                group.addPersonToGroup(new Person(personName, ""));
            }
        }
        if (persons.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter at least one valid person");
            return;
        }
        // Logic to save the group to our database
        groupDB.addGroupEntry(new GroupEntry(group));
        System.out.println("saved group" + groupName);
        viewFrame.updateAndShowHomePage();
    }
}
