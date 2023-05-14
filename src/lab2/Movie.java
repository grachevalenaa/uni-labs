package lab2;

public class Movie {

    private final String NAME;

    private final int RELEASE_YEAR;

    private final String GENRE;

    private final int MOVIE_DURATION;

    private final String FORMAT;

    public Movie(String NAME, int RELEASE_YEAR, String GENRE, int MOVIE_DURATION, String FORMAT) {
        this.NAME = NAME;
        this.RELEASE_YEAR = RELEASE_YEAR;
        this.GENRE = GENRE;
        this.MOVIE_DURATION = MOVIE_DURATION;
        this.FORMAT = FORMAT;
    }

    public String getNAME() {
        return NAME;
    }
}
