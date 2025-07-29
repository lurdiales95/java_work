package com.example.jdbc.demo;

import com.example.jdbc.demo.model.Movie;
import com.example.jdbc.demo.model.MovieDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.jdbc.demo.repository.MovieRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private MovieRepository movieRepo;

	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		displayAll();
//		createMovie();
//		getById();
//		update();
//		delete();
//		getByGenre();
//		getByIdSproc();
		createMovieSproc();
	}

	private void getByIdSproc() {
		System.out.print("Enter Movie ID: ");
		int id = Integer.parseInt(scanner.nextLine());

		Optional<Movie> result = movieRepo.getByIdSproc(id);

		if (result.isPresent()) {
			Movie movie = result.get();
			System.out.printf("ID: %d%n", movie.getMovieID());
			System.out.printf("Title: %s%n", movie.getTitle());
			System.out.printf("Release Date: %s%n", movie.getReleaseDate());
		} else {
			System.out.printf("Movie with id %d not found!%n", id);
		}
	}

	private void getByGenre() {
		System.out.print("Enter Genre ID: ");
		int id = Integer.parseInt(scanner.nextLine());

		List<MovieDetails> movies = movieRepo.getByGenre(id);

		for(MovieDetails deets : movies) {
			System.out.printf("%d\t%s\t%s\t%s%n", deets.getMovieID(), deets.getTitle(), deets.getGenreName(), deets.getRatingCode());
		}
	}
	private void delete() {
		System.out.print("Enter Movie ID: ");
		int id = Integer.parseInt(scanner.nextLine());

		if (movieRepo.delete(id)) {
			System.out.println("Movie deleted!");
		} else {
			System.out.println("Movie was not found!");
		}
	}
	private void update() {
		Movie updated = new Movie();
		String input;
		System.out.print("Enter Movie ID: ");
		updated.setMovieID(Integer.parseInt(scanner.nextLine()));

		Optional<Movie> existing = movieRepo.getById(updated.getMovieID());

		if (existing.isPresent()) {
			System.out.println("Leave blank if unchanged!");

			System.out.printf("Enter Title (%s): ", existing.get().getTitle());
			input = scanner.nextLine();
			if(!input.isEmpty()) {
				updated.setTitle(input);
			} else {
				updated.setTitle(existing.get().getTitle());
			}


			System.out.print("Enter Release Date: ");
			updated.setReleaseDate(LocalDate.parse(scanner.nextLine()));

			System.out.print("Enter Genre ID: ");
			updated.setGenreID(Integer.parseInt(scanner.nextLine()));

			System.out.print("Enter Rating ID: ");
			updated.setRatingID(Integer.parseInt(scanner.nextLine()));

			updated = movieRepo.update(updated);
			System.out.printf("Movie with id: %d was updated!%n", updated.getMovieID());
		}

	}

	private void getById() {
		System.out.print("Enter Movie ID: ");
		int id = Integer.parseInt(scanner.nextLine());

		Optional<Movie> result = movieRepo.getById(id);

		if (result.isPresent()) {
			Movie movie = result.get();
			System.out.printf("ID: %d%n", movie.getMovieID());
			System.out.printf("Title: %s%n", movie.getTitle());
			System.out.printf("Release Date: %s%n", movie.getReleaseDate());
		} else {
			System.out.printf("Movie with id %d not found!%n", id);
		}
	}


	private void displayAll() {
		List<Movie> movies = movieRepo.getAll();

		for(Movie m : movies) {
			System.out.printf("ID: %d%n", m.getMovieID());
			System.out.printf("Title: %s%n", m.getTitle());
			System.out.printf("Release Date: %s%n", m.getReleaseDate());
		}
	}

	private void createMovie() {
		Movie movie = new Movie();

		System.out.print("Enter Title: ");
		movie.setTitle(scanner.nextLine());

		System.out.print("Enter Release Date: ");
		movie.setReleaseDate(LocalDate.parse(scanner.nextLine()));

		System.out.print("Enter Genre ID: ");
		movie.setGenreID(Integer.parseInt(scanner.nextLine()));

		System.out.print("Enter Rating ID: ");
		movie.setRatingID(Integer.parseInt(scanner.nextLine()));

		movie = movieRepo.add(movie);
		System.out.printf("Movie with id: %d was created!%n", movie.getMovieID());
	}

	private void createMovieSproc() {
		Movie movie = new Movie();

		System.out.print("Enter Title: ");
		movie.setTitle(scanner.nextLine());

		System.out.print("Enter Release Date: ");
		movie.setReleaseDate(LocalDate.parse(scanner.nextLine()));

		System.out.print("Enter Genre ID: ");
		movie.setGenreID(Integer.parseInt(scanner.nextLine()));

		System.out.print("Enter Rating ID: ");
		movie.setRatingID(Integer.parseInt(scanner.nextLine()));

		movie = movieRepo.addSproc(movie);
		System.out.printf("Movie with id: %d was created!%n", movie.getMovieID());
	}
}
