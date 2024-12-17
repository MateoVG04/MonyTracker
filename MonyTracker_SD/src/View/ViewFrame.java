package View;

import View.Panels.GroupPanel;
import View.Panels.HomePanel;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private HomePanel homePanel;
    private GroupPanel groupPanel;

    public ViewFrame() {
        setTitle("Money Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Initialize main Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        // Initialize sub Panels
        homePanel = new HomePanel(this);
        groupPanel = new GroupPanel(this);
        // Add panels to the card layout,
        // so we can switch between them by doing cardlayout.show(cardPanel, "PanelName");
        cardPanel.add(homePanel, "HOME");
        cardPanel.add(groupPanel, "GROUP");
        add(cardPanel);
    }

    public void showHomePage() {
        cardLayout.show(cardPanel, "HOME");
    }

    public void showGroupPage() {
        cardLayout.show(cardPanel, "GROUP");
    }
}
