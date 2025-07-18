import java.util.ArrayList;

public class InMemoryWaitList implements WaitListService{
    private final ArrayList<Party> waitList;

    public InMemoryWaitList() {
        waitList = new ArrayList<>();
    }

    public void addParty(Party party) {
        waitList.add(party);
    }

    public Party callNextParty() {
        if (waitList.isEmpty()) {
            return null;
        }
        Party nextParty = waitList.remove(0);
        return nextParty;
    }

    public Party removeParty(int index) {
        if (index < 0 || index >= waitList.size()) {
            return null;
        }
        Party removedParty = waitList.remove(index);
        return removedParty;
    }

    public ArrayList<Party> getList() {
        return new ArrayList<>(waitList);

    }

}