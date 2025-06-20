public class TestInputHandler implements InputHandler {
    @Override
    public int getPositiveInteger() {
        return 5;
    }

    @Override
    public int getMaxNumber() {
        return 10;
    }

    @Override
    public boolean getPlayAgain() {
        return false;
    }
}
