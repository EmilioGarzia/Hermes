package unipa.prog3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unipa.prog3.view.controller.MainController;

import java.io.IOException;

public class MainApplication extends Application {
    private static MainController mainController;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(loadView("/unipa/prog3/main.fxml"));
        stage.setTitle("Progetto Programmazione 3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

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