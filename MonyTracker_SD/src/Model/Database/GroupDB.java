package Model.Database;

import Model.Database.Entries.GroupEntry;
import Model.Database.Entries.GroupEntryNull;

import java.util.HashMap;

public class GroupDB {
    private final HashMap<Integer, GroupEntry> db;
    private volatile static GroupDB uniqueInstance;

    public GroupDB() {
        this.db = new HashMap<>();
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

    public void addGroupEntry(GroupEntry groupEntry) {
        int groupID = groupEntry.getGroup().getGroupID();
        if (!db.containsKey(groupID)) {
            db.put(groupID, groupEntry);
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
}
