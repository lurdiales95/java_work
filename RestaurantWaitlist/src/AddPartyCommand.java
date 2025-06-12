public class AddPartyCommand {
    public static void executve(WaitListService service) {
        Party newParty = TerminalUtils.createParty();
        service.addParty(newParty);
        TerminalUtils.print(String.format("%s, party of %d has been added to the list."));
        TerminalUtils.pressToContinue();


    }
}
