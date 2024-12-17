package View.Panels;

import View.ViewFrame;

import javax.swing.*;
import java.awt.*;

public class GroupPanel extends JFrame {
    private ViewFrame viewFrame;

    public GroupPanel(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
        this.setLayout(new BorderLayout());
    }
}
