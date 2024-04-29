package sait.mms.problemdomain;

public class Movie {
	
	// Attributes
	private int id;
	private int duration;
	private String title;
	private int year;

	/**
	 * Movie Constructor
	 * @param id The movies ID
	 * @param duration The movies Duration
	 * @param title The title of the movie
	 * @param year The year the movie was released
	 */
	public Movie(int id, int duration, String title, int year) {
		super();
		this.id = id;
		this.duration = duration;
		this.title = title;
		this.year = year;
	}

	/**
	 * Get the id of the movie
	 * @return The Movie's ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the movie
	 * @param id The Movie's ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the duration of the movie
	 * @return The Movie's Duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the movie
	 * @param duration The Movie's duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Get the title of the movie
	 * @return The title of the movie
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the movie
	 * @param title The Movie's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the movie's year of release
	 * @return The movie's year of release
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Set the movie's year of release
	 * @param year The Movie's year of release
	 */
	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		String s;
		int titleLength = title.length() + 2; // Creating the space between the year and title
		if (duration < 100) {
			s = String.format("%d %16d %"+titleLength+"s", duration, year, title);
		} else {
			s = String.format("%d %15d %"+titleLength+"s", duration, year, title);
		}
		
		return s;
	}

}
