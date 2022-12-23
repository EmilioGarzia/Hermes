package unipa.prog3.model.entity;

public class Utente {
    private final String email, password;

    public Utente(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
