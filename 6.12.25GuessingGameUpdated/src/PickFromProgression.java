public class PickFromProgression implements NumberGenerator {
    private int[] numbers = {1, 5, 3, 7, 5, 2, 4, 8};
    private int currentIndex = 0;

    @Override
    public int generateNumber(int max) {
        if (currentIndex > numbers.length) {
            // start from the beginning
            currentIndex = 0;
        }

        int num = numbers[currentIndex];
        currentIndex++;
        return num;

    }
}
