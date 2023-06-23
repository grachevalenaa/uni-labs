package lab2;

import java.sql.Timestamp;

public class Session {

    private final Hall hall;  // можем в этом поле хранить любой тип зала

    private final Movie movie;

    private final Timestamp startTime;

    public Session(Hall hall, Movie movie, Timestamp startTime) {
        this.hall = hall;
        this.movie = movie;
        this.startTime = startTime;

    }

    public Hall getHall() {
        return hall;
    }

    public Movie getMovie() {
        return movie;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_RESET = "\u001B[0m";
        return String.format(ANSI_BLUE + "КИНОТЕАТР: " + ANSI_RESET + "%6S\t\t" + ANSI_BLUE + "ФИЛЬМ: "+ ANSI_RESET+
                        "%27s\t\t" + ANSI_BLUE + "НАЧАЛО: " + ANSI_RESET + "%20s\t\t" + ANSI_BLUE + "ЗАЛ: " + ANSI_RESET + "%8s",
                hall.getMovieTheater().getName(),
                movie.getNAME(),
                startTime.toString(),
                hall.getTypeOfHall().toString());
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
