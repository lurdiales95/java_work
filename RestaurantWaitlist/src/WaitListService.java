import java.util.ArrayList;

public interface WaitListService {
    void addParty(Party party);
    Party callNextParty();
    Party removeParty(int index);
    ArrayList<Party> getList();


}
