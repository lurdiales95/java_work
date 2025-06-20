public class Main {
    public static void main(String[] args) {
        WaitListService service = new InMemoryWaitList();

        // application loop
        while(true) {
            String choice = TerminalUtils.getMenuChoice();

            if (choice.equals("1")) {
                ViewWaitlistCommand.execute(service);
            } else if (choice.equals("2")) {
                AddPartyCommand.execute(service);
            } else if (choice.equals("3")) {
                RemovePartyCommand.execute(service);
            } else if (choice.equals("4")) {
                CallNextPartyCommand.execute(service);

            } else {
                break;
            }
        }
    }
    private static WaitListService getWaitlistImplementation() {
        return new InMemoryWaitList();
    }

}

