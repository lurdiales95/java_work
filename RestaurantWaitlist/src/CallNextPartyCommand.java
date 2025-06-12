public class CallNextPartyCommand {
    public static void execute(WaitListService service) {
        Party calledParty = service.callNextParty();

        if (calledParty == null) {
            TerminalUtils.print("There are no parties on the list.");

        } else {
            TerminalUtils.print(String.format("Calling %s, party of %d", calledParty.getName(), calledParty.getSize()));

        }
        TerminalUtils.pressToContinue();

    }
}
