package Model.Observers;

import Model.Group;
import Model.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailSender implements PropertyChangeListener{
    private ArrayList<Person> groupMembers;
    private Group group;
    private final String MoneyTrackerEmail = "MonyTrackerSD@gmail.com";
    // money_tracker1
    private final String MoneyTrackerPassword = "zxdm kuek orql iutn";
    private volatile static EmailSender uniqueInstance;

    public static EmailSender getInstanceEmailSender(){
        if (uniqueInstance == null){
            synchronized (EmailSender.class){
                if (uniqueInstance == null){
                    uniqueInstance = new EmailSender();
                }
            }
        }
        return uniqueInstance;
    }
    // als je een email wilt sturen, moet je eerst de group meegeven naar wie er een mail moet verstuurd worden
    public void groupToSend(Group group) {
        this.groupMembers = group.getGroupMembers();
        this.group = group;
    }
    @Override
    public void propertyChange(PropertyChangeEvent event){
        if ("groupEntryAdded".equals(event.getPropertyName())){
            final String subject = "New Group With You Was Made";
            // SMTP server properties instellen
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // email van het bedrijf (MoneyTracker) authenticeren
            javax.mail.Authenticator auth = new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(MoneyTrackerEmail, MoneyTrackerPassword);
                }
            };

            // sessie maken
            Session session = Session.getInstance(props,auth);
            
            try{
                for (Person person : groupMembers) {
                    Message message = new MimeMessage(session);
                    InternetAddress from = new InternetAddress(MoneyTrackerEmail);
                    message.setFrom(from);
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(person.getEmail()));
                    message.setSubject(subject);
                    String body = "Hallo "+person.getName()+",\nU bent toegevoegd aan de groep: "+this.group.getGroupName()+" " +
                            "op MoneyTracker.\nMet vriendelijke groeten,\nHet MoneyTracker Team!";
                    message.setText(body);
                    message.saveChanges();

                    // verstuur nu de mail
                    Transport.send(message);
                }

            } catch (MessagingException e){
                e.printStackTrace();
            }
        }
    }
}
