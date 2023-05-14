package lab2;

import java.time.LocalDateTime;

public class MovieSession {

    private final MovieTheaterHall hall;

    private final Movie movie;

    private final LocalDateTime startTime;

    public MovieSession(MovieTheaterHall hall, Movie movie, LocalDateTime startTime) {
        this.hall = hall;
        this.movie = movie;
        this.startTime = startTime;

    }

    public MovieTheaterHall getHall() {
        return hall;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "КИНОТЕАТР: " + hall.getMovieTheater().getName() + "\t\tФИЛЬМ: " + movie.getNAME() + "\t\tНАЧАЛО: " + startTime.toString();
    }

    public int getRevenue() {
        int rows = getHall().getROWS();
        int columns = getHall().getCOLUMNS();
        int revenue = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (getHall().getHallMatrix()[i][j] < 0) {
                    revenue -= getHall().getHallMatrix()[i][j];
                }
            }
        }
        return revenue;
    }
}
