package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import unipa.prog3.MainApplication;

/**
 * Classe astratta che contiene tutti i metodi di uso generale dei vari controller
 * */
public abstract class Controller {
    @FXML
    private Node node; // Oggetto Node associato al contenitore principale della view

    public Controller() {
        MainApplication.getMainController().push(this);
    }

    public Node getNode() {
        return node;
    }

    /**
     * Metodo che viene invocato nel momento in cui la view viene visualizzata
     * */
    public abstract void onResume();
}
