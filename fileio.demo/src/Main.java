import java.io.*;
import java.util.Scanner;

public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);

//        //This allows us to print code directory. Works for debugging relative file paths.
//        File file = new File("data/input.txt");
//
//
//        // true if file exists
//        System.out.println(file.exists());
//        // true if it's a file
//        System.out.println(file.isFile());
//        // true if it's a directory
//        System.out.println(file.isDirectory());
//        // prints file length in bytes
//        System.out.println(file.length());
//    }
//}

        static {
            System.out.println("Working directory " + System.getProperty("user.dir"));
        }

        public static void main(String [] args) {
            // Print working directory (already done in static block)

            // Create a new file
            File file2 = new File("data/poem.txt");

            try {
                boolean created = file2.createNewFile();
                System.out.println("File created: " + created);

                // Create a Scanner only if file exists
                if (file2.exists()) {
                    Scanner scanner = new Scanner(file2);
                    if (scanner.hasNextLine()) {
                        System.out.println("First line: " + scanner.nextLine());
                    }
                    scanner.close();
                }

                // Delete the file
                boolean deleted = file2.delete();
                System.out.println("File deleted: " + deleted);

            } catch (IOException e) {
                System.out.println("Couldn't create or read the file!");
                e.printStackTrace();
            }
        }
    }



        // Write to file
//
//
//        }

//        try {
//            PrintWriter writer = new PrintWriter("data/output.txt");
//            writer.println("Hello, World!");
//            writer.println("Today is a good day for Java!");
//            writer.println("Happy June!");
//            writer.print("This will not have a carriage return |");
//            writer.printf("Number: %d%n", 15);
//
//            writer.close();
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
//}


//        try {
//            FileReader reader = new FileReader("data/output.txt");
//            int charCode;
//            while ((charCode = reader.read()) != -1) {
//                char c = (char) charCode;
//                System.out.print(c);
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println(e);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
//}
//        // Java's long way of doing 'with'
//        try (BufferedReader reader = new BufferedReader(new FileReader("data/output.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading the file: " + e.getMessage());
//        }
//    }
//}


