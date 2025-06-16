public class ViewWaitlistCommand {
    public static void execute(WaitListService service) {
        TerminalUtils.print(service.getList());
        TerminalUtils.pressToContinue();
    }
}
