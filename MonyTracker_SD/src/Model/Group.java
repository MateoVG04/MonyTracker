package Model;

import java.util.ArrayList;

public class Group {
    private String groupName;
    private int groupID;
    private ArrayList<Person> groupMembers;
    private ArrayList<Ticket> tickets;
    public Group(String groupName, String groupDescription, int groupID) {
        this.groupName = groupName;
        this.groupID = groupID;
        this.groupMembers = new ArrayList<>();
        this.tickets = new ArrayList<>();
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public int getGroupID() {
        return groupID;
    }
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    public void addPersonGroup(Person person) {
        this.groupMembers.add(person);
    }
    public void removePersonGroup(Person person) {
        this.groupMembers.remove(person);
    }
    public ArrayList<Person> getGroupMembers() {
        return groupMembers;
    }
    public int getSize() {
        return groupMembers.size();
    }
}
