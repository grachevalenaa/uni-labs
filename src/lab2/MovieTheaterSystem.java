package lab2;

import java.util.ArrayList;
import java.util.List;

public abstract class MovieTheaterSystem {

    private static List<MovieSession> allSessions = new ArrayList<>();


    public static List<MovieSession> getAllSessions() {
        return allSessions;
    }

    public static void setNewSession(MovieSession session) {
        allSessions.add(session);
    }

}
