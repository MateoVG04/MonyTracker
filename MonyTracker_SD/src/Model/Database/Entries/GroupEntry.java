package Model.Database.Entries;

import Model.Group;

public class GroupEntry {
    private final Group group;

    public GroupEntry(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return this.group;
    }
}
