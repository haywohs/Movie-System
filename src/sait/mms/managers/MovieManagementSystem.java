package sait.mms.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import sait.mms.contracts.DatabaseDriver;
import sait.mms.drivers.MariaDBDriver;
import sait.mms.problemdomain.Movie;

public class MovieManagementSystem {
	private DatabaseDriver db;
	Scanner in = new Scanner(System.in);
	
	/**
	 * Movie Management System constructor
	 */
	public MovieManagementSystem() {
		this.db = new MariaDBDriver();
		try {
			this.db.connect();
			
			while(true) {
				displayMenu();
			}
			
		} catch (SQLException e) {
			System.out.println("connections not found");
		}
	}
	
	/**
	 * Displays the menu options and processes the users input
	 */
	public void displayMenu() {
		System.out.println("Jim's Movie Manager");
		System.out.println("1\t Add New Movie");
		System.out.println("2\t Print movies released in year");
		System.out.println("3\t Print random list of movies");
		System.out.println("4\t Delete a movie");
		System.out.println("5\t Exit");
		
		System.out.print("\nEnter an option: ");
		int option = in.nextInt();
		
		switch(option) {
		case 1:
			addMovie();
			break;
		case 2:
			printMoviesInYear();
			break;
		case 3:
			printRandomMovies();
			break;
		case 4:
			deleteMovie();
			break;
		case 5:
			System.exit(0);
			break;
		}
	}
	
	/**
	 * Asks the user for movie title, duration, and year and then adds the movie into the database
	 */
	public void addMovie() {
		
		// Asking for new movie data
		System.out.print("\nEnter movie title: ");
		in.nextLine();
		String title = in.nextLine();
		System.out.print("Enter duration: ");
		int duration = in.nextInt();
		System.out.print("Enter year: ");
		int year = in.nextInt();
		
		// Get the largest id and add 1 for the new movie ID
		String getMaxID = "select Max(id) + 1 from movies";
		ResultSet rs;
		int id = 0;
		try {
			rs = db.get(getMaxID);
			
			// movies the pointer to the correct row
			rs.next();
			id = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("can't retrive id");
			e.printStackTrace();
		}
		
		// Inserting the new movie into the database
		if (id != 0) {
			String query = "insert into movies (id, duration, title, year) values (" + id + "," + duration + ",'" + title + "'," + year + ")";
			int rows = 0;
			try {
				rows = db.update(query);
			} catch (SQLException e) {
				System.out.println("Can't update");
				e.printStackTrace();
			}
		
			System.out.println("Added movie to database.\n");
		}
	}
		
	/**
	 * Asks user for a year, and prints out all the movies released that year
	 */
	public void printMoviesInYear() {
		
		// Asking for the year to search for
		System.out.print("Enter in year: ");
		int year = in.nextInt();
		
		String query = "select * from movies where year = " + year;
		
		ResultSet rs;
		int totalDuration = 0;
		try {
			rs = db.get(query);
			
			System.out.println("\n Movie List");
			
			System.out.printf("%s %10s %7s\n", "Duration", "Year", "Title");
			while(rs.next()) {
				Movie movie = new Movie(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));
				totalDuration += rs.getInt(2);
				System.out.println(movie);
			}
		} catch (SQLException e) {
			System.out.println("Cant find query");
		}
		
		System.out.println("\nTotal duration: " + totalDuration + "\n");
	}
	
	/**
	 * Prompts the user with the number of random movies they want, and print the results to screen
	 */
	public void printRandomMovies() {
		
		// Asking for the number of random movies
		System.out.print("Enter number of movies: ");
		int limit = in.nextInt();
		
		String query = "select * from movies order by rand() limit " + limit;
		
		ResultSet rs;
		int totalDuration = 0;
		try {
			rs = db.get(query);
			
			System.out.printf("%s %10s %7s\n", "Duration", "Year", "Title");
			while (rs.next()) {
				Movie movie = new Movie(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));
				totalDuration += rs.getInt(2);
				System.out.println(movie);
			}
		} catch (SQLException e) {
			System.out.println("Can't get random movies");
		}
		
		System.out.println("\nTotal duration: " + totalDuration + "\n");
		
	}
	
	/**
	 * Prompts the user the movie ID they want deleted. and delete movie from the database
	 */
	public void deleteMovie() {
		System.out.print("Enter the movie ID that you want to delete: ");
		int id = in.nextInt();
		
		String query = "delete from movies where id = " + id;
		
		try {
			db.update(query);
			System.out.println("Movie " + id + " is deleted.\n");
		} catch (SQLException e) {
			System.out.println("can't find movie");
			e.printStackTrace();
		}
	}
}
