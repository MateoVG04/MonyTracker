package View.SwingFactory.Panels;

import Model.Database.Entries.GroupEntry;
import Model.Database.GroupDB;
import Model.Group;
import View.SwingFactory.SwingViewFrame;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private final SwingViewFrame viewFrame;
    private final GroupDB groupDB;

    public HomePanel(SwingViewFrame viewFrame) {
        this.viewFrame = viewFrame;
        this.groupDB = GroupDB.getInstance();
        // We use this BoxLayout, so all the SubPanels will come under each other
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // RGB colors: https://teaching.csse.uwa.edu.au/units/CITS1001/colorinfo.html
        setBackground(Color.BLUE);
        // Title and SubTitle panel, so they always stay on screen
        JPanel titlesPanel = getTitlesPanel();
        // add titlesPanel to top of the Home Page
        add(titlesPanel);
        // Scroll panel with all the groups
        JScrollPane scrollGroupsPanel = getGroupsPanel();
        // Add the scrollPanel in the middle of the screen
        add(scrollGroupsPanel);
        // Add the add group button on bottom of the screen
        JPanel buttonPanel = getButtonPanel();
        add(buttonPanel);
    }

    private static JPanel getTitlesPanel() {
        JPanel titlesPanel = new JPanel();
        titlesPanel.setLayout(new BoxLayout(titlesPanel, BoxLayout.Y_AXIS));
        titlesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlesPanel.setBackground(Color.BLUE);
        // Title on top of the screen aligned in the center and BIG
        JLabel titleLabel = new JLabel("Money Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        https://fonts.google.com/specimen/Montserrat
        titlesPanel.add(titleLabel);
        // Create SubTitle under Title
        JLabel subTitleLabel = new JLabel("Groups:", SwingConstants.CENTER);
        subTitleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        subTitleLabel.setForeground(Color.WHITE);
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlesPanel.add(subTitleLabel);
        return titlesPanel;
    }

    private JScrollPane getGroupsPanel() {
        // SubPanel to see all groups
        JPanel groupsPanel = new JPanel();
        // Align the groups vertically and in the center
        groupsPanel.setLayout(new BoxLayout(groupsPanel, BoxLayout.Y_AXIS));
        groupsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Very Light Blue
        groupsPanel.setBackground(new Color(51, 204, 255));
        // Add the groups as buttons to the panel with their name
        for (GroupEntry groupEntry : groupDB.getAllGroupEntries()) {
            Group group = groupEntry.getGroup();
            JButton button = new JButton("Group: " + group.getGroupName());
            button.setPreferredSize(new Dimension(180, 40));
            button.setMaximumSize(button.getPreferredSize());
            // Small offset because it just couldn't align in the center
            button.setAlignmentX(Component.CENTER_ALIGNMENT + 0.025f);
            // when button is clicked we go to the right group page
            button.addActionListener(e -> this.viewFrame.updateAndShowGroupPage(group.getGroupID()));
            groupsPanel.add(button);
            groupsPanel.add(Box.createVerticalStrut(20));
        }
        // Make it a scrollPane so you can scroll endlessly
        JScrollPane scrollGroupsPanel = new JScrollPane(groupsPanel);
        scrollGroupsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return scrollGroupsPanel;
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.setPreferredSize(new Dimension(160, 80));
        addGroupButton.setMaximumSize(addGroupButton.getPreferredSize());
        addGroupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // when button is clicked we go to an add group dialogue
        addGroupButton.addActionListener(e -> this.viewFrame.updateAndShowAddGroupPage());
        buttonPanel.add(addGroupButton);
        return buttonPanel;
    }
}
