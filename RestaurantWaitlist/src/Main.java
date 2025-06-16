public class Main {
    public static void main(String[] args) {
        WaitListService service = new WaitListService();

        //application loop
        while(true) {
            String choice = TerminalUtils.getMenuChoice();

            if (choice.equals("1")) {
                ViewWaitlistCommand.execute(service);
        }

            if (Choice.equals("5")) {
                break;

            }
        }
    }
}

