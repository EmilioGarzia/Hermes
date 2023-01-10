package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import unipa.prog3.MainApplication;

public class HomeController extends Controller {
    @FXML
    public void send() {
        MainApplication.getMainController().showView("/unipa/prog3/send-view.fxml");
    }

    @FXML
    public void track() {
        MainApplication.getMainController().showView("/unipa/prog3/track-view.fxml");
    }

    @Override
    public void onResume() {}
}
