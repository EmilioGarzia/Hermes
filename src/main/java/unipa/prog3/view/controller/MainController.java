package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import unipa.prog3.MainApplication;

import java.util.Stack;

/**
 * Classe che gestisce il pannello principale main.fxml che conterrà poi tutte le view
 * */
public class MainController extends Stack<Controller> {
    @FXML
    private ScrollPane contentPane;

    public MainController() {
        MainApplication.setMainController(this);
    }

    /**
     * Mostra la home-view come prima view in assoluto
     */
    public void initialize() {
        showView("/unipa/prog3/view/home-view.fxml");
    }

    /**
     * Provvede a caricare la view send-view.fxml qualora l'utente cliccasse su "Spedisci collo"
     * */
    @FXML
    public void send() {
        MainApplication.getMainController().showView("/unipa/prog3/view/send-view.fxml");
    }

    /**
     * Provvede a caricare la view track-view.fxml qualora l'utente cliccasse su "Traccia Spedizione"
     * */
    @FXML
    public void track() {
        MainApplication.getMainController().showView("/unipa/prog3/view/track-view.fxml");
    }

    /**
     * Provvede a caricare la view courier-view.fxml qualora l'utente cliccasse su "Segnala spedizione"
     * */
    @FXML
    public void reportDelivery() {
        MainApplication.getMainController().showView("/unipa/prog3/view/courier-view.fxml");
    }

    /**
     * Carica e mostra all'utente la view situata nel percorso specificato
     * @param path Il percorso del file fxml della view
     * */
    public void showView(String path) {
        contentPane.setContent(MainApplication.loadView(path));
    }

    /**
     * Rimuove il controller della view corrente dallo stack e mostra la view precedente
     * */
    public void navigateBack() {
        pop();
        Controller controller = lastElement();
        contentPane.setContent(controller.getNode());
        controller.onResume();
    }
}
