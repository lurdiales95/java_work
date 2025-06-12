public class Main {
    public static void main(String[] args) {
        WaitListService service = new WaitListService();

        while(true) {
            String choice = TerminalUtils.getMenuChoice();

            if (Choice.equals("1")) {
                ViewWaitlistCommand.execute(service);
            } else if (choice.equals( "2")) {
                AddPartyCommand.execute(service);
            } else if (choice.equals("3")) {

            }


        }
        }

    }
}
