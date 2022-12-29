package unipa.prog3.model.entity;

public class Courier extends Entity {
    private final String nome, cognome, email, password;

    public Courier(String id, String nome, String cognome, String email, String password) {
        super(id);
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public Courier(String nome, String cognome, String email, String password) {
        this(null, nome, cognome, email, password);
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
