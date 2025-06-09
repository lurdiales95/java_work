public class Main {
    public static void main(String [] args) {
        LockerService service = new LockerService(1000);
        //you can ask user how many lockers are available.

        Result status = service.rentlocker();

        if(status.getSuccess()) {
            IO.displayResult(status);
        }



        }
    }


    /* RULES
    1. You may only use the Scanner and System.out.println() in one class
    2. Other classes must return strings, booleans, etc. so you know what to print
    3. What classes should you have:
        1. Locker
        2. LockerService (rent, access, release)
        3. IO|Utilities (printing scanner)
        4. Main (app workflow)
        5. Result


     */

//Main should:
//Spit out locker service,
//Configure array
//Make sure everything is ready to run
//Start a loop on main to display the menu. Ask IO class to display menu, get user choice, and display prompts.