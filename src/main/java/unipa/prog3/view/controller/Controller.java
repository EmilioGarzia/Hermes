package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import unipa.prog3.MainApplication;

public abstract class Controller {
    @FXML
    public Node node;

    public Controller() {
        MainApplication.getMainController().push(this);
    }

    public Node getNode() {
        return node;
    }

    public abstract void onResume();
}
