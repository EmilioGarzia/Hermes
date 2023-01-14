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
        showView("/unipa/prog3/view/home-view.fxml");
    }

    @FXML
    public void send() {
        MainApplication.getMainController().showView("/unipa/prog3/view/send-view.fxml");
    }

    @FXML
    public void track() {
        MainApplication.getMainController().showView("/unipa/prog3/view/track-view.fxml");
    }

    @FXML
    public void reportDelivery() {
        MainApplication.getMainController().showView("/unipa/prog3/view/courier-view.fxml");
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
