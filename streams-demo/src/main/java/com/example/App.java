package com.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Movie> allMovies;


        allMovies = getMovies();
        // streams are just for loops
        // highRatedWithArrayList();
        // highRatedWithStream();
        // moviesFromGenre();
        // mapExample();
        // chainingExample();
        // groupingExample();
        // averageRating();
        // highestRated();
        // highestRated2();
        // sortByTitle();
        // sortByYear();
        // sortByRating();
        // fizzBuzz();

        // Close scanner to prevent resource leak
        scanner.close();
    }

    private static void fizzBuzz() {
        IntStream.rangeClosed(1, 100)
                .mapToObj(n -> {
                    if (n % 15 == 0) return "FizzBuzz";
                    if (n % 3 == 0) return "Fizz";
                    if (n % 5 == 0) return "Buzz";
                    return String.valueOf(n);
                })
                .forEach(System.out::println);
    }

    private static void sortByRating() {
        List<Movie> allMovies = getMovies(); // Fixed: Initialize the variable

        // Fixed: If you want movies ranked 2-5, use skip(1).limit(4)
        // If you want movies ranked 2-4, use skip(1).limit(3)
        List<Movie> next3 = allMovies.stream()
                .sorted(Comparator.comparing(Movie::rating).reversed())
                .skip(1)
                .limit(4) // Changed to 4 to match the "2-5" in the title
                .toList();

        print(next3, "2-5 Top Rated Movies");
    }

    private static void sortByYear() {
        List<Movie> allMovies = getMovies();

        allMovies.stream()
                .sorted(Comparator.comparing(Movie::releaseYear).reversed())
                .limit(3)
                .forEach(Movie::print);
    }

    private static void sortByTitle() {
        List<Movie> allMovies = getMovies();

        allMovies.stream()
                .sorted(Comparator.comparing(Movie::title).reversed())
                .forEach(Movie::print);
    }

    private static void highestRated2() {
        List<Movie> allMovies = getMovies();

        Optional<Movie> best = allMovies.stream()
                .sorted(Comparator.comparing(Movie::rating).reversed())
                .findFirst();
        best.ifPresent(m->System.out.println(m.title()));
    }

    private static void highestRated() {
        List<Movie> allMovies = getMovies();
        Optional<Movie> highestRated = allMovies.stream()
                .max(Comparator.comparing(m-> m.rating()));

        highestRated.ifPresent(m -> m.print());
    }

    private static void averageRating() {
        List<Movie> allMovies = getMovies();

        OptionalDouble average = allMovies.stream()
                .mapToDouble(m -> m.rating())
                .average();

        System.out.printf("The average rating of all movies is %.2f%n", average.orElse(0.0));
    }

    private static void groupingExample() {
        List<Movie> allMovies = getMovies();

        Map<String, List<Movie>> moviesByGenre = allMovies.stream()
                .sorted(Comparator.comparing(Movie::genre))
                .collect(Collectors.groupingBy(m -> m.genre()));

        moviesByGenre.forEach((genre, movieList) -> {
            System.out.println(genre);
            List<String> titles = movieList.stream()
                    .map(m -> m.title())
                    .toList();

            for(String title:titles) {
                System.out.println("\t" + title);
            }
            //System.out.printf("%s: %d movies%n", genre, movieList.size());
        });
    }

    private static void chainingExample() {
        List<Movie> allMovies = getMovies();

        List<String> titles = allMovies.stream()
                .filter(m -> m.releaseYear() > 2000 && m.rating() >= 9)
                .map(m -> m.title())
                .toList();

        for (String title : titles) {
            System.out.println(title);
        }
    }

    // map lets us "slice" an object
    private static void mapExample() {
        List<Movie> allMovies = getMovies();

        List<String> titles = allMovies.stream()
                .map(m -> m.title())
                .toList();

        for (String title : titles) {
            System.out.println(title);
        }
    }

    private static void moviesFromGenre() {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        List<Movie> allMovies = getMovies();

        List<Movie> byGenre = allMovies.stream()
                .filter(m -> m.genre().equalsIgnoreCase(genre))
                .toList();

        print(byGenre, "Movies for Genre: " + genre);
    }

    private static void highRatedWithStream() {
        List<Movie> allMovies = getMovies();

        // -> lambda , 'in-line function without declaring formally'
        List<Movie> highRated = allMovies.stream()
                .filter(movie -> movie.rating() >= 9)
                .toList();

        print(highRated, "Highest Rated Movies (from stream)");
    }

    private static void highRatedWithArrayList() {
        List<Movie> allMovies = getMovies();

        // print only the movies with a 9.0 rating or higher
        ArrayList<Movie> highRated = new ArrayList<>();

        for(Movie movie : allMovies) {
            if (movie.rating() >= 9) {
                highRated.add(movie);
            }
        }

        print(highRated, "Highest Rated Movies");
    }

    private static List<Movie> getMovies() {
        return Arrays.asList(
                new Movie("The Shawshank Redemption", "Drama", "Frank Darabont", 9.3, 1994),
                new Movie("The Godfather", "Crime", "Francis Ford Coppola", 9.2, 1972),
                new Movie("Pulp Fiction", "Crime", "Quentin Tarantino", 8.9, 1994),
                new Movie("The Dark Knight", "Action", "Christopher Nolan", 9.0, 2008),
                new Movie("Forrest Gump", "Drama", "Robert Zemeckis", 8.8, 1994),
                new Movie("Inception", "Sci-Fi", "Christopher Nolan", 8.8, 2010),
                new Movie("The Matrix", "Sci-Fi", "The Wachowskis", 8.7, 1999),
                new Movie("Goodfellas", "Crime", "Martin Scorsese", 8.7, 1990)
        );
    }

    private static void print(List<Movie> movies, String title) {
        System.out.println(title);
        System.out.printf("%-30s | %-10s | %-25s | %-6s | %s%n",
                "Title",
                "Genre",
                "Director",
                "Rating",
                "Year");

        for(Movie movie : movies) {
            movie.print();
        }
    }
}