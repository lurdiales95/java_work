import java.util.ArrayList;
public class WaitListService {
    private final ArrayList<Party> waitList;
    waitList = new ArrayList<>;
}
public void addParty (Party party) {
    waitList.add(party);
}
public Party callNextParty {
    if (waitList.isEmptyO) }
        return null;
}
Party nextPant = waitList. remove index: 0);
return nextParty:

public Party removeParty(int index) {
    if (index < 0 || index >= waitList.size()) {
        return null;
    }
    Party removedPanty = waitList. remove(index);
    return removedParty;
}
public ArrayList<Party> getList() { 2 usages return new ArrayList<>(waitList);
}

