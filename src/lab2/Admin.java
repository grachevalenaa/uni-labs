package lab2;

import java.util.Scanner;

public class Admin extends User {

    private static final String LOGIN = "admin";

    private static final String PASSWORD = "passw";

    public static boolean logInAdminAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        return ifLoginPasswordRight(login, password);
    }

    public static void adminMenu() {

        String[] options = {"1- Общая выручка",
                "2- Количество проданных билетов",
                "3- Выручка по типу зала",
                "4- Количество клиентов каждого статуса",
                "5- Удалить/ Редактировать/ Добавить кинотеатры, залы",
                "6- Выйти из личного кабинета администратора"
        };

        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (option != options.length) {
            try {
                ConsoleMenu.printMenu(options);
                option = Integer.parseInt(scanner.next());

                if (option < 1 || option > options.length) {
                    throw new NumberFormatException();
                }

                String ANSI_BLUE = "\u001B[34m";
                String ANSI_RESET = "\u001B[0m";

                switch (option) {
                    case 1 -> System.out.println(ANSI_BLUE + "Общая выручка: " + ANSI_RESET + DB.getAllRevenue());
                    case 2 -> System.out.println(ANSI_BLUE + "Количество проданных билетов: " + ANSI_RESET + DB.getTicketNum());
                    case 3 -> System.out.println(ANSI_BLUE + "Выручка по типу зала:\n" + ANSI_RESET + DB.getHallRevenueInfo());
                    case 4 -> System.out.println(ANSI_BLUE + "Количество клиентов каждого статуса:\n" + ANSI_RESET + DB.getNumOfClientsInfo());
                }

                System.out.println();

            } catch (NumberFormatException ex) {
                System.out.println("ОШИБКА ПРИ ВВОДЕ ДАННЫХ! Попробуйте снова\n\n" +
                        "Введите, пожалуйста, число между 1 и " + options.length + " включительно вновь");
            } catch (Exception ex) {
                System.out.println("Произошла ошибка\n\nПопробуйте еще раз");
            }

            /*if (option != options.length) {
                ConsoleMenu.printMenu(options);
                option = Integer.parseInt(scanner.next());
            }*/
        }
    }
    private static boolean ifLoginPasswordRight(String login, String password) {
        return (login.equals(LOGIN) && password.equals(PASSWORD));
    }
}
