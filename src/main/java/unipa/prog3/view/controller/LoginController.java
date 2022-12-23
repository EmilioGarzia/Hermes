package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import unipa.prog3.controller.service.LoginService;
import unipa.prog3.model.entity.Utente;
import unipa.prog3.view.MainApplication;

import java.io.IOException;

public class LoginController {
    private final LoginService service;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    public LoginController() {
        service = new LoginService();
    }

    @FXML
    protected void onSubmitLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        Utente utente = service.login(email, password);
        if (utente == null)
            errorLabel.setText("Email o password non valida!");
        else {
            errorLabel.setText("");
            MainApplication.getMainController().loadView("/unipa/prog3/courier-view.fxml");
        }
    }
}