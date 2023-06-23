package lab2;

import java.util.ArrayList;
import java.util.List;

public class MovieTheaterSystem {

    private static List<Client> allClients = new ArrayList<>();

    private static List<Session> allSessions;  // все сеансы

    public static List<Session> getAllSessions() {
        return allSessions;
    }

    public static void setAllSessions(List<Session> sessions) {
        allSessions = sessions;
    }

    public static List<Client> getAllClients() {
        return allClients;
    }

    public static void setAllClients(List<Client> allClients) {
        MovieTheaterSystem.allClients = allClients;
    }

    public static void incrementClients(Client client) {
        allClients.add(client);
    }

    public static Client findCertainClient(String phoneNumber, String password) {
        Client client;
        for (int i = 0; i < allClients.size(); ++i) {
            client = allClients.get(i);
            if (client.getPhoneNumber().equals(phoneNumber) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }
}
