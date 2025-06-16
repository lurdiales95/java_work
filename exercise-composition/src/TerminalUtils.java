import java.util.Scanner;

public class TerminalUtils {
    private static Scanner io = new Scanner(System.in);

    public static String getArtifactName() {
        System.out.print("Enter the name of the artifact: ");
        return io.nextLine();
    }

    public static int getYearOfDiscovery() {
        System.out.print("Enter the eyar of discovery: ");
        int year = io.nextInt();
        io.nextLine();
        return year;
    }

    public static Person createDiscoverer() {
        System.out.println("Enter discoverer information: ");
        return createPerson();
    }

    public static Person createCurator() {
        System.out.println("Enter curator information: ");
        return createPerson();
    }

    public static Person createPerson() {
        System.out.print("Enter first name: ");
        String firstName = io.nextLine();

        System.out.print("Enter last name: ");
        String lastName = io.nextLine();

        System.out.print("Enter primary specialty: ");
        String primarySpecialty = io.nextLine();

        return new Person(firstName, lastName, primarySpecialty);
    }

    public static boolean isDiscovererAlsoCreator() {
        System.out.print("Is the discoverer also the creator? (Y/N)");
        String response = io.nextLine().trim().toUpperCase();
        return response.equals("Y") || response.equals ("YES");
    }

    public static Artifact createArtifact() {
        String artifactName = getArtifactName();
        int yearOfDiscovery = getYearOfDiscovery();
        Person discoverer = createDiscoverer();

        Person curator;
        if (isDiscovererAlsoCreator()) {
            curator = discoverer;
        } else {
            curator = createCurator();
        }

        return new Artifact(artifactName, yearOfDiscovery, discoverer, curator);
    }

}