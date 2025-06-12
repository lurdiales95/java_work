import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> colors = new ArrayList<>();
        //you can give an ArrayList a starting capacity = new ArrayList<>(initialCapacity: 20);

        colors.add("red");
        colors.add("green");
        colors.add("blue");

        colors.remove(1);
        for (int i = 0; i < colors.size(); i++) {
            System.out.println(colors.get(i));
        }
    }
    public static void listExample() {
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie("Star Wars", 1977));
        movies.add(new Movie("Shawshank Redemption", 1990));

        // When you use .stream() it's going to do a loop to find what you're requiring.
        //Equivalent of doing
        // List<Movie> oldMovies = newArrayList <>();
        //  for (int i = 0; i < movie.size(); i++)
        //      if (movies.get(i).getReleaseYear() < 1985) {
        //          oldMovies2.add(movies.get(i));
        // This bottom example is syntactic sugar
        /* List<Movie> oldMovies = movies.stream()
                .filter(m -> m.getreleaseYear() < 1985)
                .toList(); */

        movies.sort(Comparator.comparing(Movie::getTittle));

            for (int i = 0; i < movies.size(); i++) {
                System.out.println(movies.get(i).getTitle);
            }

    }
}


// diamond operator