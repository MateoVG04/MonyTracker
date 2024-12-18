package Model.Database.Entries;

import Model.Group;

public class GroupEntryNull extends GroupEntry {
    public GroupEntryNull() {
        super(null);
    }
    @Override
    public Group getGroup() {
        return null;
    }
}
