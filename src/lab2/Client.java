package lab2;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Client {

    private static final int NUM_OF_CLIENT_FIELDS = 4;

    private String name;

    private String phoneNumber;

    private String email;

    private int finances;

    public Client(String name, String phoneNumber, String email, int finances) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.finances = finances;
    }

    // Фабричный метод
    public static Client createClientFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Пожалуйста, последовательно введите свое имя, номер телефона, email, свой бюджет для покупки сеанса");

        // Ввод данных пользователя
        String[] clientData = new String[NUM_OF_CLIENT_FIELDS];
         for (int i = 0; i < NUM_OF_CLIENT_FIELDS; ++i) {
            try {
                clientData[i] = scanner.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Ошибка. Пожалуйста, последовательно введите свое имя, номер телефона, email, свой бюджет для покупки сеанса");
                scanner.next();
            } catch (Exception ex) {
                System.out.println("Неизвестная ошибка. Попробуйте еще раз");
                scanner.next();
            }
        }
         return new Client(clientData[0], clientData[1], clientData[2], Integer.parseInt(clientData[3]));
    }


    public static MovieSession findMovieSessionMenu(Client client) {

        String[] options = {"1- Найти сеанс по фильму",
                "2- Найти сеанс по времени его начала",
                "3- Найти сеанс по имеющемуся бюджету",
                "4- Выйти"
        };

        Scanner scanner = new Scanner(System.in);
        int option = 0;
        List<MovieSession> recommendedSessions = null;
        MovieSession recommendedSession = null;
        while (option != options.length) {
            ConsoleMenu.printMenu(options);
            try {
                option = Integer.parseInt(scanner.next());

                if (option < 1 || option > options.length) {
                    throw new NumberFormatException();
                }

                switch (option) {
                    case 1 -> recommendedSessions = inputPriorityMovie();
                    case 2 -> recommendedSessions = inputPriorityStartTime();
                    case 3 -> recommendedSessions = ClientSystem.findSessions(client.getFinances());
                }
                if (option != 4) {
                    recommendedSession = chooseMovieSession(recommendedSessions);
                    option = 4;
                }
            } catch (NumberFormatException ex) {
                System.out.println("ОШИБКА ПРИ ВВОДЕ ДАННЫХ! Попробуйте снова\n\n" +
                        "Введите, пожалуйста, число между 1 и " + options.length + " включительно вновь");
            } catch (Exception ex) {
                System.out.println("Произошла ошибка\n\nПопробуйте еще раз");
            }
        }

        return recommendedSession;
    }

    public void buyTicketFromConsole(MovieSession session) {

        Scanner scanner = new Scanner(System.in);
        String answer = "";
        while (!answer.equals("Нет")) {
            try {
                System.out.print("Хотите ли вы купить билет(ы) на сеанс? (Да/ Нет): ");
                answer = scanner.next();

                if (!answer.equals("Да") && !answer.equals("Нет")) {
                    throw new InputMismatchException();
                }
                if (answer.equals("Да")) {
                    inputSeatInHall(session);
                }
            } catch (InputMismatchException ex) {
                System.out.println("ОШИБКА ПРИ ВВОДЕ ДАННЫХ! Попробуйте снова\n\n");
            } catch (Exception ex) {
                System.out.println("Произошла ошибка! Попробуйте еще раз\n\n");
            }
        }


    }

    private void inputSeatInHall(MovieSession session) {

        System.out.println("Как много билетов вы хотите купить? ");
        System.out.print("Количество: ");
        int count;
        int row;
        int column;
        int[][] hallMatrix = session.getHall().getHallMatrix();
        Scanner scanner = new Scanner(System.in);
        int countTicket = 0;

        try {
            count = Integer.parseInt(scanner.next());

            for (int i = 1; i <= count; ++i) {
                session.getHall().toString();
                System.out.print("Введите ряд для " + i + " билета: ");
                row = Integer.parseInt(scanner.next());
                System.out.print("Введите место в " + row + " ряду для " + i + " билета: ");
                column = Integer.parseInt(scanner.next());
                if (hallMatrix[row - 1][column - 1] < 0) {
                    System.out.println("Это место занято!\n");
                    continue;
                }
                if (finances - hallMatrix[row - 1][column - 1] < 0) {
                    System.out.println("У вас недостаточно средств для покупки!\n");
                    continue;
                }
                finances = finances - hallMatrix[row - 1][column - 1];
                hallMatrix[row - 1][column - 1] = -hallMatrix[row - 1][column - 1];
                countTicket++;
            }

            if (countTicket > 0) {
                System.out.println("\nСпасибо, " + name + ". Вы приобрели " + countTicket +
                        " билет(а)(ов).\nВся информация отправлена на почту: " + email + "\n");
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static MovieSession chooseMovieSession(List<MovieSession> sessions) {
        if (sessions.isEmpty()) {
            System.out.println("Подходящих сеансов не оказалось. Извините за неудобства!\n");
            return null;
        }


        if (sessions.size() == 1) {
            System.out.println("Вам доступен лишь один сеанс:\n" + sessions.get(0).toString() + "\n");
            return sessions.get(0);
        }


        System.out.println("Выберите сеанс из представленных ниже: ");
        for (int i = 1; i <= sessions.size(); ++i) {
            System.out.println(" " + i + "- " + sessions.get(i - 1).toString());
        }
        System.out.print("Ваш выбор: ");

        int choice;
        Scanner scanner = new Scanner(System.in);
        try {
            choice = Integer.parseInt(scanner.next());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return sessions.get(choice - 1);
    }

    private static List<MovieSession> inputPriorityMovie() {
        List<MovieSession> recommendedSessions;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите фильм для поиска сеансов: ");
        try {
            recommendedSessions = ClientSystem.findSessions(scanner.nextLine());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return recommendedSessions;
    }

    private static List<MovieSession> inputPriorityStartTime() {
        List<MovieSession> recommendedSessions;
        Scanner scanner = new Scanner(System.in);
        int[] startTime = new int[4];

        System.out.println("Введите последовательно месяц, число, час, минуты в числовом формате");
        try {
            for (int i = 0; i < 4; ++i) {
                startTime[i] = Integer.parseInt(scanner.next());
            }
        } catch (NumberFormatException ex) {
            throw new RuntimeException(ex);
        }
        recommendedSessions = ClientSystem.findSessions(LocalDateTime.of(
                2023,
                startTime[0],
                startTime[1],
                startTime[2],
                startTime[3])
        );

        return recommendedSessions;
    }

    public int getFinances() {
        return finances;
    }
}
