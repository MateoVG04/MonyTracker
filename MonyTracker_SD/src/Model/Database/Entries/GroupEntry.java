package Model.Database.Entries;

import Model.Group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// Right now the group Entry has no functionality
// But in the future we can add stuff like time and date of group created
// And a lot of other parameters
public class GroupEntry{
    private final Group group;

    public GroupEntry(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return this.group;
    }
}
