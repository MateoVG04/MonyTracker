package Model.Database;

import Model.Database.Entries.GroupEntry;
import Model.Database.Entries.GroupEntryNull;
import Model.Observers.EmailSender;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;

public class GroupDB {
    private final HashMap<Integer, GroupEntry> db;
    private volatile static GroupDB uniqueInstance;
    private final PropertyChangeSupport support;
    private final EmailSender emailSender = EmailSender.getInstanceEmailSender();

    private GroupDB() {
        this.db = new HashMap<>();
        this.support = new PropertyChangeSupport(this);
    }

    public static GroupDB getInstance() {
        if (uniqueInstance == null) {
            synchronized (GroupDB.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new GroupDB();
                }
            }
        }
        return uniqueInstance;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

    public void addGroupEntry(GroupEntry groupEntry) {
        int groupID = groupEntry.getGroup().getGroupID();
        if (!db.containsKey(groupID)) {
            GroupEntry oldEntry = db.put(groupID, groupEntry);
            // geef de group door aan de emailSender zodat de email sender naar wie hij een email moet sturen
            emailSender.groupToSend(groupEntry.getGroup());

            // ER IS IETS AANGEPAST -> email sender moet email sturen
            support.firePropertyChange("groupEntryAdded",oldEntry,groupEntry);
        } else {
            throw new IllegalArgumentException("Group with ID " + groupID + " already exists.");
        }
    }

    public GroupEntry getGroupEntry(int groupID) {
        return this.db.getOrDefault(groupID, new GroupEntryNull());
    }

    public void removeGroupEntry(int groupID) {
        if (db.containsKey(groupID)) {
            db.remove(groupID);
        }
        else {
            throw new IllegalArgumentException("Group with ID " + groupID + " does not exist.");
        }
    }

    public Collection<GroupEntry> getAllGroupEntries() {
        return db.values();
    }
}
