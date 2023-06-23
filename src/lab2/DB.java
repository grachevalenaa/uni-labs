package lab2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DB {
    private static final String USER_NAME = "root";

    private static final String PASSWORD = "root";

    private static final String URL = "jdbc:mysql://localhost:3306/mysql";

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables){
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void setAllDataFromDB() {
        try (Statement statement = connection.createStatement()) {
            Class.forName(JDBC_DRIVER);

            // добавление фильмов
            ResultSet resultSet = statement.executeQuery("SELECT * FROM db_cinema.movies");
            List<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                movies.add(new Movie(
                        resultSet.getString("name"),
                        resultSet.getInt("release_year"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration"),
                        resultSet.getString("format")
                ));
            }

            // добавление кинотеатров
            resultSet = statement.executeQuery("SELECT name, address, max_capacity FROM db_cinema.movie_theaters");
            List<MovieTheater> movieTheaters = new ArrayList<>();
            while (resultSet.next()){
                movieTheaters.add(new MovieTheater(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }

            // добавление залов
            resultSet = statement.executeQuery("SELECT * FROM db_cinema.halls");
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from db_cinema.prices where id = ?");
            ResultSet tempResultSet;
            List<Hall> halls = new ArrayList<>();
            int idPrice;  // переменная, в которую каждый раз записывается нужный индекс цены (для читаемости кода)
            while (resultSet.next()) {
                idPrice = resultSet.getInt("id_price");
                preparedStatement.setInt(1, idPrice);
                tempResultSet = preparedStatement.executeQuery();
                tempResultSet.next();  // в начале курсор стоит до первой строки
                if (tempResultSet.getInt("sec_rows_num") == 0) {
                    halls.add(DataFactory.getHall(
                            Halls.valueOf(resultSet.getString("id_hall")),
                            resultSet.getInt("rows"),
                            resultSet.getInt("columns"),
                            tempResultSet.getInt("first_price"),
                            movieTheaters.get(resultSet.getInt("id_movie_theater"))
                    ));
                } else {
                    halls.add(DataFactory.getHall(
                            Halls.valueOf(resultSet.getString("id_hall")),
                            resultSet.getInt("columns"),
                            tempResultSet.getInt("first_rows_num"),
                            tempResultSet.getInt("first_price"),
                            tempResultSet.getInt("sec_rows_num"),
                            tempResultSet.getInt("sec_price"),
                            movieTheaters.get(resultSet.getInt("id_movie_theater"))
                    ));
                }
            }

            // добавление сеансов
            List<Session> sessions = new ArrayList<>();
            resultSet = statement.executeQuery("SELECT * FROM db_cinema.sessions");
            while (resultSet.next()) {
                sessions.add(new Session(
                        halls.get(resultSet.getInt("id_hall")),
                        movies.get(resultSet.getInt("id_movie")),
                        resultSet.getTimestamp("start_time")
                ));
            }
            MovieTheaterSystem.setAllSessions(sessions);

            // добавление всех клиентов
            List<Client> clients = new ArrayList<>();
            resultSet = statement.executeQuery("SELECT * FROM db_cinema.clients");
            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getString("name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getInt("finance"),
                        resultSet.getString("password")
                ));
            }
            MovieTheaterSystem.setAllClients(clients);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void addClientToDB(Client client) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO db_cinema.clients (name, phone_number, email, finance, password) " +
                "VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPhoneNumber());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setInt(4, client.getFinances());
            preparedStatement.setString(5, client.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public static Clients getTypeOfClientFromDB(Client client) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("Select status from db_cinema.clients " +
                "where phone_number = ? and password = ?")) {

            ResultSet tempResultSet;
            preparedStatement.setString(1, client.getPhoneNumber());
            preparedStatement.setString(2, client.getPassword());
            tempResultSet = preparedStatement.executeQuery();
            tempResultSet.next();
            return Clients.valueOf(tempResultSet.getString("status"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void addNewTicketToDB(Session session, Client client, int price) {

        int id_session;
        try (CallableStatement callableStatement = connection.prepareCall("{? = call db_cinema.findSessionId(?, ?, ?, ?)}")) {
            // находим id_session
            callableStatement.setTimestamp(2, session.getStartTime());
            callableStatement.setString(3, session.getMovie().getNAME());
            callableStatement.setString(4, session.getHall().getMovieTheater().getName());
            callableStatement.setString(5, session.getHall().getTypeOfHall().toString());
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();
            id_session = callableStatement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        int id_client;
        try (CallableStatement callableStatement = connection.prepareCall("{? = call db_cinema.findClientId(?, ?)}")) {
            // находим id_client
            callableStatement.setString(2, client.getName());
            callableStatement.setString(3, client.getPassword());
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();
            id_client = callableStatement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT into db_cinema.ticket_selling (id_session, id_client, price) " +
                "VALUES (?, ?, ?)")) {
            // вставляем строку в таблицу ticket_selling
            preparedStatement.setInt(1, id_session);
            preparedStatement.setInt(2, id_client);
            preparedStatement.setInt(3, price);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static int getAllRevenue() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select sum(price) from db_cinema.ticket_selling");
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static int getTicketNum() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select count(*) from db_cinema.ticket_selling");
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String getHallRevenueInfo() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("Select sum(price) from db_cinema.ticket_selling as t where exists(Select * from db_cinema.sessions as s where id = t.id_session and exists(Select * from db_cinema.halls where id = s.id_hall and id_hall = ?))")) {
            StringBuilder stringBuilder = new StringBuilder();
            ResultSet resultSet;
            for (Halls typeOfHall: Halls.values()) {
                preparedStatement.setString(1, typeOfHall.toString());
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                stringBuilder.append(typeOfHall).append(": ").append(resultSet.getInt(1)).append("\t\t");
            }
            return stringBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String getNumOfClientsInfo() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("Select count(*) from db_cinema.clients where status = ?")) {
            StringBuilder stringBuilder = new StringBuilder();
            ResultSet resultSet;
            for (Clients typeOfClient: Clients.values()) {
                preparedStatement.setString(1, typeOfClient.toString());
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                stringBuilder.append(typeOfClient).append(": ").append(resultSet.getInt(1)).append("\t\t");
            }
            return stringBuilder.toString();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
