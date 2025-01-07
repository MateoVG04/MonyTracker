package Model.Database.Entries;

import Model.Group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GroupEntry{
    private final Group group;

    public GroupEntry(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return this.group;
    }
}
