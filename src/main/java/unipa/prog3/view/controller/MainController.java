package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import unipa.prog3.MainApplication;

import java.util.Stack;

public class MainController extends Stack<Controller> {
    @FXML
    private ScrollPane contentPane;

    public MainController() {
        MainApplication.setMainController(this);
    }

    @FXML
    public void initialize() {
        showView("/unipa/prog3/home-view.fxml");
    }

    @FXML
    public void onLogin() {
        showView("/unipa/prog3/login-view.fxml");
    }

    @FXML
    public void onSignUp() {
        showView("/unipa/prog3/signup-view.fxml");
    }

    public void showView(String path) {
        contentPane.setContent(MainApplication.loadView(path));
    }

    public void navigateBack() {
        pop();
        Controller controller = lastElement();
        contentPane.setContent(controller.getNode());
        controller.onResume();
    }
}
