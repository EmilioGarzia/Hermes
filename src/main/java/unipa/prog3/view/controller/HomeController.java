package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import unipa.prog3.view.MainApplication;

public class HomeController {
    @FXML
    public void send() {
        MainApplication.getMainController().loadView("/unipa/prog3/send-view.fxml");
    }

    @FXML
    public void track() {
        MainApplication.getMainController().loadView("/unipa/prog3/track-view.fxml");
    }
}
