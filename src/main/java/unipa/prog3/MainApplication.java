package unipa.prog3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unipa.prog3.view.controller.MainController;

import java.io.IOException;

/**
 * Classe principale. Contiene il metodo main.
 */
public class MainApplication extends Application {
    private static MainController mainController;

    /**
     * Mostra a schermo la finestra e la riempie con gli elementi caricati dal file main.fxml
     * @param stage Istanza dell'oggetto che fa riferimento alla finestra dell'applicazione
     */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(loadView("/unipa/prog3/view/main.fxml"));
        stage.setTitle("Hermès");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Carica una view dal relativo file fxml
     * @param path Percorso del file fxml
     * @return Istanza della view caricata
     * @param <T> Classe dell'oggetto relativo alla view caricata
     */
    public static <T> T loadView(String path) {
        try {
            return new FXMLLoader(MainApplication.class.getResource(path)).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void setMainController(MainController mainController) {
        MainApplication.mainController = mainController;
    }
}