package View.Panels;

import View.ViewFrame;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private ViewFrame viewFrame;

    public HomePanel(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
        this.setLayout(new BorderLayout());
    }
}
