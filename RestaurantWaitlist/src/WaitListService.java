import java.util.ArrayList;

public class WaitListService() {
    private final ArrayList<Party> waitList;
    public WaitListService() {
        waitList = new ArrayList<>;
    }

    public Party callNextParty() {
        if (waitList.isEmpty()) {
            return null;
        }
        Party nextPant = waitList.remove(0);
        return nextParty;
    }

    public Party removeParty(int index) {
        if (index < 0 || index >= waitList.size()) {
            return null;
        }
    }
}