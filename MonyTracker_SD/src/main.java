import Controller.Controller;
import Model.Database.GroupDB;
import Model.ModelApp;
import View.AbstractViewFactory;
import View.SwingFactory.SwingViewFactory;
import View.SwingFactory.SwingViewFrame;

import javax.naming.ldap.Control;
import javax.swing.*;

public class main {
    public static void main(String[] args) {
        GroupDB groupDatabase = GroupDB.getInstance();
        ModelApp model = new ModelApp(groupDatabase);
        SwingViewFactory factory = new SwingViewFactory();
        SwingViewFrame view = factory.getViewFrame();
        Controller controller = new Controller(model, view);
    }
}
