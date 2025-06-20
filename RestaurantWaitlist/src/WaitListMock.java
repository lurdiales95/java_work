import java.util.ArrayList;
import java.util.Arrays;

public class WaitListMock implements WaitListService{
    @Override
    public void addParty(Party party) {
        return;

    }

    @Override
    public Party callNextParty() {
        return new Party("Test", 5);
    }

    @Override
    public Party removeParty(int index) {
        return new Party("Test", 3);
    }

    @Override
    public ArrayList<Party> getList() {
        return new ArrayList<>(Arrays.asList(
                new Party("Johnson", 4),
                new Party("Smith", 2),
                new Party("Garcia", 6),
                new Party("Williams", 3),
                new Party("Brown", 5),
                new Party("Davis", 2)

        ));
    }
}
