import Controller.Controller;
import Model.Database.GroupDB;
import Model.ModelApp;
import Model.Observers.EmailSender;
import View.SwingFactory.SwingViewFactory;

public class main {
    public static void main(String[] args) {
        // Create the group database
        GroupDB groupDB = GroupDB.getInstance();
        EmailSender emailSender = EmailSender.getInstanceEmailSender();
        groupDB.addPropertyChangeListener(emailSender);
        ModelApp model = new ModelApp();
        Controller controller = new Controller(model);
        // SwingViewFactory will create the view and initialise it
        // Whenever the user clicks on something, the view sends it to the controller, the controller will validate the input
        // If it's valid -> model will do the action and the controller observes when the model is done
        // Then the view observes this and will update the view.
        // If it's not valid -> the view observes this and will show an error message on the screen
        new SwingViewFactory(controller);
    }
}
