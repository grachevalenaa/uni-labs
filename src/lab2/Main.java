package lab2;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        MovieTheater cheapMovieTheater = new MovieTheater(
                50,
                "Дешёвый",
                "Москва",
                new String[]{"2D"});
        MovieTheater expensiveMovieTheater = new MovieTheater(
                50,
                "Дорогой",
                "Москва",
                new String[]{"2D", "3D"});
        MovieTheater bigMovieTheater = new MovieTheater(
                300,
                "Большой",
                "Москва",
                new String[]{"2D", "3D"});

        MovieTheaterHall cheapHall1 = new MovieTheaterHall(5, 10, 150, cheapMovieTheater);
        MovieTheaterHall cheapHall2 = new MovieTheaterHall(5, 10, 150, cheapMovieTheater);
        MovieTheaterHall cheapHall3 = new MovieTheaterHall(5, 10, 150, cheapMovieTheater);
        MovieTheaterHall cheapHall4 = new MovieTheaterHall(5, 10, 150, cheapMovieTheater);
        MovieTheaterHall expensiveHall1 = new MovieTheaterHall(5, 10, 990, expensiveMovieTheater);
        MovieTheaterHall expensiveHall2 = new MovieTheaterHall(5, 10, 990, expensiveMovieTheater);
        MovieTheaterHall expensiveHall3 = new MovieTheaterHall(5, 10, 990, expensiveMovieTheater);
        MovieTheaterHall expensiveHall4 = new MovieTheaterHall(5, 10, 990, expensiveMovieTheater);
        MovieTheaterHall bigHall1 = new MovieTheaterHall(10, 6, 200, 4, 600, bigMovieTheater);
        MovieTheaterHall bigHall2 = new MovieTheaterHall(10, 6, 200, 4, 600, bigMovieTheater);
        MovieTheaterHall bigHall3 = new MovieTheaterHall(10, 6, 200, 4, 600, bigMovieTheater);
        MovieTheaterHall bigHall4 = new MovieTheaterHall(10, 6, 200, 4, 600, bigMovieTheater);



        Movie theTrumanShowMovie = new Movie("Шоу Трумана", 1998, "drama", 103, "2D");
        Movie theMagicMaxLastDance = new Movie("Супер Майк: Последний танец", 2023, "drama", 112, "3D");
        Movie theDunePartTwoMovie = new Movie("Дюна 2", 2023, "drama", 110, "3D");
        Movie theBrilliantHandMovie = new Movie("Бриллиантовая рука", 1968, "comedy", 94, "2D");
        Movie theCargoTwoHundredMovie = new Movie("Груз 200", 2007, "thriller", 90, "2D");
        Movie theMeBeforeYouMovieMovie = new Movie("До встречи с тобой", 2016, "melodrama", 106, "3D");
        Movie theLittleVeraMovie = new Movie("Маленькая Вера", 1988, "melodrama", 110, "2D");
        Movie theRabiesMovie = new Movie("Бешенство", 2023, "thriller", 100, "3D");

        MovieSession cheapSession1 = new MovieSession(cheapHall1, theTrumanShowMovie, LocalDateTime.of(2023, 3, 14, 10,0));
        MovieSession cheapSession2 = new MovieSession(cheapHall2, theBrilliantHandMovie, LocalDateTime.of(2023, 3, 14, 12,0));
        MovieSession cheapSession3 = new MovieSession(cheapHall3, theCargoTwoHundredMovie, LocalDateTime.of(2023, 3, 14, 14,0));
        MovieSession cheapSession4 = new MovieSession(cheapHall4, theLittleVeraMovie, LocalDateTime.of(2023, 3, 14, 16,0));
        MovieSession expensiveSession1 = new MovieSession(expensiveHall1, theMagicMaxLastDance, LocalDateTime.of(2023, 3, 14, 10,0));
        MovieSession expensiveSession2 = new MovieSession(expensiveHall2, theDunePartTwoMovie, LocalDateTime.of(2023, 3, 14, 12,0));
        MovieSession expensiveSession3 = new MovieSession(expensiveHall3, theMeBeforeYouMovieMovie, LocalDateTime.of(2023, 3, 14, 14,0));
        MovieSession expensiveSession4 = new MovieSession(expensiveHall4, theRabiesMovie, LocalDateTime.of(2023, 3, 14, 16,0));
        MovieSession bigSession1 = new MovieSession(bigHall1, theTrumanShowMovie, LocalDateTime.of(2023, 3, 14, 10,0));
        MovieSession bigSession2 = new MovieSession(bigHall2, theBrilliantHandMovie, LocalDateTime.of(2023, 3, 14, 12,0));
        MovieSession bigSession3 = new MovieSession(bigHall3, theMeBeforeYouMovieMovie, LocalDateTime.of(2023, 3, 14, 14,0));
        MovieSession bigSession4 = new MovieSession(bigHall4, theRabiesMovie, LocalDateTime.of(2023, 3, 14, 16,0));

        MovieTheaterSystem.setNewSession(cheapSession1);
        MovieTheaterSystem.setNewSession(cheapSession2);
        MovieTheaterSystem.setNewSession(cheapSession3);
        MovieTheaterSystem.setNewSession(cheapSession4);
        MovieTheaterSystem.setNewSession(expensiveSession1);
        MovieTheaterSystem.setNewSession(expensiveSession2);
        MovieTheaterSystem.setNewSession(expensiveSession3);
        MovieTheaterSystem.setNewSession(expensiveSession4);
        MovieTheaterSystem.setNewSession(bigSession1);
        MovieTheaterSystem.setNewSession(bigSession2);
        MovieTheaterSystem.setNewSession(bigSession3);
        MovieTheaterSystem.setNewSession(bigSession4);

        ConsoleMenu.menu();
    }
}
