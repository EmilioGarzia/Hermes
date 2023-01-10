package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import unipa.prog3.controller.service.CourierService;
import unipa.prog3.MainApplication;
import unipa.prog3.controller.service.util.ServiceProvider;
import unipa.prog3.model.entity.Courier;

public class LoginController extends Controller {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    protected void onSubmitLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        CourierService service = (CourierService) ServiceProvider.getService(Courier.class);
        if (service.login(email, password)) {
            errorLabel.setText("");
            MainApplication.getMainController().showView("/unipa/prog3/courier-view.fxml");
        } else errorLabel.setText("Email o password errati!");
    }

    @Override
    public void onResume() {}
}