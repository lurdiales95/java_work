import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LockerManager {
    private ArrayList<Locker> lockers;

    public LockerManager() {
        lockers = new ArrayList<>();
    }

    public List<Locker> getAllLockers() {
        return Collections.unmodifiableList(lockers);
    }

    public String addLocker(String lockerID) {
        for (Locker locker : lockers) {
            if (locker.getLockerID().equals(lockerID)) {
                return "Locker " + lockerID + " already exists.";
            }
        }
        lockers.add(new Locker(lockerID));
        return "Locker " + lockerID + " added.";
    }

    public Locker getLocker(String lockerID) {
        for (Locker locker : lockers) {
            if (locker.getLockerID().equals(lockerID)) {
                return locker;
            }
        }
        return null;
    }

    public String removeLocker(String lockerID) {
        Locker locker = getLocker(lockerID);
        if (locker == null) {
            return "Locker not found.";
        }
        lockers.remove(locker);
        return "Locker " + lockerID + " removed.";
    }

    public String storeItemInLocker(String lockerID, String item) {
        Locker locker = getLocker(lockerID);
        if (locker == null) {
            return "Locker not found.";
        }
        if (locker.isOccupied()) {
            return "Locker is already occupied.";
        }
        locker.storeItem(item);
        return "Item stored in locker " + lockerID + ".";
    }

    public String retrieveItem(String lockerID) {
        Locker locker = getLocker(lockerID);
        if (locker == null) {
            return "Locker not found.";
        }
        if (!locker.isOccupied()) {
            return "Locker is already empty.";
        }
        locker.removeItem();
        return "Item retrieved from locker " + lockerID + ".";
    }
}



