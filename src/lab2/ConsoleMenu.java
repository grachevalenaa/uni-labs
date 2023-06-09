package lab2;

import java.util.Scanner;

public class ConsoleMenu {

    public static void menu() {

        String[] options = {"1- Зайти как клиент",
                "2- Зайти в личный кабинет администратора (требуется пароль)",
                "3- Выйти"
        };
        // ИСПОЛЬЗОВАТЬ ЗДЕСЬ ENUM
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        Client client;
        while (option != options.length) {
            printMenu(options);
            try {
                option = Integer.parseInt(scanner.next());

                if (option < 1 || option > options.length) {
                    throw new NumberFormatException();
                }

                switch (option) {
                    case 1 -> {
                        client = Client.clientMenu();  // войти в существующий акканут или создать новый
                        Session recommendedSession = Client.findMovieSessionMenu(client);
                        if (recommendedSession == null) {
                            break;
                        }
                        client.buyTicketFromConsole(recommendedSession);
                    }

                    case 2 -> {
                        if (Admin.logInAdminAccount()) {
                            Admin.adminMenu();
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("ОШИБКА ПРИ ВВОДЕ ДАННЫХ! Попробуйте снова\n\n" +
                        "Введите пожалуйста число между 1 и " + options.length + " включительно вновь");
            } catch(NullPointerException ex) {
                System.out.println("Мы не нашли такого пользователя в системе\n\nПопробуйте еще раз");
            } catch (Exception ex) {
                System.out.println("Произошла ошибка\n\nПопробуйте еще раз");
            }

        }

    }

    public static void printMenu(String[] options) {

        for (String option : options) {
            System.out.println(option);
        }

        System.out.print("Выберите пункт меню: ");

    }
}