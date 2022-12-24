package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import unipa.prog3.controller.service.CourierService;
import unipa.prog3.model.entity.Courier;

public class SignupController {
    private final CourierService service;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passConfirmField;

    public SignupController() {
        service = new CourierService();
    }

    @FXML
    public void onSubmitSignup() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = passConfirmField.getText();

        if (name.isEmpty())
            errorLabel.setText("Il campo nome è vuoto!");
        else if (surname.isEmpty())
            errorLabel.setText("Il campo cognome è vuoto!");
        else if (email.isEmpty())
            errorLabel.setText("Il campo email è vuoto!");
        else if (password.isEmpty())
            errorLabel.setText("Il campo password è vuoto!");
        else if (confirmPassword.isEmpty())
            errorLabel.setText("Il campo conferma password è vuoto!");
        else if (!password.equals(confirmPassword))
            errorLabel.setText("Le password non coincidono!");
        else if (password.length() < 8)
            errorLabel.setText("La password è troppo debole!");
        else {
            Courier courier = new Courier(name, surname, email, password);
            boolean success = service.signup(courier);
            if (success) {
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setText("Registrazione avvenuta con successo!");
            } else errorLabel.setText("L'email è già presente nel nostro database!");
        }
    }
}
