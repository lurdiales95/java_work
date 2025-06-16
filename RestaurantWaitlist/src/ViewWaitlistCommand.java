public class ViewWaitlistCommand {
    public static void Execute(WaitListService service) {
        TerminalUtils.print(service.getList());
        TerminalUtils.pressToContinue();
    }
}
