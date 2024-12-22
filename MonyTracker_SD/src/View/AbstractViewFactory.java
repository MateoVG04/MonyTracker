package View;

import Model.Ticket;

public interface AbstractViewFactory<Type> {
    // We use generics, so you can also use other libraries than JPanel to create a GUI
    Type createHomePage();
    // groupID so you know which group to go to
    Type createGroupPage(int groupID);
    // ticketID so you know which ticket to go to and groupID, so you know which group to go back to with back button
    Type createTicketPage(Ticket ticket, int groupID);
    // addGroupPage to add groups to the home Page
    Type createAddGroupPage();
    // addTicketPage to add tickets to groups
    Type createAddTicketPage(int groupID);
}
