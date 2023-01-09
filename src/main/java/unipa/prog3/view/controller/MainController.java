package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import unipa.prog3.MainApplication;

import java.io.IOException;
import java.util.Stack;

public class MainController extends Stack<Controller> {
    @FXML
    private ScrollPane contentPane;

    @FXML
    public void initialize() {
        MainApplication.setMainController(this);
        loadView("/unipa/prog3/home-view.fxml");
    }

    @FXML
    public void onLogin() {
        loadView("/unipa/prog3/login-view.fxml");
    }

    @FXML
    public void onSignUp() {
        loadView("/unipa/prog3/signup-view.fxml");
    }

    public void loadView(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            contentPane.setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void navigateBack() {
        pop();
        Controller controller = get(size()-1);
        contentPane.setContent(controller.getNode());
        controller.onResume();
    }
}
