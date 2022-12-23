package unipa.prog3.controller.service;

import unipa.prog3.model.DataManager;
import unipa.prog3.model.entity.Utente;

public class LoginService {
    private final String userFile = "users.csv";
    private final DataManager dataManager;

    public LoginService() {
        dataManager = DataManager.getInstance();
    }

    public Utente login(String email, String password) {
        String[] users = dataManager.readData(userFile);
        for (String s : users) {
            String[] info = s.split(";");
            if (info[0].equals(email) && info[1].equals(password))
                return new Utente(info[0], info[1]);
        }

        return null;
    }
}
