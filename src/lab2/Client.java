package lab2;

import java.sql.Timestamp;
import java.util.*;


public class Client extends User {

    private static final int NUM_OF_CLIENT_FIELDS = 5;

    private String name;

    private String phoneNumber;

    private String email;

    private int finances;

    private String password;

    public Client(String name, String phoneNumber, String email, int finances, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.finances = finances;
        this.password = password;
    }

    public static Client clientMenu() throws RuntimeException {
        String[] options = {"1- Войти в существующий аккаунт",
                "2- Создать аккаунт",
        };
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        Client client = null;
        while (client == null) {
            ConsoleMenu.printMenu(options);
            try {
                option = Integer.parseInt(scanner.next());

                if (option < 1 || option > options.length) {
                    throw new NumberFormatException();
                }

                switch (option) {
                    case 1 -> {
                        client = findExistingClient();
                        if (client == null) {
                            throw new NullPointerException();
                        }
                    }

                    case 2 -> {
                        client = Client.createClientFromConsole();
                        MovieTheaterSystem.incrementClients(client);
                        DB.addClientToDB(client);

                    }
                }
            } catch (NumberFormatException ex) {
                throw new NumberFormatException();
            } catch (NullPointerException ex) {
                throw new NullPointerException();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException();
            }
        }
        return client;
    }

    private static Client findExistingClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите свой номер телефона: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        return MovieTheaterSystem.findCertainClient(phoneNumber, password);
    }

    // Фабричный метод
    private static Client createClientFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Пожалуйста, последовательно введите свое имя, номер телефона, email, свой бюджет и пароль");

        // Ввод данных пользователя
        String[] clientData = new String[NUM_OF_CLIENT_FIELDS];
         for (int i = 0; i < NUM_OF_CLIENT_FIELDS; ++i) {
            try {
                clientData[i] = scanner.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Ошибка. Пожалуйста, последовательно введите свое имя, номер телефона, email, свой бюджет и пароль");
                scanner.next();
            } catch (Exception ex) {
                System.out.println("Неизвестная ошибка. Попробуйте еще раз");
                scanner.next();
            }
         }

         return new Client(clientData[0], clientData[1], clientData[2], Integer.parseInt(clientData[3]), clientData[4]);
    }


    public static Session findMovieSessionMenu(Client client) {

        String[] options = {"1- Найти сеанс по фильму",
                "2- Найти сеанс по времени его начала",
                "3- Найти сеанс по имеющемуся бюджету",
        };

        Scanner scanner = new Scanner(System.in);
        int option = 0;
        List<Session> recommendedSessions = null;
        Session recommendedSession = null;
        while (option != options.length + 1) {
            ConsoleMenu.printMenu(options);
            try {
                option = Integer.parseInt(scanner.next());

                if (option < 1 || option > options.length) {
                    throw new NumberFormatException();
                }

                switch (option) {
                    case 1 -> recommendedSessions = inputPriorityMovie();
                    case 2 -> recommendedSessions = inputPriorityStartTime();
                    case 3 -> recommendedSessions = findSessions(client.getFinances());
                }
                recommendedSession = chooseMovieSession(recommendedSessions);
                option = options.length + 1;
            } catch (NumberFormatException ex) {
                System.out.println("ОШИБКА ПРИ ВВОДЕ ДАННЫХ! Попробуйте снова\n\n" +
                        "Введите, пожалуйста, число между 1 и " + options.length + " включительно вновь");
            } catch (Exception ex) {
                System.out.println("Произошла ошибка\n\nПопробуйте еще раз");
            }
        }

        return recommendedSession;
    }

    public void buyTicketFromConsole(Session session) {
        Scanner scanner = new Scanner(System.in);
        String answer = "Да";  // в начале по дефолту входим в цикл, answer меняется пользователем после первой итерации
        while (!answer.equals("Нет")) {
            try {
                if (!answer.equals("Да") && !answer.equals("Нет")) {
                    throw new InputMismatchException();
                }
                if (answer.equals("Да")) {
                    inputSeatInHall(session);
                }
                System.out.print("Хотите ли вы еще купить билет(ы) на этот сеанс? (Да/ Нет): ");
                answer = scanner.next();
            } catch (InputMismatchException ex) {
                System.out.println("ОШИБКА ПРИ ВВОДЕ ДАННЫХ! Попробуйте снова\n\n");
            } catch (Exception ex) {
                System.out.println("Произошла ошибка! Попробуйте еще раз\n\n");
            }
        }
    }

    public double getDiscountPerCent() {
        try {
            if (DB.getTypeOfClientFromDB(this).equals(Clients.ORDINARY)) {
                return 1;
            } else if (DB.getTypeOfClientFromDB(this).equals(Clients.VIP)) {
                return 0.8;
            }
            return 0.9;
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void inputSeatInHall(Session session) throws RuntimeException {

        System.out.println("Как много билетов вы хотите купить?");
        System.out.print("Количество: ");
        int count;
        int row;
        int column;
        int[][] hallMatrix = session.getHall().getHallMatrix();
        Scanner scanner = new Scanner(System.in);
        int countTicket = 0;
        int currentPrice; //  переменная для читаемости кода
        int total = 0;

        try {
            count = Integer.parseInt(scanner.next());

            for (int i = 1; i <= count; ++i) {
                session.getHall().toString();
                System.out.print("Введите ряд: ");
                row = Integer.parseInt(scanner.next());
                System.out.print("Введите место в " + row + " ряду: ");
                column = Integer.parseInt(scanner.next());
                currentPrice = (int) Math.round(hallMatrix[row - 1][column - 1] * getDiscountPerCent());
                if (currentPrice < 0) {
                    System.out.println("Это место занято!\n");
                    continue;
                }
                if (finances - currentPrice < 0) {
                    System.out.println("У вас недостаточно средств для покупки! Пополните счёт\n");
                    break;
                }
                finances = finances - currentPrice;
                total += currentPrice;
                System.out.println("Этот билет Вам обошелся в " + currentPrice + " рублей\n");
                hallMatrix[row - 1][column - 1] = -hallMatrix[row - 1][column - 1];
                countTicket++;
                DB.addNewTicketToDB(session, this, currentPrice);
            }

            if (countTicket > 0) {
                System.out.println("\nСпасибо, " + name + ". Вы приобрели " + countTicket +
                        " билет(а)(ов). Суммарно вы потратили " + total + " рублей" +
                        "\nВся информация отправлена на почту: " + email + "\nВаш статус: " + DB.getTypeOfClientFromDB(this) +
                        ", поэтому вы получили скидку в " + (1 - getDiscountPerCent()) * 100 + " %");
                if (DB.getTypeOfClientFromDB(this).equals(Clients.VIP)) {
                    System.out.println("Также вы можете получить бесплатный попкорн");
                } else {
                    System.out.println("К сожалению, посетителям с таким статусом не выдается бесплатный попкорн");
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Session chooseMovieSession(List<Session> sessions) throws RuntimeException {
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

    private static List<Session> inputPriorityMovie() throws RuntimeException {
        List<Session> recommendedSessions;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите фильм для поиска сеансов: ");
        try {
            recommendedSessions = findSessions(scanner.nextLine());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return recommendedSessions;
    }

    private static List<Session> inputPriorityStartTime() throws RuntimeException {
        List<Session> recommendedSessions;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите дату в данном формате: ГГГГ-ММ-ДД ЧЧ:ММ:СС");
        try {
            recommendedSessions = findSessions(Timestamp.valueOf(scanner.nextLine()));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        }

        return recommendedSessions;
    }

    private static List<Session> findSessions(String movie) {
        List<Session> allSessions = new ArrayList<>(MovieTheaterSystem.getAllSessions());

        ListIterator<Session> iterator = allSessions.listIterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getMovie().getNAME().equals(movie)) {
                iterator.remove();
            }
        }

        return allSessions;
    }

    private static List<Session> findSessions(Timestamp startTime) {
        List<Session> allSessions = new ArrayList<>(MovieTheaterSystem.getAllSessions());

        ListIterator<Session> iterator = allSessions.listIterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getStartTime().equals(startTime)) {
                iterator.remove();
            }
        }

        return allSessions;
    }

    private static List<Session> findSessions(int finances) {
        List<Session> allSessions = new ArrayList<>(MovieTheaterSystem.getAllSessions());
        ListIterator<Session> iterator = allSessions.listIterator();
        // переменные для читаемости кода
        Hall hall;
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
                    if (matrixHall[i][j] <= finances && matrixHall[i][j] > 0) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }

            if (!flag) {
                iterator.remove();
            }

            flag = false;
        }

        return allSessions;
    }

    public int getFinances() {
        return finances;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
