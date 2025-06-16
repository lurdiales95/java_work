public class RemovePartyCommand {
    public static void execute(WaitListService service) {
        int index = TerminalUtils.getPartyByIndex(service.getList());

        if (index != -1); {
            Party removed = service.removeParty(index);

            if (removed != null) {
                TerminalUtils.print(String.format("%s has been removed.", removed.getName()));
            }
        }

        TerminalUtils.pressToContinue();
    }
}
