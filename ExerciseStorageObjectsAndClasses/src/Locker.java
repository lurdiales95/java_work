public class Locker {
    private String lockerID;
    private boolean isOccupied;
    private String contents;

    public Locker(String lockerID) {
        this.lockerID = lockerID;
        this.isOccupied = false;
        this.contents = "";
    }

    public String getLockerID() {
        return lockerID;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void storeItem(String item) {
        this.contents = item;
        this.isOccupied = true;
    }

    public void removeItem() {
        this.contents = "";
        this.isOccupied = false;
    }

    @Override
    public String toString() {
        return "Locker ID: " + lockerID + ", Occupied: " + isOccupied + ", Contents: " + contents;
    }
}




