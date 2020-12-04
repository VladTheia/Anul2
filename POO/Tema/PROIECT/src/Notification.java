import java.util.Date;

enum NotificationType {
    ADD, REMOVE, MODIFY;
}

public class Notification {
    NotificationType notificationType;
    int departmentID;
    int itemID;

    public Notification(NotificationType nt, int depId, int itID) {
        notificationType = nt;
        departmentID = depId;
        itemID = itID;
    }

    @Override
    public String toString() {
        String result = "";
        switch (notificationType) {
            case ADD:
                result += "ADD;";
                break;
            case MODIFY:
                result += "MODIFY;";
                break;
            case REMOVE:
                result += "REMOVE;";
                break;
        }
        result += itemID + ";" + departmentID;
        return result;
    }
}