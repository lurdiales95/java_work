public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Museum Artifact Tracking System!");
        System.out.println("==============================================");

        Artifact artifact = TerminalUtils.createArtifact();

        System.out.println("\n=== ARTIFACT REPORT ===");
        System.out.println(artifact.toString());
    }
}
