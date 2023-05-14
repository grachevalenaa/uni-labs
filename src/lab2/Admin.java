package lab2;

import java.util.List;
import java.util.Scanner;

public class Admin extends MovieTheaterSystem {

    private static final String LOGIN = "admin";

    private static final String PASSWORD = "passw";

    public static void inputAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        if (ifLoginPasswordRight(login, password)) {
            List<MovieSession> sessions = MovieTheaterSystem.getAllSessions();
            int revenue = 0;
            for (MovieSession session: sessions) {
                revenue += session.getRevenue();
            }
            System.out.println("Ваша выручка составляет: " + revenue + "  рублей\n");
        }
    }

    public static boolean ifLoginPasswordRight(String login, String password) {
        return (login.equals(LOGIN) && password.equals(PASSWORD));
    }

}
