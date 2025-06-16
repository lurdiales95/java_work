public class AddPartyCommand {
    public static void execute(WaitListService service) {
        Party newParty = TerminalUtils.createParty();
        service.addParty(newParty);
        TerminalUtils.print(String.format("%s, party of %d has been added to the list.", newParty.getName(), newParty.getSize()));
        TerminalUtils.pressToContinue();


    }
}
