package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import unipa.prog3.controller.service.CourierService;
import unipa.prog3.view.MainApplication;

public class LoginController {
    private final CourierService service;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    public LoginController() {
        service = new CourierService();
    }

    @FXML
    protected void onSubmitLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (service.login(email, password)) {
            errorLabel.setText("");
            MainApplication.getMainController().loadView("/unipa/prog3/courier-view.fxml");
        } else errorLabel.setText("Email o password errati!");
    }
}