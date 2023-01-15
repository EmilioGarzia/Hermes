package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import unipa.prog3.MainApplication;

/**
 * Classe astratta che contiene tutti i metodi di suo generale dei vari controller
 * */
public abstract class Controller {
    @FXML
    private Node node;

    public Controller() {
        MainApplication.getMainController().push(this);
    }

    /**
     * Ritorna un nodo, ovvero, un astrazione dei panel principali delle view
     * @return Node
     * */
    public Node getNode() {
        return node;
    }

    /**
     * Metodo che viene invocato quando la view viene visualizzata
     * */
    public abstract void onResume();
}
