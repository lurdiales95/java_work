public class AlwaysPicksOne implements NumberGenerator {

    @Override
    public int generateNumber(int max) {
        return 1;
    }
}
