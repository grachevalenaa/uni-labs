package lab2;

import java.time.LocalDateTime;
import java.util.*;

public class ClientSystem extends MovieTheaterSystem{

    public static List<MovieSession> findSessions(String movie) {
        List<MovieSession> allSessions = new ArrayList<>(getAllSessions());

        ListIterator<MovieSession> iterator = allSessions.listIterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getMovie().getNAME().equals(movie)) {
                iterator.remove();
            }
        }

        return allSessions;
    }

    public static List<MovieSession> findSessions(LocalDateTime startTime) {
        List<MovieSession> allSessions = new ArrayList<>(getAllSessions());

        ListIterator<MovieSession> iterator = allSessions.listIterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getStartTime().equals(startTime)) {
                iterator.remove();
            }
        }

        return allSessions;
    }

    public static List<MovieSession> findSessions(int finances) {
        List<MovieSession> allSessions = new ArrayList<>(getAllSessions());
        ListIterator<MovieSession> iterator = allSessions.listIterator();
        // переменные для читаемости кода
        MovieTheaterHall hall;
        int[][] matrixHall;
        int columns;
        int rows;
        boolean flag = false;

        while (iterator.hasNext()) {
            hall = iterator.next().getHall();
            matrixHall = hall.getHallMatrix();
            columns = hall.getCOLUMNS();
            rows = hall.getROWS();


            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < columns; ++j) {
                    if (matrixHall[i][j] <= finances) {
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }

            if (flag == false) {
                iterator.remove();
            }

            flag = false;
        }

        return allSessions;
    }
}
